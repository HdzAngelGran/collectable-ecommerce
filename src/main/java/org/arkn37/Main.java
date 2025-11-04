package org.arkn37;

import com.google.gson.Gson;
import org.arkn37.controller.ItemController;
import org.arkn37.controller.OfferController;
import org.arkn37.controller.PageController;
import org.arkn37.controller.UserController;
import org.arkn37.exception.NotFoundException;
import org.arkn37.repository.ItemRepository;
import org.arkn37.repository.OfferRepository;
import org.arkn37.repository.UserRepository;
import org.arkn37.service.ItemService;
import org.arkn37.service.OfferService;
import org.arkn37.service.UserService;
import org.arkn37.utils.ApiRoute;
import org.arkn37.utils.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.*;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final Gson gson = new Gson();

    private static final ItemRepository itemRepository = new ItemRepository();
    private static final ItemService itemService = new ItemService(itemRepository);
    private static final ItemController itemController = new ItemController(itemService);

    private static final UserRepository userRepository = new UserRepository();
    private static final UserService userService = new UserService(userRepository);
    private static final UserController userController = new UserController(userService);

    private static final OfferRepository offerRepository = new OfferRepository();
    private static final OfferService offerService = new OfferService(offerRepository);
    private static final OfferController offerController = new OfferController(offerService);

    private static final PageController pageController = new PageController(itemService, offerService);

    public static void main(String[] args) {
        port(8080);

        staticFiles.location("/public");

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
                before("/*", (q, a) -> log.info("Received api call items"));
                get("", itemController::getByFilter, gson::toJson);
                post("", itemController::add, gson::toJson);
                path("/" + ApiRoute.PARAM_UUID, () -> {
                    get("", itemController::getById, gson::toJson);
                    get("/offers", offerController::getByItemUuid, gson::toJson);
                    post("/offers", offerController::add, gson::toJson);
                });
            });
        });

        path("/", () -> {
            get("", pageController::showItems, new MustacheTemplateEngine());
            get("/" + ApiRoute.PARAM_UUID + "/offers", pageController::itemOffers, new MustacheTemplateEngine());
        });

        notFound(ExceptionHandler::resourceNotFound);
        internalServerError(ExceptionHandler::internalServerError);
        exception(NotFoundException.class, ExceptionHandler::dataNotFound);
        exception(IllegalArgumentException.class, ExceptionHandler::badRequest);
        exception(Exception.class, ExceptionHandler::exception);
    }
}
