package com.example.thegarden.ui.scan;

import android.widget.Button;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.widget.ViewPager2;

import com.example.thegarden.savePlants.PlantInfo;

import java.util.List;

public class ScanViewModel extends ViewModel {

    private MutableLiveData<List<PlantInfo>> plantInfoList = new MutableLiveData<>();

    public LiveData<List<PlantInfo>> getPlantInfoList() {
        return plantInfoList;
    }

    public void setPlantInfoList(List<PlantInfo> list) {
        plantInfoList.setValue(list);
    }
}