package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.ProductionCompanyAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.helper.InternetConnection;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.Movies;
import com.example.moviedb.view.activities.MovieDetailsActivity;
import com.example.moviedb.viewmodel.MovieViewModel;

import java.util.List;

public class MovieDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView lbl_id, lbl_synopsis_title, lbl_production_title, lbl_title, lbl_synopsis, lbl_genre_movie_details, lbl_tagline_movie_details, lbl_vote_movie_detail, lbl_releasedate_movie_detail, lbl_popularity_movie_detail;
    private String movieId, movieTitle, movieVoteCount, movieOverview, moviePopularity, movieTagline, movieBackdrop, moviePoster, movieReleaseDate, movieVoteAverage = "";
    private ImageView img_poster, img_backdrop;
    private MovieViewModel model;
    InternetConnection connection;
    private ProgressBar progressBar3;
    private ScrollView scrollView;
    private RecyclerView rv_production_company;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        lbl_synopsis_title = view.findViewById(R.id.lbl_synopsis_details_fragment); //for showing the "synopsis" text
        lbl_production_title = view.findViewById(R.id.lbl_production_companies_details_fragment); //for showing the "production companies" text
        lbl_id = view.findViewById(R.id.lbl_movie_id_movie_details_fragment);
        lbl_title = view.findViewById(R.id.lbl_title_details_fragment);
        img_poster = view.findViewById(R.id.img_poster_details_fragment);
        img_backdrop = view.findViewById(R.id.img_backdrop_details_fragment);
        lbl_synopsis = view.findViewById(R.id.lbl_overview_details_fragment);
        lbl_vote_movie_detail = view.findViewById(R.id.lbl_vote_details_fragment);
        lbl_releasedate_movie_detail = view.findViewById(R.id.lbl_release_date_details_fragment);
        lbl_popularity_movie_detail = view.findViewById(R.id.lbl_popularity_details_fragment);
        lbl_tagline_movie_details = view.findViewById(R.id.lbl_tagline_details_fragment);
        lbl_genre_movie_details = view.findViewById(R.id.lbl_genre_details_fragment);
        progressBar3 = view.findViewById(R.id.progressBar3);
        scrollView = view.findViewById(R.id.scrollView);
        rv_production_company = view.findViewById(R.id.frame_production_companies_details_fragment);

        connection = new InternetConnection(getActivity());
        Handler handler = new Handler();
        if(connection.isConnected()){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar3.setVisibility(View.GONE);
                    movieId = getArguments().getString("movieId");

                    model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
                    model.getMovieById(movieId);
                    model.getResultGetMovieById().observe(getActivity(), showDetails);

                    lbl_id.setText(movieId);
                }
            }, 800);
        }

        return view;
    }

    private Observer<Movies> showDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String movie_poster_path = movies.getPoster_path().toString().trim();
            String movie_backdrop_path = movies.getBackdrop_path().toString().trim();
            String movie_title = movies.getTitle().toString();
            String movie_tagline = movies.getTagline().toString();
            String movie_avg_vote = String.valueOf(movies.getVote_average());
            String movie_vote = String.valueOf(movies.getVote_count());
            String movie_popularity = String.valueOf(movies.getPopularity());
            String movie_release_date = movies.getRelease_date().toString();
            String movie_overview = movies.getOverview().toString();
            List<Movies.Genres> movie_genre = movies.getGenres();
            List<Movies.ProductionCompanies> movie_production_companies = movies.getProduction_companies();

            if(!(movie_poster_path == null)) {
                String movie_poster = Const.IMG_URL + movies.getPoster_path();
                Glide.with(getActivity()).load(movie_poster).into(img_poster);
            }if(!(movie_backdrop_path == null)) {
                String movie_backdrop = Const.IMG_URL + movies.getBackdrop_path();
                Glide.with(getActivity()).load(movie_backdrop).into(img_backdrop);
            }

            lbl_synopsis_title.setText("Synopsis");
            lbl_production_title.setText("Production Companies");

            lbl_title.setText(movie_title);
            lbl_tagline_movie_details.setText(movie_tagline);
            lbl_releasedate_movie_detail.setText("Release Date: " + movie_release_date);
            lbl_popularity_movie_detail.setText("Popularity: " + movie_popularity);
            lbl_vote_movie_detail.setText("Avg. Vote: " + movie_avg_vote + "(" + movie_vote + ")");
            lbl_synopsis.setText(movie_overview);

            for(int a = 0; a < movie_genre.size(); a++){
                Movies.Genres get = movie_genre.get(a);
                if(a < movie_genre.size() - 1){
                    lbl_genre_movie_details.append(get.getName() + ", ");
                }else{
                    lbl_genre_movie_details.append(get.getName());
                }
            }

            LinearLayoutManager production_company_layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
            ProductionCompanyAdapter productionCompanyAdapter = new ProductionCompanyAdapter(getActivity());
            productionCompanyAdapter.setProductionCompanyList(movies.getProduction_companies());
            rv_production_company.setAdapter(productionCompanyAdapter);
            rv_production_company.setLayoutManager(production_company_layoutManager);

            ItemClickSupport.addTo(rv_production_company).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    String pCompanyName = movie_production_companies.get(position).getName();
                    Toast.makeText(getActivity(), pCompanyName, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}