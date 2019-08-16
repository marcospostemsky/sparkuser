// El metodo get de la clase getJson retorna un Buffered Reader de la url que se le envíe

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class getJSON {

    public static BufferedReader get(String urlGet){
        try {
            URL url = new URL(urlGet);

            try {
                URLConnection urlconnection = url.openConnection();
                urlconnection.setRequestProperty("Accept","application/json");
                urlconnection.setRequestProperty("User-Agent","Mozilla/5.0");
                if(urlconnection instanceof HttpURLConnection) {
                    HttpURLConnection connection = (HttpURLConnection) urlconnection;
                    return  (new BufferedReader(new InputStreamReader(connection.getInputStream())));

                } else {
                    System.out.println("URL inválida");
                    return  (null);
                }


            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }

        } catch (MalformedURLException exception) {
            System.out.println(exception.getMessage());
        }
        return  (null);
    }

}
