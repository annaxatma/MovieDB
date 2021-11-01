package com.example.moviedb.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.NowPlaying;

import java.util.List;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.CardViewViewHolder> {

    private Context context;
    private List<NowPlaying.Results> listNowPlaying;
    public List<NowPlaying.Results> getListNowPlaying(){
        return listNowPlaying;
    }

    public void setListNowPlaying(List<NowPlaying.Results> listNowPlaying){
        this.listNowPlaying = listNowPlaying;
    }

    public NowPlayingAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public NowPlayingAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie_list, parent, false);
        return new NowPlayingAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.CardViewViewHolder holder, int position) {
        final NowPlaying.Results results = getListNowPlaying().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_data.setText(results.getRelease_date());
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.img_poster);

        //BUNDLENYA PINDAH KE NOW PLAYING FRAGMENT
//        holder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(context, MovieDetailsActivity.class);
////                intent.putExtra("movie_id", ""+results.getId());
////                intent.putExtra("movie_title", ""+results.getTitle());
////                intent.putExtra("movie_synopsis", ""+results.getOverview());
////                intent.putExtra("movie_rating", ""+results.getVote_average());
////                intent.putExtra("movie_poster", ""+results.getPoster_path());
////                intent.putExtra("movie_adult", ""+results.isAdult());
////                intent.putExtra("movie_releasedate", ""+results.getRelease_date());
////                context.startActivity(intent);
//                Bundle bundle = new Bundle();
//                bundle.putString("movieId", ""+results.getId());
//                Navigation.findNavController(view).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return getListNowPlaying().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {

        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_data;
        CardView cv;

        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card);
            lbl_title = itemView.findViewById(R.id.lbl_title_card);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card);
            lbl_release_data = itemView.findViewById(R.id.lbl_releasedate_card);
            cv = itemView.findViewById(R.id.cv_card);
        }
    }
}
