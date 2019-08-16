import com.google.gson.Gson;

import java.io.IOException;

public class AddUsers {

    public static void main(String[] args) {
        IUserService serviceSE = new UserServiceSE();

        try {
            User user = serviceSE.getUser("agustin","123456");
            if ( user != null){
                System.out.println(new Gson().toJson(user));
            } else{
                System.out.println("Usuario y contraseña incorrectos");
            }

            System.out.println(serviceSE.userExists("agustinh","123456"));

            serviceSE.updateUser("CambioToken", new User("agustin","123456"));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
