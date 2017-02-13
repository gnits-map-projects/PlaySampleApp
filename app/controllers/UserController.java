package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import javax.inject.Inject;

public class UserController extends Controller {

    private UserService userService;

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Result createUser() {

        return status(NOT_IMPLEMENTED);
    }

    public Result getUser(Integer id) {

        return status(NOT_IMPLEMENTED);
    }

    public Result updateUser(Integer id) {

        return status(NOT_IMPLEMENTED);
    }

    public Result deleteUser(Integer id) {

        return status(NOT_IMPLEMENTED);
    }

    public Result signIn() {

        return status(NOT_IMPLEMENTED);
    }

    public Result signOut() {

        return status(NOT_IMPLEMENTED);
    }

    public Result getCurrentUser() {

        return status(NOT_IMPLEMENTED);
    }

}
