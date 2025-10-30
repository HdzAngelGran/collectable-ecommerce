package org.arkn37;

import com.google.gson.Gson;
import org.arkn37.controller.ItemController;
import org.arkn37.controller.PageController;
import org.arkn37.controller.UserController;
import org.arkn37.exception.NotFoundException;
import org.arkn37.utils.ApiRoute;
import org.arkn37.utils.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.*;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final Gson gson = new Gson();
    private static final ItemController itemController = new ItemController();
    private static final UserController userController = new UserController();
    private static final PageController pageController = new PageController();

    public static void main(String[] args) {
        port(8080);

        path(ApiRoute.API_V1.toString(), () -> {
            before("/*", (q, a) -> log.info("Received api call"));

            path(ApiRoute.USERS.toString(), () -> {
                get("", userController::getByFilter, gson::toJson);
                get("/" + ApiRoute.PARAM_UUID, userController::getById, gson::toJson);
                post("", userController::add, gson::toJson);
                put("/" + ApiRoute.PARAM_UUID, userController::update, gson::toJson);
                delete("/" + ApiRoute.PARAM_UUID, userController::delete, gson::toJson);
                options("/" + ApiRoute.PARAM_UUID, userController::exist, gson::toJson);
            });

            path(ApiRoute.ITEMS.toString(), () -> {
                get("/", itemController::getByFilter, gson::toJson);
                get("/" + ApiRoute.PARAM_UUID, itemController::getById, gson::toJson);
                post("/", itemController::add, gson::toJson);
            });
        });

        path("/", () -> {
            get("", pageController::showItems, new MustacheTemplateEngine());
        });

        notFound(ExceptionHandler::resourceNotFound);
        internalServerError(ExceptionHandler::internalServerError);
        exception(NotFoundException.class, ExceptionHandler::dataNotFound);
        exception(IllegalArgumentException.class, ExceptionHandler::badRequest);
        exception(Exception.class, ExceptionHandler::exception);
    }
}
