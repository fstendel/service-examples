package de.florianstendel.apps.soap;

import com.examples.wsdl.helloservice.HelloPortType;

import javax.jws.WebService;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InterruptedNamingException;
import javax.naming.NamingException;

/**
 * Created by Florian Stendel on 02.09.2016.
 */
@WebService(endpointInterface = "com.examples.wsdl.helloservice.HelloPortType",
               targetNamespace = "http://www.examples.com/wsdl/HelloService.wsdl")
public class EchoService implements HelloPortType {

    @Override
    public String sayHello(String firstName) {
        return null;
    }
}
