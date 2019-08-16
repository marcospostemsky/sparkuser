package serviceImpl;

import service.IAPIService;
import conection.Conection;

import java.io.BufferedReader;
import java.io.IOException;

public class SitesAPIService implements IAPIService {
    public String getSites() {
        BufferedReader reader = Conection.get("http://localhost:8084/sites");

        try {
           return reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String getCategories(String id) {
        BufferedReader reader = Conection.get("http://localhost:8084/sites/"+id+"/categories");

        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public String existCategories(String id,String idCategory) {
        return null;
    }
}
