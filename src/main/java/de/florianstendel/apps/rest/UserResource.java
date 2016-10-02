package de.florianstendel.apps.rest;

import de.florianstendel.apps.rest.entity.Error;
import de.florianstendel.apps.rest.entity.User;
import de.florianstendel.apps.rest.service.UserService;

import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class UserResource {

    @Inject
    private UserService userService;

    private static Map<String, User> users = new HashMap<>();


    /**
     * Gets a list of currently stored users.
     *
     * @return List of stored users.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(){

        return userService.getUsers();
       // return new ArrayList<>(users.values());
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
            User previousExistingUser = users.put(id, user);

            if(previousExistingUser !=null){
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            }else {
                httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            }

            return user;

        }catch(IllegalArgumentException | UnsupportedOperationException | ClassCastException e) {
            throw new BadRequestException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new Error("EX00002","Bad request arguments.","Bad request arguments."))
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
            Set<Map.Entry<String,User>> userEntrySet = users.entrySet();
            boolean isReplaced = false;

            for(Map.Entry entry : userEntrySet){

                if(entry.getValue() != null && entry.getValue().equals(user)){
                    users.put((String)entry.getKey(), (User)entry.getValue());
                    isReplaced = true;
                    httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    break;
                }
            }

            if(!isReplaced){
                String nextPosition = String.valueOf(users.size() + 1);
                users.put(nextPosition,user);
                httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            }

            return user;

        }catch(IllegalArgumentException | UnsupportedOperationException | ClassCastException e) {
            throw new BadRequestException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new Error("EX00002","Bad request arguments.","Bad request arguments."))
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

        User removedUser = users.remove(id);

        if(removedUser != null)
            return removedUser;

        throw new NotFoundException(
                Response.status(Response.Status.NOT_FOUND)
                        .entity(new Error("EX00002","Entity not found.","Entity not found."))
                        .build());
    }
}
