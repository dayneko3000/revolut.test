package ru.revolut.process;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.revolut.dto.Account;
import ru.revolut.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;

import static org.testng.Assert.assertEquals;

public class TransferTest {

    private Account first;
    private Account second;
    private BigDecimal amount;

    @BeforeMethod
    public void setUp() throws Exception {
        amount = BigDecimal.valueOf(10000000);
        first = new Account(123L, amount);
        second = new Account(124L, amount);
    }

    @Test
    public void testDeadlock() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            latch.countDown();
            for (int i = 0; i < 100000; i++) {
                new Transfer(first, second, BigDecimal.ONE).process();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            latch.countDown();
            for (int i = 0; i < 100000; i++) {
                new Transfer(second, first, BigDecimal.ONE).process();
            }
        });
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(first.getAmount(), amount);
        assertEquals(second.getAmount(), amount);
    }

    @Test
    public void testRace() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);

        Thread thread1 = new Thread(() -> {
            latch.countDown();
            for (int i = 0; i < 100000; i++) {
                new Transfer(first, second, BigDecimal.ONE).process();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            latch.countDown();
            for (int i = 0; i < 100000; i++) {
                new Transfer(first, second, BigDecimal.ONE).process();
            }
        });
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(first.getAmount(), amount.subtract(BigDecimal.valueOf(100000 * 2)));
        assertEquals(second.getAmount(), amount.add(BigDecimal.valueOf(100000 * 2)));
    }

    @Test(expectedExceptions = InsufficientFundsException.class)
    public void testTransferFail() throws InterruptedException {
        new Transfer(first, second, amount.add(BigDecimal.valueOf(1))).process();
    }
}