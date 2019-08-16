import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;

public class SitesAPIService implements IAPIService {
    public String getSites() {
        BufferedReader reader = getJSON.get("http://localhost:8084/sites");

        try {
           return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getCategories(String id) {
        BufferedReader reader = getJSON.get("http://localhost:8084/sites/"+id+"/categories");

        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String existCategories(String id,String idCategory) {
        return null;
    }
}
