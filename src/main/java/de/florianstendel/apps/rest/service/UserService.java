package de.florianstendel.apps.rest.service;

import de.florianstendel.apps.rest.entity.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

/**
 *
 * @author Florian Stendel
 */
@ApplicationScoped
public class UserService {


    private static Map<String, User> users = new HashMap<>();

    public List<User> listUsers(){

        return new ArrayList<>(users.values());
    }


    /**
     *
     *
     * @param user
     * @return true, if
     */
    public boolean createReplaceUser(final User user){

        //ToDo: Implementation not thread-safe
        boolean isReplaced = false;

        if(users.containsValue(user)){
            Set<Map.Entry<String,User>> entrySet = users.entrySet();
            for(Map.Entry<String, User> entry : entrySet){
                if(user != null && user.equals(entry.getValue())){
                    users.put(entry.getKey(),user);
                    isReplaced = true;
                }
            }
        }else{
            String nextIdKey = String.valueOf(users.size()+1);
            users.put(nextIdKey,user);
        }

        return isReplaced;
    }


    /**
     *
     *
     * @param user
     * @return true, if
     */
    public boolean createReplaceUser(final String id, final User user){

        User replacedUser = users.put(id,user);

        return (replacedUser != null);
    }


    /**
     * Deletes User by id and returns the
     *
     * @return User
     */
    public User deleteUser(final String id){

        return users.remove(id);
    }
}
