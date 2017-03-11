package agata91bcomgithub.movieapp.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import agata91bcomgithub.movieapp.listing.MovieItem;

/**
 * Created by RENT on 2017-03-07.
 */

public class MovieContainer {

    @SerializedName("Search")
    private List<MovieItem> items;
    private String totalResults;
    @SerializedName("Response")
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
