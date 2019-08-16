package api;

import com.google.gson.Gson;

import static spark.Spark.*;

import response.StandarResponseLogin;
import service.IUserService;
import serviceImpl.LoginServiceToken;
import serviceImpl.SitesAPIService;
import serviceImpl.UserServiceSE;

public class UsersAPI {

    public static void main(String[] args) {


        final IUserService userService = new UserServiceSE();
        port(8086);

        /**
         * Recibe POST de logeo, y verificas que el username y password (recibidos por headers) existan en la base de
         * datos, si es correcto devuelve un token y id de usuario por el body.
         */
        post("/users" , (req , res) -> {
            res.type("application/json");
            String username = req.headers("username");
            String password = req.headers("password");
            String token = new LoginServiceToken().loginUser(userService.getUser(username,password));


            res.status(202);
            if (token != null) {
                return new Gson().toJson(new StandarResponseLogin(userService.userExists(username,password),username,token));
            }
            res.status(401);
            return "{ \"StatusCode\": \"Autentificación fallida\"}";

        });

        /**
         * Recibe GET a /sites, verificando token y id (recibidos por headers), si todo es correcto devuelve todos los
         * sites de la API de Meli.
         */

        get("/sites", (req , res) -> {
            res.type("application/json");
            String id = req.headers("id");
            String token = req.headers("token");
            res.status(200);


            if ( token.equals(userService.findToken(id))){
                return new SitesAPIService().getSites();
            }

            res.status(403);
            return "{ \"StatusCode\": \"Token incorrecto\"}";
        });

        /**
         * Recibe GET a /sites/:id/categories, verificando token y id (recibidos por headers), si todo es correcto
         * devuelve todos las categorias del site con :id de la API de Meli.
         */

        get("/sites/:id/categories", (req , res) -> {
            res.type("application/json");
            String id = req.headers("id");
            String token = req.headers("token");
            res.status(200);

            if ( token.equals(userService.findToken(id))){
                return new SitesAPIService().getCategories(req.params(":id"));
            }

            res.status(403);
            return "{ \"StatusCode\": \"Token incorrecto\"}";
        });

        /**
         * Recibe GET a /sites/:id/categories/:idCat/:item, verificando token y id (recibidos por headers), si todo es
         * correcto, solo devuelve un 200 para que la API ITEMS pueda verificar antes de almacenar el item.
         */

        get("/sites/:id/categories/:idCat/:item", (req , res) -> {
            res.type("application/json");
            String id = req.headers("id");
            String token = req.headers("token");
            res.status(200);

            if ( token.equals(userService.findToken(id))){
                return "{ \"StatusCode\": \"OK\"}";
            }

            res.status(403);
            return "{ \"StatusCode\": \"Token incorrecto\"}";
        });

    }

}
