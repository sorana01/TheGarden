package com.example.thegarden.ui.home;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mFirstName = new MutableLiveData<>();

    public HomeViewModel(Application application) {
        super(application);
        // Initialize the first name LiveData
        loadUser();
    }


    private void loadUser(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MySharedPrefs", Application.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("userFirstName", "Guest");

        mFirstName.setValue(firstName);
    }

    // Getter method for the first name LiveData
    public LiveData<String> getFirstName() {
        return mFirstName;
    }

}
