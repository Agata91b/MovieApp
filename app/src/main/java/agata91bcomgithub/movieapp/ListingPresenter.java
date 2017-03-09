package agata91bcomgithub.movieapp;


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
    private MovieContainer searchResult;

    public ListingPresenter(){
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.omdbapi.com/")
                .build();

    }

    public Observable<MovieContainer> getDataAsync(String title) {
        return retrofit.create(SearchService.class).search(title);

    }
}
