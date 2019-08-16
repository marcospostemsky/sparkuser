package serviceImpl;

import clase.User;
import com.google.gson.Gson;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import service.IUserService;

import java.io.IOException;

public class UserServiceSE implements IUserService {

    private int port=9200;
    public RestHighLevelClient client;

    public User getUser(String username, String password) throws IOException {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",
                port, "http")));

        SearchRequest searchRequest = new SearchRequest("users");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("username",username));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest , RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] searchHitsArr = searchHits.getHits();


        client.close();


        if (searchHitsArr.length == 1){
            User user = new Gson().fromJson(searchHitsArr[0].getSourceAsString(), User.class);
            if (user.getPassword().equals(password)){
                return user;
            }
        }

        return null;

    }

    public String findToken(String id) {

        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",
                port, "http")));
        GetRequest getRequest = new GetRequest("users","user",id);
        GetResponse getResponse = null;
        try {
            getResponse = client.get(getRequest, RequestOptions.DEFAULT);

            User user = new Gson().fromJson(getResponse.getSourceAsString(), User.class);


            client.close();

            return user.getToken();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public int updateUser(String token, User user) throws IOException {
        String id = this.userExists(user.getUsername(), user.getPassword());
        user.setToken(token);

        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", port, "http")));

        //Convierte a json string para IndexRequest
        String jsonString = new Gson().toJson(new Gson().toJsonTree(user));

        UpdateRequest updateRequest = new UpdateRequest("users","user",id);
        updateRequest.doc(jsonString,XContentType.JSON);

        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);


        client.close();


        return 0;
    }

    public String userExists(String username, String password) throws IOException {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",
                port, "http")));

        SearchRequest searchRequest = new SearchRequest("users");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("username",username));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest , RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] searchHitsArr = searchHits.getHits();


        client.close();


        if (searchHitsArr.length == 1){
            User user = new Gson().fromJson(searchHitsArr[0].getSourceAsString(), User.class);
            if (user.getPassword().equals(password)){
                return searchHitsArr[0].getId();
            }
        }

        return "";
    }

    public int addUser(User user) throws IOException {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", port, "http")));

        //Convierte a json string para IndexRequest
        String jsonString = new Gson().toJson(new Gson().toJsonTree(user));

        IndexRequest indexRequest = new IndexRequest("users","user","username")
                .source(jsonString,XContentType.JSON);

        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT );

        client.close();
        return indexResponse.hashCode();
    }
}
