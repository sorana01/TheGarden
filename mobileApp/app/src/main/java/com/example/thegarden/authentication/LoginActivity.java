package com.example.thegarden.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thegarden.MainActivity;
import com.example.thegarden.R;
import com.example.thegarden.dto.AuthRequestDto;
import com.example.thegarden.dto.AuthResponseDto;
import com.example.thegarden.network.ApiClient;
import com.example.thegarden.registration.RegistrationActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2); // Set the content view to your login layout

        initializeComponents();
    }

    private void initializeComponents(){

        TextInputEditText inputEditTextEmail = findViewById(R.id.emailEditText);
        TextInputEditText inputEditTextPassword = findViewById(R.id.passwordEditText);
        MaterialButton buttonLogin = findViewById(R.id.loginButton);
        TextView buttonRegister = findViewById(R.id.registerButton);

        buttonRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish(); // Optional: Remove if you want to be able to navigate back to LoginActivity
        });


        ApiClient retrofitService = new ApiClient();
        LoginApi loginApi = retrofitService.getRetrofit().create(LoginApi.class);

        buttonLogin.setOnClickListener(view -> {
            String email = inputEditTextEmail.getText().toString().trim();
            String password = inputEditTextPassword.getText().toString().trim();

            // Input Validation
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            AuthRequestDto loginRequest = new AuthRequestDto();
            loginRequest.setEmail(email);
            loginRequest.setPassword(password);

            // Show Loading Indicator (pseudo-code, replace with your implementation)
            //showLoadingIndicator();

            loginApi.loginUser(loginRequest)
                    .enqueue(new Callback<AuthResponseDto>() {
                        @Override
                        public void onResponse(Call<AuthResponseDto> call, Response<AuthResponseDto> response) {
                            //hideLoadingIndicator(); // Assuming you have a method to hide the loading indicator

                            if (response.isSuccessful() && response.body() != null) {
                                AuthResponseDto authResponse = response.body();
                                String email = authResponse.getEmail();
                                String firstName = authResponse.getFirstName();

                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userEmail", email);
                                editor.putString("userFirstName", firstName);
                                editor.apply();


                                String token = response.headers().get("Authorization"); // Get the token from the response header
                                if (token != null) {
                                    // Store the token securely
                                    storeTokenSecurely(token);
                                    // Use the email and lastName for any other operations you need
                                    navigateToMainScreen(); // Assuming you have a method to navigate to the main screen
                                } else {
                                    Toast.makeText(LoginActivity.this, "Token was not provided by the server.", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                // Handle different types of error responses (e.g., 401 Unauthorized, 404 Not Found, etc.)
                                String error = parseError(response);
                                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthResponseDto> call, Throwable t) {
                            //hideLoadingIndicator(); // Hide loading indicator

                            // This is usually a network error, such as no internet connection, or a serialization issue
                            Log.e("LoginActivity", "Network error or call failure", t);

                            Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }


    private void storeTokenSecurely(String token) {
        SharedPreferences sharedPreferences = getSharedPreferences("YourAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("AuthToken", token);
        editor.apply();
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

    private void navigateToMainScreen() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Call this if you don't want users to return to the login screen when they press the back button.
    }

}
