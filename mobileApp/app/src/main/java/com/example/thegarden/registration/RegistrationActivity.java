package com.example.thegarden.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thegarden.R;
import com.example.thegarden.authentication.LoginActivity;
import com.example.thegarden.dto.RegistrationRequestDto;
import com.example.thegarden.network.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);

        initializeComponents();
    }

    private void initializeComponents(){
        TextInputEditText inputEditTextFirstName = findViewById(R.id.firstNameEditText);
        TextInputEditText inputEditTextLastName = findViewById(R.id.lastNameEditText);
        TextInputEditText inputEditTextEmail = findViewById(R.id.emailEditText);
        TextInputEditText inputEditTextPassword = findViewById(R.id.passwordEditText);
        TextInputEditText inputEditTextConfirmPassword = findViewById(R.id.confirmPasswordEditText); // Make sure you have this field in your XML
        TextView textViewPasswordMismatch = findViewById(R.id.textViewPasswordMismatch);
        MaterialButton buttonRegister = findViewById(R.id.registerButton);

        buttonRegister.setEnabled(false); //disable register


        // Create a common TextWatcher for both password fields
        TextWatcher passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Nothing needed here
            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = inputEditTextPassword.getText().toString();
                String confirmPassword = inputEditTextConfirmPassword.getText().toString();

                if (!password.equals(confirmPassword)) {
                    textViewPasswordMismatch.setVisibility(View.VISIBLE);
                    buttonRegister.setEnabled(false);
                } else {
                    textViewPasswordMismatch.setVisibility(View.GONE);
                    buttonRegister.setEnabled(true);
                }
            }
        };

        // Assign the TextWatcher to both EditText fields
        inputEditTextPassword.addTextChangedListener(passwordWatcher);
        inputEditTextConfirmPassword.addTextChangedListener(passwordWatcher);


        TextView buttonLogin = findViewById(R.id.loginButton);

        buttonLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Optional: Call finish() if you want to close the RegistrationActivity
        });

        ApiClient retrofitService = new ApiClient();
        RegistrationApi registrationApi = retrofitService.getRetrofit().create(RegistrationApi.class);

        buttonRegister.setOnClickListener(view -> {
            String firstName = String.valueOf(inputEditTextFirstName.getText());
            String lastName = String.valueOf(inputEditTextLastName.getText());
            String email = String.valueOf(inputEditTextEmail.getText());
            String password = String.valueOf(inputEditTextPassword.getText());

            RegistrationRequestDto register = new RegistrationRequestDto();
            register.setFirstName(firstName);
            register.setLastName(lastName);
            register.setEmail(email);
            register.setPassword(password);

            registrationApi.saveUser(register)
                    .enqueue(new Callback<ResponseBody>() { // Note the use of ResponseBody here instead of RegistrationRequestDto
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                // Registration is successful
                                try {
                                    // Use response.body().string() to get the response text
                                    String successMessage = response.body() != null ? response.body().string() : "Registration Successful";
                                    showSnackbar(successMessage, false);
                                } catch (IOException e) {
                                    Log.e("RegistrationActivity", "Error reading success message", e);
                                    showSnackbar("Registration Successful", false);
                                }
                            } else {
                                // There is an error during registration
                                String errorMessage = "Unknown error";
                                try {
                                    errorMessage = response.errorBody() != null ? response.errorBody().string() : errorMessage;
                                } catch (IOException e) {
                                    Log.e("RegistrationActivity", "Error reading error message", e);
                                }
                                showSnackbar(errorMessage, true);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            showSnackbar("Save failed: " + t.getMessage(), true);
                        }
                    });
        });
    }

    private void showSnackbar(String message, boolean isError) {
        View contextView = findViewById(android.R.id.content); // Find the main content view (you may need to replace this with your root layout id)
        Snackbar snackbar = Snackbar.make(contextView, message, Snackbar.LENGTH_LONG);
        if (isError) {
            snackbar.setBackgroundTint(getResources().getColor(R.color.errorColor)); // Set the background color for error messages
        } else {
            snackbar.setBackgroundTint(getResources().getColor(R.color.successColor)); // Set the background color for success messages
        }
        snackbar.show();
    }

}
