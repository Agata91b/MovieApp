package agata91bcomgithub.movieapp.search;

import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import agata91bcomgithub.movieapp.R;
import agata91bcomgithub.movieapp.listing.OnMovieDetailsClickListener;

import static butterknife.ButterKnife.*;

/**
 * Created by RENT on 2017-03-11.
 */

public class PosterRecyclerViewAdapter extends RecyclerView.Adapter<PosterRecyclerViewAdapter.ViewHolder> {


    private List<SimpleMovieItem> simpleMovieItems = Collections.emptyList();
    private OnMovieDetailsClickListener onMovieDetailsClikListener;

    public void setOnMovieDetailsClikListener(OnMovieDetailsClickListener onMovieDetailsClikListener) {
        this.onMovieDetailsClikListener = onMovieDetailsClikListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View layout =  LayoutInflater.from(parent.getContext())
               .inflate(R.layout.poster_layout, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(holder.posterImageView.getContext()).load(simpleMovieItems.get(position).getPoster())
                .into(holder.posterImageView);
        holder.posterImageView.setOnClickListener(v -> {
            if(onMovieDetailsClikListener!= null){
                onMovieDetailsClikListener.onMovieDetailsClick(simpleMovieItems.get(position).getImdbID());
            }
        });

    }

    @Override
    public int getItemCount() {
        return simpleMovieItems.size();
    }

    public void setSimpleMovieItems(List<SimpleMovieItem> simpleMovieItems) {
        this.simpleMovieItems = simpleMovieItems;
        notifyDataSetChanged();
    }




    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = findById(itemView, R.id.poster_image_view);

        }
    }

}
