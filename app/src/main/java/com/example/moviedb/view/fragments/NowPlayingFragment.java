package com.example.moviedb.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.helper.EndlessRecyclerScrollView;
import com.example.moviedb.helper.InternetConnection;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.view.MainMenuActivity;
import com.example.moviedb.view.SplashScreenActivity;
import com.example.moviedb.view.activities.NowPlayingActivity;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.example.moviedb.helper.ItemClickSupport;

public class NowPlayingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
    }

    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
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

    private RecyclerView rv_now_playing_fragment;
    private ProgressBar progressBar;

    private MovieViewModel view_model;
    InternetConnection connection;
    private boolean loading = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        rv_now_playing_fragment = view.findViewById(R.id.rv_now_playing_fragment);
        progressBar = view.findViewById(R.id.progressBar);

        connection = new InternetConnection(getActivity());
        Handler handler = new Handler();
        if(connection.isConnected()){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                    view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
                    view_model.getNowPlaying();
                    view_model.getResultNowPalying().observe(getActivity(), showNowPlaying);
                }
            }, 800);
        }

//        linearLayoutManager = new LinearLayoutManager(getActivity());
//        rv_now_playing_fragment.setLayoutManager(linearLayoutManager);
//        endlessScroll = new EndlessRecyclerScrollView(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                loadNextDataFromApi(page);
//            }
//        };
//        rv_now_playing_fragment.addOnScrollListener(endlessScroll);

        return view;
    }

//    private void loadNextDataFromApi(int offset) {
//
//    }

    private Observer<NowPlaying> showNowPlaying = new Observer<NowPlaying>() {
        @Override
        public void onChanged(NowPlaying nowPlaying) {
            rv_now_playing_fragment.setLayoutManager(new LinearLayoutManager(getActivity()));
            NowPlayingAdapter adapter = new NowPlayingAdapter(getActivity());
            adapter.setListNowPlaying(nowPlaying.getResults());
            rv_now_playing_fragment.setAdapter(adapter);

            ItemClickSupport.addTo(rv_now_playing_fragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                    return false;
                }
            });

            ItemClickSupport.addTo(rv_now_playing_fragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle(); 
                    bundle.putString("movieId", ""+nowPlaying.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_nowPlayingFragment_to_movieDetailsFragment, bundle);
                }
            });
        }
    };
}