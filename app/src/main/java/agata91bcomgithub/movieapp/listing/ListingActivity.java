package agata91bcomgithub.movieapp.listing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import agata91bcomgithub.movieapp.RetrofitProvider;
import agata91bcomgithub.movieapp.details.DetailsActivity;
import agata91bcomgithub.movieapp.search.MovieContainer;
import agata91bcomgithub.movieapp.R;
import agata91bcomgithub.movieapp.search.SearchService;
import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;


import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.*;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> implements CurrentItemListener, ShowOrHideCounter, OnMovieDetailsClickListener {
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

    @BindView(R.id.counter)
    TextView counter;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    String title;
    int year;
    String type;

    private EndlessScrollListener endlessScrollListener;

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
        type = getIntent().getStringExtra(SEARCH_TYPE);

        adapter = new MoviesListAdapter();
        adapter.setOnMovieDetailsClickListener(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        endlessScrollListener = new EndlessScrollListener(layoutManager, getPresenter());
        recyclerView.addOnScrollListener(endlessScrollListener);
        endlessScrollListener.setCurrentItemListener(this);
        endlessScrollListener.setShowOrHideCounter(this);

        swipeRefreshLayout.setOnRefreshListener(() -> startLoading(title, year, type));

        startLoading(title, year, type);
        counter.setTranslationX(counter.getWidth() * 2);

    }

    private void startLoading(String title,  int year, String type) {
        getPresenter().getDataAsync(title, year, type)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(this::success, this::error);
    }

    private void error(Throwable throwable) {
        swipeRefreshLayout.setRefreshing(false);
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }


    public void appendItems(MovieContainer movieContainer){
        adapter.addItems(movieContainer.getItems());
        endlessScrollListener.setTotalsItemsNumber(Integer.parseInt(movieContainer.getTotalResults()));
    }

    private void success(MovieContainer movieContainer) {
        swipeRefreshLayout.setRefreshing(false);
        if("false".equalsIgnoreCase(movieContainer.getResponse())){
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noResults));
        }else {
            viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(swipeRefreshLayout));
            adapter.setItems(movieContainer.getItems());
            endlessScrollListener.setTotalsItemsNumber(Integer.parseInt(movieContainer.getTotalResults()));
        }
    }

    public static Intent createIntent(Context context, String title, int year, String type) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        intent.putExtra(SEARCH_YEAR, year);
        intent.putExtra(SEARCH_TYPE, type);
        return intent;
    }

    @Override
    public void onNewCurrentItem(int currentItem, int totalItemsCount) {
        counter.setText(currentItem + "/" + totalItemsCount);
    }

    @Override
    public void showCounter() {
        counter.setVisibility(View.VISIBLE);
        counter.animate().translationX(0).start();
    }

    @Override
    public void hideCounter() {
        counter.animate().translationX(counter.getWidth() * 2).start();
    }

    @Override
    public void onMovieDetailsClick(String imdbID) {
        startActivity(DetailsActivity.createIntent(this, imdbID));

    }
}
