# revolut.test
I used jetty as a servlet container. 
I used Google.Guice for a little dependency injection (It was the first time do not judge strictly). 
For the unit testing used TestNG. For the integration testing used REST Assured.
Storaging in memory is custom implemented on the simple HashMap, this allowed to make synchronization on the account object.

I did not write a lot of integration tests because they basically duplicate the coverage of unit tests. And did not write javadocs
