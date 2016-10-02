package de.florianstendel.apps.rest;

import de.florianstendel.apps.rest.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.security.KeyStore;
import java.util.*;

/**
 * Created by Florian Stendel on 03.09.2016.
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
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(){


        return new ArrayList<User>(users.values());
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
            throw new BadRequestException();
        }
    }

    /**
     * Creates a user resource choosing an appropiate resource identifier.
     *
     * @param user
     * @return The user created/replaced.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(final User user){

        try {
            Set<Map.Entry<String,User>> userEntrySet = users.entrySet();
            boolean isReplaced = false;

            for(Map.Entry entry : userEntrySet){

                if(entry.getValue() != null && entry.getValue().equals(user)){
                    users.put((String)entry.getKey(), (User)entry.getValue());
                    isReplaced = true;
                    break;
                }
            }

            if(isReplaced == false){
                String nextPosition = String.valueOf(users.size() + 1);
                users.put(nextPosition,user);
            }

            return user;

        }catch(IllegalArgumentException | UnsupportedOperationException | ClassCastException e) {
            throw new BadRequestException();
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

        throw new NotFoundException();
    }
}
