package com.thedeveloperworldisyours.feed.webservices;

import com.squareup.okhttp.OkHttpClient;
import com.thedeveloperworldisyours.feed.models.Feed;
import com.thedeveloperworldisyours.feed.utils.Constants;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by javiergonzalezcabezas on 29/3/15.
 */
public class Client {
    public interface ClientInterface{
        @GET("/")
        void getQuestions( @Query("nojsoncallback") String nojsoncallback, @Query("format") String format, Callback<Feed> callback);
    }

    public static ClientInterface initRestAdapter()
    {
        OkHttpClient client = new OkHttpClient();

        return (ClientInterface) new RestAdapter.Builder()
                .setClient(new OkClient(client))
                .setEndpoint(Constants.URL)
                .build()
                .create(ClientInterface.class);
    }
}
