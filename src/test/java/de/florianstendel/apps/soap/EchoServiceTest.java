package de.florianstendel.apps.soap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by fstendel on 16.06.2017.
 */
public class EchoServiceTest {

    private EchoService echoService;


    @Before
    public void setUp(){

        echoService = new EchoService();

    }


  //  @Test
    public void testEchoEchoes(){

        // Given
        final String input = "echoIn";
        final String expectedOutput = "echoIn";

        // When
        final String actualOutput = echoService.sayHello("Alex");


        //Then
        Assert.assertEquals("Returned value expected to match input value.",expectedOutput,actualOutput);
    }
}
