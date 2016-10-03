package in.techtatva.techtatva.network;

import in.techtatva.techtatva.models.categories.CategoriesListModel;
import retrofit2.Call;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by anurag on 29/6/16.
 */
public class CategoriesAPIClient {

    public static final String BASE_URL="https://api.myjson.com/";
    private static Retrofit retrofit = null;

    public static APIInterface getInterface(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit.create(APIInterface.class);
    }

    public interface APIInterface {
        @GET("bins/1c06o")
        Call <CategoriesListModel> getCategories();

    }
}

