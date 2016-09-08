package de.florianstendel.apps.rest;

import de.florianstendel.apps.rest.misc.SuccessStatus;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Flori on 03.09.2016.
 */
@Path("/users")
public class UserResource {

    private static List<User> users = new ArrayList<>();

    @GET
    public List<User> getUsers(){

        return users;
    }


    @PUT
    @SuccessStatus(Response.Status.CREATED)
    public void addUser(User user){

        if(!users.contains(user)) {
            users.add(user);
        }
    }


    @DELETE
    @SuccessStatus(Response.Status.OK)
    public void removeUser(User user) {

        boolean isRemoved = users.remove(user);
        if(!isRemoved){
            throw new NotFoundException();
        }
    }
}
