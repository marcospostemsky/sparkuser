public interface ILoginService {
    public String loginUser(User user); //Debe devolver un token si user es distinto de null, además debe almacenar el token en el usuario.
}
