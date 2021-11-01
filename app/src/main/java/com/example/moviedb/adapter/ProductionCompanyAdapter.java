package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.ProductionCompanyViewHolder> {

    private Context context;
    private List<Movies.ProductionCompanies> productionCompaniesList;

    public ProductionCompanyAdapter(Context context) {
        this.context = context;
    }

    public List<Movies.ProductionCompanies> getProductionCompanyList() {
        return productionCompaniesList;
    }

    public void setProductionCompanyList(List<Movies.ProductionCompanies> productionCompaniesList) {
        this.productionCompaniesList = productionCompaniesList;
    }

    @NonNull
    @Override
    public ProductionCompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_production_companies, parent, false);
        return new ProductionCompanyAdapter.ProductionCompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyAdapter.ProductionCompanyViewHolder holder, int position) {
        final Movies.ProductionCompanies productionCompanies = getProductionCompanyList().get(position);

        if(productionCompanies.getLogo_path() == null){
            Glide.with(context).load(R.drawable.ic_launcher_foreground).into(holder.img_production_company);
        }else{
            Glide.with(context).load(Const.IMG_URL + productionCompanies.getLogo_path()).into(holder.img_production_company);
        }
    }

    @Override
    public int getItemCount() {
        return getProductionCompanyList().size();
    }

    public class ProductionCompanyViewHolder extends RecyclerView.ViewHolder {

        CardView cv_production_companies;
        ImageView img_production_company;

        public ProductionCompanyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_production_company = itemView.findViewById(R.id.img_production_company);
        }
    }
}
