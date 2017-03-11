package agata91bcomgithub.movieapp.search;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioGroup;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.ItemTransformation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agata91bcomgithub.movieapp.R;
import agata91bcomgithub.movieapp.RetrofitProvider;
import agata91bcomgithub.movieapp.listing.ListingActivity;
import agata91bcomgithub.movieapp.listing.MovieItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private Map<Integer, String> apiKeyMap = new HashMap<Integer, String>(){{
        put(R.id.radio_movies, "movie");
        put(R.id.radio_episodes, "episode");
        put(R.id.radio_games, "game");
        put(R.id.radio_series, "series");
    }};

    @BindView(R.id.number_picker)
    NumberPicker numberPicker;
    @BindView(R.id.edit_text)
    TextInputEditText editText;

    @BindView(R.id.search_button)
    ImageView searchButton;

    @BindView(R.id.year_checkbox)
    CheckBox yearCheckBox;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.type_checkbox)
    CheckBox typeChecbox;

    @BindView(R.id.header_poster)
    RecyclerView posterHeaderRecyclerView;
    private PosterRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_main);
        ButterKnife.bind(this);


        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        numberPicker.setMinValue(1950);
        numberPicker.setMaxValue(year);
        numberPicker.setWrapSelectorWheel(true);

        adapter = new PosterRecyclerViewAdapter();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        posterHeaderRecyclerView.setLayoutManager(layoutManager);
        posterHeaderRecyclerView.setHasFixedSize(true);
        posterHeaderRecyclerView.setAdapter(adapter);
        posterHeaderRecyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        RetrofitProvider retrofitProvider = (RetrofitProvider) getApplication();
        Retrofit retrofit = retrofitProvider.provideRetrofit();
        SearchService searchService = retrofit.create(SearchService.class);
        searchService.search(1, "a*" , "2016", null )
                        .flatMap(searchResult -> Observable.fromIterable(searchResult.getItems())
                        .map(MovieItem::getPoster))
                        .filter(posterUrl -> !"N/A".equalsIgnoreCase(posterUrl))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toList()
                        .subscribe(this::success, this::error);


    }

    private void success(List<String> list){
        adapter.setUrls(list);
    }

    private void error(Throwable throwable){

    }

     @OnCheckedChanged (R.id.type_checkbox)
        void onTypeCheckboxStateChanged(CompoundButton buttonView, boolean isChecked){
         radioGroup.setVisibility(isChecked ? View.VISIBLE : View.GONE);

     }



    @OnCheckedChanged(R.id.year_checkbox)
    void onCheckboxStateChanged(CompoundButton buttonView, boolean isChecked){
        numberPicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.search_button)
    void onSearchButtonClick() {
        int checkRadioId = radioGroup.getCheckedRadioButtonId();

        String typeKey = typeChecbox.isChecked() ? apiKeyMap.get(checkRadioId): null;

        int year = yearCheckBox.isChecked() ? numberPicker.getValue() : ListingActivity.NO_YEAR_SELECTED;
        startActivity(ListingActivity.createIntent(SearchActivity.this,
                editText.getText().toString(),
                year,typeKey));


    }



}
