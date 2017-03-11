package agata91bcomgithub.movieapp.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import agata91bcomgithub.movieapp.RetrofitProvider;
import agata91bcomgithub.movieapp.search.MovieContainer;
import agata91bcomgithub.movieapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.*;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {
    private static final String SEARCH_TITLE = "search_title";
    private static final String SEARCH_YEAR = "search_year";
    public static final  int NO_YEAR_SELECTED = -1;
    private static final String SEARCH_TYPE = "search_type";
    private MoviesListAdapter adapter;

    @BindView(R.id.flipper_view)
     ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_image)
    ImageView noInternetImage;

    @BindView(R.id.recycler_view)
     RecyclerView recyclerView;

    @BindView(R.id.no_results)
    FrameLayout noResults;

    String title;
    int year;

//    public void setDataOnUiThread(MovieContainer result, boolean isProblemWithInternetConnection) {
//        runOnUiThread(() -> {
//            if(isProblemWithInternetConnection){
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
//            }else{
//                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
//                adapter.setItems(result.getItems());
//            }
//
//        }); }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        ButterKnife.bind(this);

        if(savedInstanceState == null) {
            RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
            getPresenter().setRetrofit(retrofitProvider.provideRetrofit());

        }
        title = getIntent().getStringExtra(SEARCH_TITLE);
        year = getIntent().getIntExtra(SEARCH_YEAR, NO_YEAR_SELECTED);

        String type = getIntent().getStringExtra(SEARCH_TYPE);
        adapter = new MoviesListAdapter();
        recyclerView.setAdapter(adapter);


        getPresenter().getDataAsync(title, year, type)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(this::success, this::error);

    }





    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    private void success(MovieContainer movieContainer) {
        if("false".equalsIgnoreCase(movieContainer.getResponse())){
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResults));
        }else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
            adapter.setItems(movieContainer.getItems());
        }
    }

    public static Intent createIntent(Context context, String title, int year, String type) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, type);
        return intent;
    }

}
