package de.florianstendel.apps.soap;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InterruptedNamingException;
import javax.naming.NamingException;

/**
 * Created by Florian Stendel on 02.09.2016.
 */
@WebService(serviceName = "EchoService",
            targetNamespace = "http://apps.florianstendel.de/")
public class EchoService {

    @WebMethod
    @WebResult(name = "echoedText")
    public String echo(@WebParam(name = "inputText") String input){

        return input;
    }
}
