package agata91bcomgithub.movieapp.details;

import android.os.Bundle;
import android.support.annotation.Nullable;

import agata91bcomgithub.movieapp.RetrofitProvider;
import io.reactivex.Observable;
import nucleus.presenter.Presenter;
import retrofit2.Retrofit;

/**
 * Created by RENT on 2017-03-13.
 */

public class DetailsPresenter extends Presenter<DetailsActivity> {

    private Retrofit retrofit;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }



        public Observable<MovieDetails> loadDetails(String imdbID) {
        DetailService detailService = retrofit.create(DetailService.class);
        return detailService.getDetailInfo(imdbID);
    }
}
