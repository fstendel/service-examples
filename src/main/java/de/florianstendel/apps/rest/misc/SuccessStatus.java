package de.florianstendel.apps.rest.misc;

import javax.ws.rs.NameBinding;
import javax.ws.rs.core.Response;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Flori on 04.09.2016.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessStatus {

    Response.Status value() default Response.Status.OK;

}
