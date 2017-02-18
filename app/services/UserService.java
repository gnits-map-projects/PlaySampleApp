package services;

import models.User;

public class UserService {

    public void createUser(User user) {

        // TODO Save user in DB

    }

    public User authenticate(String username, String password) {

        // TODO Find username in DB and compare passwords
        // TODO If success, generate random auth_token and save in DB and return it from this method

        return hardCodedUser();
    }

    public User findUserByAuthToken(String authToken) {

        // TODO Find user in DB using authToken
        // TODO Verify token and expiration

        return hardCodedUser();
    }

    private User hardCodedUser() {
        return new User("mattias", "password", User.Role.USER);
    }

    private User hardCodedAdmin() {
        return new User("admin", "admin", User.Role.ADMIN);
    }

}
