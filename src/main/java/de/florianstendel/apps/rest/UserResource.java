package de.florianstendel.apps.rest;

import de.florianstendel.apps.rest.entity.Fault;
import de.florianstendel.apps.rest.entity.User;
import de.florianstendel.apps.rest.service.UserService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

/**
 * Class implementing an user resource of a RESTful API.
 *
 * @author Florian Stendel
 */
@Path("/users")
public class UserResource {

    @Inject
    private UserService userService;

    /**
     * Gets a list of currently stored users.
     *
     * @return List of stored users.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(){

        try{
            return userService.listUsers();
        }catch(Throwable e){
            throw new ServerErrorException(
                    Response.serverError()
                            .entity(new Fault("EX00001","Internal Server Error.","Internal Server Error."))
                            .build());
        }
    }


    /**
     * Creates a user resource at the specified resource identifier.
     *
     * @param id Identifier of the resouce to create.
     * @param user Data used to create User.
     * @param httpServletResponse Reference to the response context.
     *
     * @return The user created/replaced.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@PathParam("id")final String id, final User user, @Context HttpServletResponse httpServletResponse){

        try {
            boolean isUserReplaced = userService.createReplaceUser(id, user);

            if (isUserReplaced) {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            }
            return user;
        }catch(Throwable e){
            throw new ServerErrorException(
                    Response.serverError()
                            .entity(new Fault("EX00001","Internal Server Error.","Internal Server Error."))
                            .build());
        }
    }

    /**
     * Creates a user resource choosing an appropiate resource identifier.
     *
     * @param user Data used to create user.
     * @return The user created/replaced.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(final User user, @Context HttpServletResponse httpServletResponse){

        try {
            boolean isUserReplaced = userService.createReplaceUser(user);

            if (isUserReplaced) {
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            }

            return user;
        }catch(Throwable e){
            throw new ServerErrorException(
                    Response.serverError()
                            .entity(new Fault("EX00001","Internal Server Error.","Internal Server Error."))
                            .build());
        }
}

    /**
     * Deletes the resource at the location specified by {id}.
     *
     * @param id Id of the resource to delete.
     * @return Content of the deleted resource previously associated with the given id..
     * @throws NotFoundException If no data has been found for the given id.
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User removeUser(@PathParam("id") final String id) throws NotFoundException{

        try {
            User removedUser = userService.deleteUser(id);

            if (removedUser != null) {
                return removedUser;
            } else {
                throw new NotFoundException(
                        Response.status(Response.Status.NOT_FOUND)
                                .entity(new Fault("EX00002", "Entity not found.", "Entity not found."))
                                .build());
            }
        }catch(Throwable e){
            throw new ServerErrorException(
                Response.serverError()
                        .entity(new Fault("EX00001","Internal Server Error.","Internal Server Error."))
                        .build());
        }
    }
}
