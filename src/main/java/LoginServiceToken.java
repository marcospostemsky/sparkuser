import java.io.IOException;
import java.security.SecureRandom;

public class LoginServiceToken implements ILoginService {
    public String loginUser(User user) {

        if (user != null){
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String token = bytes.toString();

            try {
                new UserServiceSE().updateUser(token,user);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
