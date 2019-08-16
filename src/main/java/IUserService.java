import java.io.IOException;

public interface IUserService {

    public User getUser(String username, String password) throws IOException;
    public String findToken (String id);
    public int updateUser (String token, User user) throws IOException;
    public String userExists (String username, String password) throws IOException;
    public int addUser(User user) throws IOException;


}
