package ru.revolut.server;

import com.jayway.restassured.response.ValidatableResponse;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.revolut.context.GuiceServerContext;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class IntegrationTest {

    private static final int TEST_PORT = 3333;

    private TransferServer instance;
    private String host;

    @BeforeMethod
    public void setUp() throws Exception {
        instance = new TransferServer(TEST_PORT, new GuiceServerContext());
        host = "http://localhost:" + TEST_PORT;
    }

    @AfterMethod
    public void tearDown() throws Exception {
        instance.stop();
    }

    @Test
    public void testServerStart() throws Exception {
        instance.start();

        final ValidatableResponse createFirstAccount = given().when().body("100").post(host + "/createAccount/123").then();
        createFirstAccount.statusCode(200);
        createFirstAccount.body(equalTo("success"));

        final ValidatableResponse createSeconAccount = given().when().body("100").post(host + "/createAccount/124").then();
        createSeconAccount.statusCode(200);
        createSeconAccount.body(equalTo("success"));

        final ValidatableResponse transfer = given().when().body("50").post(host + "/transfer/123/124").then();
        transfer.statusCode(200);
        transfer.body(equalTo("success"));

        final ValidatableResponse firstBalance = given().when().body("123").post(host + "/balance").then();
        firstBalance.statusCode(200);
        firstBalance.body(equalTo("50"));

        final ValidatableResponse secondBalance = given().when().body("124").post(host + "/balance").then();
        secondBalance.statusCode(200);
        secondBalance.body(equalTo("150"));
    }
}
