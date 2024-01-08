package com.example.thegarden.ui.plants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thegarden.R;
import com.example.thegarden.savePlants.PlantInfo;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<PlantInfo> plants;
    private OnPlantItemClickListener listener;

    public interface OnPlantItemClickListener {
        void onDeleteClick(String plantName, int position);
    }


    public PlantAdapter(List<PlantInfo> plants, OnPlantItemClickListener listener) {
        this.plants = plants;
        this.listener = listener;
    }

    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlantViewHolder holder, int position) {
        final PlantInfo plant = plants.get(holder.getAdapterPosition());
        holder.textViewPlantName.setText(plant.getName());
        Glide.with(holder.imageViewPlant.getContext())
                .load(plant.getImageUrl())
                .into(holder.imageViewPlant);

        holder.buttonDeletePlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    String plantName = plants.get(adapterPosition).getName();
                    listener.onDeleteClick(plantName, adapterPosition);
                }
            }
        });

    }




    public void updateData(List<PlantInfo> newData) {
        this.plants.clear();
        this.plants.addAll(newData);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return plants.size();
    }

    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewPlant;
        public TextView textViewPlantName;
        public Button buttonDeletePlant;

        public PlantViewHolder(View itemView) {
            super(itemView);
            imageViewPlant = itemView.findViewById(R.id.imageViewPlant);
            textViewPlantName = itemView.findViewById(R.id.textViewPlantName);
            buttonDeletePlant = itemView.findViewById(R.id.buttonDeletePlant);
        }
    }
}

