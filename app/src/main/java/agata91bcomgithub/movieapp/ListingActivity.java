package agata91bcomgithub.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.*;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {
    private static final String SEARCH_TITLE = "search_title";
    private MoviesListAdapter adapter;

    @BindView(R.id.flipper_view)
     ViewFlipper viewFlipper;

    @BindView(R.id.no_internet_image)
    ImageView noInternetImage;

    @BindView(R.id.recycler_view)
     RecyclerView recyclerView;



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
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        adapter = new MoviesListAdapter();
        recyclerView.setAdapter(adapter);


        getPresenter().getDataAsync(title)
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe(this::succes, this::error);

    }





    private void error(Throwable throwable) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
    }

    private void succes(MovieContainer movieContainer) {
        viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
        adapter.setItems(movieContainer.getItems());
    }

    public static Intent createIntent(Context context, String title) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        return intent;
    }

}
