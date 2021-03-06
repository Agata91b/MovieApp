package agata91bcomgithub.movieapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RENT on 2017-03-07.
 */

public class MovieContainer {

    @SerializedName("Search")
    private List<MovieItem> items;
    private String totalResults;
    private String response;

    public List<MovieItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
