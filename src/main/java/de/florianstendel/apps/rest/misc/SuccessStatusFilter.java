package de.florianstendel.apps.rest.misc;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Filter to override returned http success codes on methods annotated with @SuccessStatus.
 *
 * Used to override JAX-RS default behaviour on PUT/POST void methods (e.g. overriding the "No Content" returns)
 *
 * Created on 04.09.2016.
 */
@Provider
@SuccessStatus
public class SuccessStatusFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {

        if(isInSuccessRange(containerResponseContext.getStatus())){

            final Annotation[] annotations =containerResponseContext.getEntityAnnotations();
            for(Annotation annotation : annotations){

                if(annotation instanceof SuccessStatus){

                    final int annotationSuccessStatus = ((SuccessStatus)annotation).value().getStatusCode();
                    if(isInSuccessRange(annotationSuccessStatus)){

                        containerResponseContext.setStatus(annotationSuccessStatus);
                    }
                }
            }
        }
    }

    private boolean isInSuccessRange(final int statusCode){
        return statusCode >= 200 && statusCode < 300;

    }
}
