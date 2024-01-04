package com.example.thegarden.ui.plants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thegarden.R;
import com.example.thegarden.savePlants.PlantInfo;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<PlantInfo> plants;

    public PlantAdapter(List<PlantInfo> plants) {
        this.plants = plants;
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int position) {
        PlantInfo plant = plants.get(position);
        holder.textViewPlantName.setText(plant.getName());
        // Load image using a library like Glide or Picasso
        Glide.with(holder.imageViewPlant.getContext())
                .load(plant.getImageUrl())
                .into(holder.imageViewPlant);
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewPlant;
        public TextView textViewPlantName;

        public PlantViewHolder(View itemView) {
            super(itemView);
            imageViewPlant = itemView.findViewById(R.id.imageViewPlant);
            textViewPlantName = itemView.findViewById(R.id.textViewPlantName);
        }
    }
}

