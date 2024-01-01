package com.example.thegarden.ui.scan;

import android.widget.Button;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.widget.ViewPager2;

public class ScanViewModel extends ViewModel {

    private final MutableLiveData<ImageView> mImage;
    private final MutableLiveData<ViewPager2> mViewPager;
    private final MutableLiveData<Button> mButton;

    public ScanViewModel() {
        mImage = new MutableLiveData<>();
        mViewPager = new MutableLiveData<>();
        mButton = new MutableLiveData<>();
    }

    public LiveData<ImageView> getImage() {
        return mImage;
    }

    public LiveData<ViewPager2> getViewPager() {
        return mViewPager;
    }
}