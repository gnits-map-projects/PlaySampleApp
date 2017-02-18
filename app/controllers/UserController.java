package controllers;

import controllers.security.Authenticator;
import controllers.security.IsAdmin;
import controllers.security.IsAdminAction;
import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
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

    @Authenticator
    //@IsAdmin
    public Result getCurrentUser() {

        /*
        final String authorized = request().getHeader("Authorization");
        Logger.debug("Authorized header: {}", authorized);

        if (!authorized.startsWith("Bearer ")) {
            return unauthorized();
        }

        final String authToken = authorized.substring(7);
        if (authorized.isEmpty()) {
            return unauthorized();
        }

        Logger.debug("auth_token: {}", authToken);

        final User user = userService.findUserByAuthToken(authToken);
        if (null == user) {
            return unauthorized();
        }

        Logger.debug("user: {}", user);

        */

        final User user = (User) ctx().args.get("user");

        /*
        if (user.getRole() != User.Role.ADMIN) {
            return forbidden();
        }
        */


        // TODO do my stuff

        return ok(Json.toJson(user));
    }

}
