package agata91bcomgithub.movieapp.listing;


import agata91bcomgithub.movieapp.search.MovieContainer;
import agata91bcomgithub.movieapp.search.SearchService;
import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> {

    public Retrofit retrofit;

    public Observable<MovieContainer> getDataAsync(String title, int year, String type) {
        String stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);
        return retrofit.create(SearchService.class).search(title,
                stringYear, type);

    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
}