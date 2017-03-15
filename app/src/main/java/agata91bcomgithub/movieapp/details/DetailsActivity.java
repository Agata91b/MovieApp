package agata91bcomgithub.movieapp.details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import agata91bcomgithub.movieapp.RetrofitProvider;
import agata91bcomgithub.movieapp.details.gallery.GalleryActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static java.lang.String.valueOf;

@RequiresPresenter(DetailsPresenter.class)
public class DetailsActivity extends NucleusAppCompatActivity<DetailsPresenter> {
    private static final String ID_KEY ="id_key";
    private Disposable subscribe;
    @BindView(R.id.poster)
    ImageView poster;

    @BindView(R.id.title_and_year)
    TextView titleAndYear;

    @BindView(R.id.type)
    TextView type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String imdbId = getIntent().getStringExtra(ID_KEY);


        ButterKnife.bind(this);


        
        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        getPresenter().setRetrofit(retrofitProvider.provideRetrofit());

        subscribe = getPresenter().loadDetails(imdbId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::success, this::error);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscribe != null){
            subscribe.dispose();
        }
    }

    public void success(MovieDetails movieDetails){
        Glide.with(this).load(movieDetails.getPoster()).into(poster);

        poster.setOnClickListener(v -> {
            GalleryActivity.startActivity(DetailsActivity.this, movieDetails.getPoster(), poster);
        });

    }
    public void error(Throwable throwable){

    }

    public  static Intent createIntent(Context context, String imdbID) {
       Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(ID_KEY, imdbID);
        return intent;

    }
}
