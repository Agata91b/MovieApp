package agata91bcomgithub.movieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.annimon.stream.Stream;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(ListingPresenter.class)
public class ListingActivity extends NucleusAppCompatActivity<ListingPresenter> {
    private static final String SEARCH_TITLE = "search_title";
    private MoviesListAdapter adapter;
    private ViewFlipper viewFlipper;
    private ImageView noInternetImage;
    private  RecyclerView recyclerView;


    public void setDataOnUiThread(MovieContainer result, boolean isProblemWithInternetConnection) {
        runOnUiThread(() -> {
            if(isProblemWithInternetConnection){
                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(noInternetImage));
            }else{
                viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(recyclerView));
                adapter.setItems(result.getItems());
            }

        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        String title = getIntent().getStringExtra(SEARCH_TITLE);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new MoviesListAdapter();
        recyclerView.setAdapter(adapter);
        viewFlipper = (ViewFlipper)findViewById(R.id.flipper_view);
        noInternetImage = (ImageView) findViewById(R.id.no_internet_image);

        getPresenter().getDataAsync(title);

    }

    public static Intent createIntent(Context context, String title) {
        Intent intent = new Intent(context, ListingActivity.class);
        intent.putExtra(SEARCH_TITLE, title);
        return intent;
    }

}
