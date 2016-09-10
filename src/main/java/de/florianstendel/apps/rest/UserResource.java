package de.florianstendel.apps.rest;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Flori on 03.09.2016.
 */
@Path("/users")
public class UserResource {

    private static Map<String, User> users = new HashMap<String,User>();


    /**
     * Gets a list of currently stored users.
     *
     * @return List of stored users.
     */
    @GET
    public List<User> getUsers(){


        return new ArrayList<User>(users.values());
    }


    /**
     * Creates a user resource at the specified resource identifier.
     *
     * @param id Identifier of the resouce to create.
     * @param user User data.
     * @param httpServletResponse Object for accessing responses details.
     */
    @PUT
    @Path("/{id}")
    public void addUser(@PathParam("id")final String id, final User user, @Context final HttpServletResponse httpServletResponse){

        try {
            users.put(id, user);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);

        }catch(IllegalArgumentException | UnsupportedOperationException | ClassCastException e) {
            throw new BadRequestException();
        }
    }


    /**
     * Deletes the resource at the location specified by {id}.
     *
     * @param id Id of the resource to delete.
     * @return Content of the deleted resource on success or null if resource for specified did not exist.
     */
    @DELETE
    @Path("/{id}")
    public User removeUser(@PathParam("id") final String id) {

        User removedUser = users.remove(id);

        if(removedUser != null)
            return removedUser;

        throw new NotFoundException();
    }
}
