package com.example.thegarden.registration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thegarden.R;
import com.example.thegarden.authentication.LoginActivity;
import com.example.thegarden.dto.RegistrationRequestDto;
import com.example.thegarden.network.ApiClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        MaterialButton buttonRegister = findViewById(R.id.registerButton);

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
                    .enqueue(new Callback<RegistrationRequestDto>() {
                        @Override
                        public void onResponse(Call<RegistrationRequestDto> call, Response<RegistrationRequestDto> response) {
                            if (response.isSuccessful()) {
                                // Response is successful (status code 200-299)
                                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            } else {
                                // Response is not successful, get error message from response
                                try {
                                    String errorMessage = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                                    Toast.makeText(RegistrationActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    Toast.makeText(RegistrationActivity.this, "Error reading error message", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }


                        @Override
                        public void onFailure(Call<RegistrationRequestDto> call, Throwable t) {
                            Toast.makeText(RegistrationActivity.this, "Save failed", Toast.LENGTH_SHORT).show();
                            Logger.getLogger(RegistrationActivity.class.getName()).log(Level.SEVERE,"Error occurred",t);

                        }
                    });


        });

    }

}
