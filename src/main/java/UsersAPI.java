import com.google.gson.Gson;

import static spark.Spark.*;

public class UsersAPI {

    public static void main(String[] args) {


        final IUserService userService = new UserServiceSE();
        port(8086);

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
