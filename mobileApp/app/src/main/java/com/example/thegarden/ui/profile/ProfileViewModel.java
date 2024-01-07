package com.example.thegarden.ui.profile;

import android.app.Application;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ProfileViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mLastName = new MutableLiveData<>();
    private final MutableLiveData<String> mEmail = new MutableLiveData<>();

    public ProfileViewModel(Application application) {
        super(application);
        loadUserData();
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("MySharedPrefs", Application.MODE_PRIVATE);
        String email = sharedPreferences.getString("userEmail", "Not logged in");
        // Assuming you also store the user's name in SharedPreferences
        String lastName = sharedPreferences.getString("userLastName", "Guest");

        mLastName.setValue(lastName);
        mEmail.setValue(email);
    }

    public LiveData<String> getLastName() {
        return mLastName;
    }

    public LiveData<String> getEmail() {
        return mEmail;
    }
}
