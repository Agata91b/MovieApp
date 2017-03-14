package agata91bcomgithub.movieapp.listing;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Collections;
import java.util.List;

import agata91bcomgithub.movieapp.R;
import agata91bcomgithub.movieapp.RetrofitProvider;
import butterknife.ButterKnife;

/**
 * Created by RENT on 2017-03-08.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int GAMES_VIEW_HOLDER = 1;
    private static  final int MY_VIEW_HOLDER = 2;

    private List<MovieItem> items = Collections.emptyList();
    private OnMovieDetailsClickListener onMovieDetailsClickListener;
    private MovieItem movieItem;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == GAMES_VIEW_HOLDER){
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item, parent, false);
            return  new GamesViewHolder(layout);
        }else if(viewType == MY_VIEW_HOLDER){
            View layout =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent , false);
            return new MyViewHolder(layout);
        }
        return null;

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == MY_VIEW_HOLDER) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;

            movieItem = items.get(position);
            Glide.with(myViewHolder.poster.getContext()).load(items.get(position).getPoster())
                    .into(myViewHolder.poster);
            myViewHolder.titleAndYear.setText(movieItem.getTitle() + " (" + movieItem.getYear() + ")");
            myViewHolder.type.setText("typ: " + movieItem.getType());
            myViewHolder.itemView.setOnClickListener(v -> {
                if (onMovieDetailsClickListener != null) {
                    onMovieDetailsClickListener.onMovieDetailsClick(movieItem.getImdbID());
                }
            });
        }else {
            GamesViewHolder gamesViewHolder = (GamesViewHolder) holder;
            Glide.with(gamesViewHolder.poster.getContext()).load(movieItem.getPoster()).into(gamesViewHolder.poster);
            gamesViewHolder.titile.setText(movieItem.getTitle());
            gamesViewHolder.itemView.setOnClickListener(v -> {
                if (onMovieDetailsClickListener != null) {
                    onMovieDetailsClickListener.onMovieDetailsClick(movieItem.getImdbID());
                }
            });
        }
    }

    @Override

    public int getItemViewType(int position){
        if("Game".equalsIgnoreCase(items.get(position).getType())){
            return GAMES_VIEW_HOLDER;
        }else{
            return MY_VIEW_HOLDER;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<MovieItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }


    public void addItems(List<MovieItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    class GamesViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;
        TextView titile;

        public GamesViewHolder(View itemView) {
            super(itemView);
            poster = ButterKnife.findById(itemView, R.id.game_poster);
            titile =ButterKnife.findById(itemView, R.id.game_title);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView poster;
        TextView titleAndYear;
        TextView type;

        public MyViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.poster);
            titleAndYear = (TextView)itemView.findViewById(R.id.title_and_year);
            type = (TextView)itemView.findViewById(R.id.type);
        }
    }
    public void setOnMovieDetailsClickListener(OnMovieDetailsClickListener onMovieDetailsClickListener) {
        this.onMovieDetailsClickListener = onMovieDetailsClickListener;
    }
}
