package com.example.thegarden.savePlants;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thegarden.R;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<PlantInfo> plantInfoList;

    public ViewPagerAdapter(List<PlantInfo> plantInfoList) {
        this.plantInfoList = plantInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlantInfo plantInfo = plantInfoList.get(position);
        holder.slideTitle.setText(plantInfo.getName());

        // Load image using Glide or similar library
        Glide.with(holder.itemView.getContext()).load(plantInfo.getImageUrl()).into(holder.slideImage);
        Log.d("PlantID", "Name plant: " + plantInfo.getName());
        Log.d("PlantID", "Image url plant: " + plantInfo.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return Math.min(plantInfoList.size(), 3);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView slideImage;
        TextView slideTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slideImage = itemView.findViewById(R.id.slideImage);
            slideTitle = itemView.findViewById(R.id.slideTitle);
        }
    }
}

