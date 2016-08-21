package in.techtatva.techtatva.network;

import in.techtatva.techtatva.models.events.EventsListModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Naman on 7/6/2016.
 */
public class EventsAPIClient {
    private static Retrofit retrofit = null;

    public static APIInterface getInterface(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(CategoriesAPIClient.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit.create(APIInterface.class);
    }

    public interface APIInterface{
        @GET("bins/4jnwt")
        Call<EventsListModel> getEvents();
    }
}
