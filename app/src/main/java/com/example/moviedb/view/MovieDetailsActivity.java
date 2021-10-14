package com.example.moviedb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_id, lbl_synopsis, movie_synopsis_text, tv_rating_movie_detail, tv_releasedate_movie_detail, tv_adult_movie_detail;
    private String movie_id, movie_title, movie_synopsis, movie_rating, movie_releasedate, movie_adult = "";
    private ImageView img_poster, img_rating;
    private Toolbar toolbar;
    private MovieViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        lbl_id = findViewById(R.id.lbl_movie_details);
        img_poster = findViewById(R.id.img_movie_details);
        lbl_synopsis = findViewById(R.id.lbl_synopsis);
        movie_synopsis_text = findViewById(R.id.tv_synopsis_movie_detail);
        img_rating = findViewById(R.id.img_rating);
        tv_rating_movie_detail = findViewById(R.id.tv_rating_movie_detail);
        tv_releasedate_movie_detail = findViewById(R.id.tv_releasedate_movie_detail);
        tv_adult_movie_detail = findViewById(R.id.tv_adult_movie_detail);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        movie_title = intent.getStringExtra("movie_title");
        movie_synopsis = intent.getStringExtra("movie_synopsis");
        movie_rating = intent.getStringExtra("movie_rating");
        movie_releasedate = intent.getStringExtra("movie_releasedate");
        movie_adult = intent.getStringExtra("movie_adult");

        toolbar = findViewById(R.id.tb_movie_details);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movie_title);

        movie_synopsis_text.setText(movie_synopsis);
        lbl_id.setText(movie_id);
        tv_rating_movie_detail.setText(movie_rating);
        tv_releasedate_movie_detail.setText(movie_releasedate);
        if (movie_adult != "false") {
            tv_adult_movie_detail.setText("Only Certain Age Above");
        }else{
            tv_adult_movie_detail.setText("All Audience");
        }

        model = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        model.getMovieDetail();
        //model.getResultMovieDetail().observe(MovieDetailsActivity.this, showMoviePoster);
    }

    private Observer<Movies> showMoviePoster = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String poster_path = movies.getPoster_path().toString();
            if(!(poster_path == null)) {
                String movie_poster = Const.IMG_URL + poster_path;
                Glide.with(MovieDetailsActivity.this).load(movie_poster).into(img_poster);
            }
        }
    };

    //back back button milik app
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //back back button milik hp
    @Override
    public void onBackPressed() {
        finish();
    }
}