package agata91bcomgithub.movieapp.details.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import agata91bcomgithub.movieapp.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {
    private static final String URL_KEY = "url_key";

    @BindView(R.id.full_screen_image)
    ImageView fullScreenImqage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(URL_KEY);
        Glide.with(this).load(url).into(fullScreenImqage);


    }

    public static void startActivity (Activity activity, String url, ImageView imageView) {
        Intent intent = new Intent(activity, GalleryActivity.class);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, imageView, "gallery");
        intent.putExtra(URL_KEY, url);
        activity.startActivity(intent, optionsCompat.toBundle());
    }
}
