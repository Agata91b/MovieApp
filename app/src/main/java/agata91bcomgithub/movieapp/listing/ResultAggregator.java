package agata91bcomgithub.movieapp.listing;

import java.util.ArrayList;
import java.util.List;

import agata91bcomgithub.movieapp.details.MovieDetails;

/**
 * Created by RENT on 2017-03-15.
 */

public class ResultAggregator {

        private String response;
        private List<MovieItem> movieDetails = new ArrayList<>();
        private int totalItemsReusult;

    public void setTotalItemsReusult(int totalItemsReusult) {
        this.totalItemsReusult = totalItemsReusult;
    }
    public void addNewItems(List<MovieItem> newItems){
        movieDetails.addAll(newItems);
    }

    public List<MovieItem> getMovieDetails() {
        return movieDetails;
    }

    public int getTotalItemsReusult() {
        return totalItemsReusult;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
