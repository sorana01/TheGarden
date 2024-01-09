package com.example.thegarden.ui.plants;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thegarden.authentication.LoginActivity;
import com.example.thegarden.databinding.FragmentHomeBinding;
import com.example.thegarden.databinding.FragmentPlantsBinding;
import com.example.thegarden.network.ApiClient;
import com.example.thegarden.savePlants.PlantApi;
import com.example.thegarden.savePlants.PlantInfo;
import com.example.thegarden.ui.plants.PlantsViewModel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PlantsFragment extends Fragment {

    private FragmentPlantsBinding binding;
    private List<PlantInfo> plantList;
    private PlantAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlantsViewModel plantsViewModel =
                new ViewModelProvider(this).get(PlantsViewModel.class);

        binding = FragmentPlantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        // Make API Call
//        ApiClient retrofit = new ApiClient();
//        PlantApi service = retrofit.getRetrofit().create(PlantApi.class);
//        Call<List<PlantInfo>> call = service.getPlantsForCurrentUser();

        if (getContext() == null)
            Log.d("getContext() in PlantsFragment", "NULL");
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefs", getContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("AuthToken", null);
        String tokenKnown = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzb3JhbmFAeWFob28uY29tIiwibGFzdE5hbWUiOiJYIiwiaWF0IjoxNzA0NDU1MjAyLCJleHAiOjE3MDQ3MTQ0MDJ9.409En_78v1yz5OwE31kV2RQr-u7aD-OHPC-t_JNGmC75mjqCXph_PTpoTouiTMTkjJTHXAnxxjdyd3aiUj8EVg";
        Log.d("SharedPreferences", "JWT Token: " + token);

        if (token != null && !token.isEmpty()) {
            ApiClient retrofit = new ApiClient();
            PlantApi service = retrofit.getRetrofit().create(PlantApi.class);
            Call<List<PlantInfo>> call = service.getPlantsForCurrentUser(token);
            Log.d("API Request", "Authorization header: " + token);

            call.enqueue(new Callback<List<PlantInfo>>() {
                @Override
                public void onResponse(Call<List<PlantInfo>> call, Response<List<PlantInfo>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        plantList = response.body(); // Assign the fetched plants to your list
                        adapter = new PlantAdapter(plantList, new PlantAdapter.OnPlantItemClickListener() {
                            @Override
                            public void onDeleteClick(String plantName, int position) {
                                deletePlantByName(plantName, position);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    } else {
                        String error = parseError(response);
                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<PlantInfo>> call, Throwable t) {
                    // Handle API call failure
                }
            });
        } else {
            // Handle the case where token is null or empty
            Toast.makeText(getContext(), "Authentication token not found. Please login again.", Toast.LENGTH_LONG).show();
            // Optionally, redirect the user to the login screen
        }

        return root;
    }


    private void deletePlantByName(String plantName, final int position) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPrefs", getContext().MODE_PRIVATE);
        String token = sharedPreferences.getString("AuthToken", "");

        ApiClient retrofitService = new ApiClient();
        PlantApi service = retrofitService.getRetrofit().create(PlantApi.class);
        Call<Void> call = service.deletePlantByName(token, plantName); // URL encode the plant name

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    plantList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(getContext(), "Plant deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to delete plant", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String parseError(Response<?> response) {
        try {
            // We're expecting the error body to be a plain string in this case
            if (response.errorBody() != null) {
                return response.errorBody().string();
            } else {
                return "An unknown error occurred";
            }
        } catch (IOException e) {
            return "Error parsing error message";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}