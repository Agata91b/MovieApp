package agata91bcomgithub.movieapp.listing;


import java.util.Iterator;

import agata91bcomgithub.movieapp.search.MovieContainer;
import agata91bcomgithub.movieapp.search.SearchService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;




/**
 * Created by RENT on 2017-03-07.
 */

public class ListingPresenter extends Presenter<ListingActivity> implements  OnLoadNextPageListener{


    private ResultAggregator resultAggregator =  new ResultAggregator();

    public Retrofit retrofit;
    private String title;
    private String stringYear;
    private String type;
    private boolean isLoadingFromStart;






    public void startLoadingItems(String title, int year, String type) {
        this.title = title;
        this.type = type;
        stringYear = year == ListingActivity.NO_YEAR_SELECTED ? null : String.valueOf(year);

        if(resultAggregator.getMovieDetails().size()  == 0) {
            loadNextPage(1);
            isLoadingFromStart =true;

        }
    }

    @Override
    protected void onTakeView(ListingActivity listingActivity) {
        super.onTakeView(listingActivity);
        if (!isLoadingFromStart) {
            listingActivity.setNewAggregatorResult(resultAggregator);
        }
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public  void loadNextPage(int page){
        isLoadingFromStart =false;
        retrofit.create(SearchService.class).search(page, title,
                stringYear, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieContainer ->{
                    resultAggregator.addNewItems(movieContainer.getItems());
                    resultAggregator.setTotalItemsReusult(Integer.parseInt(movieContainer.getTotalResults()));
                    resultAggregator.setResponse(movieContainer.getResponse());
                    getView().setNewAggregatorResult(resultAggregator);
                }, throwable -> {
                    //nap
                });

    }
}
