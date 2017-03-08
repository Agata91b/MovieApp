package agata91bcomgithub.movieapp;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import nucleus.presenter.Presenter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> {

    public void getDataAsync(String title) {
        new Thread() {

            @Override
            public void run() {
                try {
                    String result = getData(title);
                    MovieContainer searchResult = new Gson().fromJson(result, MovieContainer.class);
                    getView().setDataOnUiThread(searchResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }


    public String getData(String title) throws IOException {
        String stringUrl = "https://www.omdbapi.com/?s=" + title;
        URL url = new URL(stringUrl);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        return convertStreamToString(inputStream);


    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : " ";

    }
}
