package agata91bcomgithub.movieapp.listing;


import agata91bcomgithub.movieapp.search.MovieContainer;
import agata91bcomgithub.movieapp.search.SearchService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> implements  OnLoadNextPageListener{

    private MovieContainer serachResults;

    public Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;




    public Observable<MovieContainer> getDataAsync( String title, int year, String type) {
        this.title = title;
        this.type = type;
        stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);

        return retrofit.create(SearchService.class).search(1, title,
                stringYear, type);

    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public  void loadNextPage(int page){
        retrofit.create(SearchService.class).search(page, title,
                stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieContainer ->{
                    getView().appendItems(movieContainer);
                }, throwable -> {
                    //nap
                });

    }
}
