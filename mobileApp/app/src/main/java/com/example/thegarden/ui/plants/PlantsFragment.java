package com.example.thegarden.ui.plants;

import android.os.Bundle;
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
        ApiClient retrofit = new ApiClient();
        PlantApi service = retrofit.getRetrofit().create(PlantApi.class);
        Call<List<PlantInfo>> call = service.getPlantsForCurrentUser();

        call.enqueue(new Callback<List<PlantInfo>>() {
            @Override
            public void onResponse(Call<List<PlantInfo>> call, Response<List<PlantInfo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PlantInfo> plantList = response.body();
                    adapter = new PlantAdapter(plantList);
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
        return root;
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