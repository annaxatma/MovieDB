package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.UpComing;

import java.util.List;

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.UpComingHolder> {

    private Context context;
    private List<UpComing.Results> listUpComing;
    private List<UpComing.Results> getListUpComing(){
        return listUpComing;
    }

    public void setListUpComing(List<UpComing.Results> listUpComing){
        this.listUpComing = listUpComing;
    }

    public UpComingAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public UpComingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_movie_list, parent, false);
        return new UpComingAdapter.UpComingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingAdapter.UpComingHolder holder, int position) {
        final UpComing.Results results = getListUpComing().get(position);
        holder.lbl_title2.setText(results.getTitle());
        holder.lbl_overview2.setText(results.getOverview());
        holder.lbl_release_date2.setText(results.getRelease_date());
        Glide.with(context).load(Const.IMG_URL + results.getPoster_path()).into(holder.img_poster2);
    }

    @Override
    public int getItemCount() {
        return getListUpComing().size();
    }

    public class UpComingHolder extends RecyclerView.ViewHolder {

        ImageView img_poster2;
        TextView lbl_title2, lbl_overview2, lbl_release_date2;
        CardView cv2;

        public UpComingHolder(@NonNull View itemView) {
            super(itemView);
            img_poster2 = itemView.findViewById(R.id.img_poster_card);
            lbl_title2 = itemView.findViewById(R.id.lbl_title_card);
            lbl_overview2 = itemView.findViewById(R.id.lbl_overview_card);
            lbl_release_date2 = itemView.findViewById(R.id.lbl_releasedate_card);
            cv2 = itemView.findViewById(R.id.cv_card);
        }
    }
}
