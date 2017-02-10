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

    public Result register() {

        return status(NOT_IMPLEMENTED);
    }

    public Result signIn() {

        return status(NOT_IMPLEMENTED);
    }

    public Result signOut(String token) {

        return status(NOT_IMPLEMENTED);
    }

    public Result setRole(Integer role) {

        return status(NOT_IMPLEMENTED);
    }

    public Result status(Integer status) {

        return status(NOT_IMPLEMENTED);
    }

    public Result delete() {

        return status(NOT_IMPLEMENTED);
    }

}
