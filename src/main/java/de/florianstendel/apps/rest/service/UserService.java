package de.florianstendel.apps.rest.service;

import de.florianstendel.apps.rest.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Flori on 02.10.2016.
 */
@ApplicationScoped
public class UserService {


    private static Map<String, User> users = new HashMap<>();

    public List<User> getUsers(){
        return new ArrayList<>(users.values());
    }
}
