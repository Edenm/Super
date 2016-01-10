package model;

/**
 * Created by MOR on 1/9/2016.
 */

/**
 * This class is represent one User in system
 * User class included all data about user like userName and password
 */
public class User {
    /** user name of the user */
    String userName;
    /** password of the user */
    String password;

    /**
     * Full cto'r
     * @param name
     * @param pass
     */
    public User(String name, String pass){
        super();
        this.userName = name;
        this.password = pass;
    }

    /**
     * @return userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @return password
     */
    public String getPassword()
    {
        return password;
    }

}
