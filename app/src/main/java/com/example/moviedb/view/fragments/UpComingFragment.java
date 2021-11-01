package com.example.moviedb.view.fragments;

import android.os.Bundle;

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

import com.example.moviedb.R;
import com.example.moviedb.adapter.NowPlayingAdapter;
import com.example.moviedb.adapter.UpComingAdapter;
import com.example.moviedb.helper.EndlessRecyclerScrollView;
import com.example.moviedb.helper.InternetConnection;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.UpComing;
import com.example.moviedb.viewmodel.MovieViewModel;

public class UpComingFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UpComingFragment() {
        // Required empty public constructor
    }

    public static UpComingFragment newInstance(String param1, String param2) {
        UpComingFragment fragment = new UpComingFragment();
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

    private RecyclerView rv_up_coming_fragment;
    private ProgressBar progressBar2;

    private MovieViewModel view_model;
    InternetConnection connection;
    private boolean loading = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);

        rv_up_coming_fragment = view.findViewById(R.id.rv_up_coming_fragment);
        progressBar2 = view.findViewById(R.id.progressBar2);

        connection = new InternetConnection(getActivity());
        Handler handler = new Handler();
        if(connection.isConnected()){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar2.setVisibility(View.GONE);
                    view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
                    view_model.getUpComing();
                    view_model.getResultUpComing().observe(getActivity(), showUpComing);
                }
            }, 800);
        }
        return view;
    }

    private Observer<UpComing> showUpComing = new Observer<UpComing>() {
        @Override
        public void onChanged(UpComing upComing) {
            rv_up_coming_fragment.setLayoutManager(new LinearLayoutManager(getActivity()));
            UpComingAdapter adapter = new UpComingAdapter(getActivity());
            adapter.setListUpComing(upComing.getResults());
            rv_up_coming_fragment.setAdapter(adapter);

            ItemClickSupport.addTo(rv_up_coming_fragment).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                    return false;
                }
            });

            ItemClickSupport.addTo(rv_up_coming_fragment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+upComing.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_upComingFragment_to_movieDetailsFragment, bundle);
                }
            });
        }
    };
}