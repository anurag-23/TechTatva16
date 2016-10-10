package in.techtatva.techtatva.network;

import in.techtatva.techtatva.models.categories.CategoriesListModel;
import in.techtatva.techtatva.models.events.EventsListModel;
import in.techtatva.techtatva.models.events.ScheduleListModel;
import in.techtatva.techtatva.models.results.ResultsListModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by anurag on 28/9/16.
 */
public class APIClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://api.mitportals.in/";

    public static APIInterface getInterface(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit.create(APIInterface.class);
    }


    public interface APIInterface{

        @GET("results/")
        Call<ResultsListModel> getResults();

        @GET("schedule/")
        Call<ScheduleListModel> getSchedule();

        @GET("events/")
        Call<EventsListModel> getEvents();

        @GET("categories/")
        Call<CategoriesListModel> getCategories();

    }
}
