package com.example.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private String fullname, username, email, password, confirmPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullname = binding.signupName.getText().toString();
                username = binding.signupUsername.getText().toString();
                email = binding.signupEmail.getText().toString();
                password = binding.signupPassword.getText().toString();
                confirmPassword = binding.signupConfrmpassword.getText().toString();

                if (formIsEmpty()) {
                    toastMessage(getString(R.string.error_field_required));
                } else if(!isUsernameValid(username)) {
                    toastMessage(getString(R.string.error_valid_username));
                }else if(!isEmailValid(email)) {
                    toastMessage(getString(R.string.error_valid_email));
                }else if(!isMinimalPasswordValid(password) || !isMinimalPasswordValid(confirmPassword)){
                    toastMessage(getString(R.string.error_minimal_password));
                }else if(!isPasswordMatch()){
                    toastMessage(getString(R.string.error_password_dont_match));
                }else{
                    //Login Process
                    goToMainActivity();

                }
            }
        });
    }

    private void toastMessage(String value){
        Toast.makeText(SignUpActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private boolean formIsEmpty() {
        return fullname.isEmpty() ||username.isEmpty() ||email.isEmpty() ||password.isEmpty() || confirmPassword.isEmpty();
    }

    private boolean isUsernameValid(String value){
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return pattern.matcher(value)
                .matches();
    }

    private boolean isEmailValid(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value)
                .matches();
    }

    private boolean isMinimalPasswordValid(String value){
        return value.length() >= 8;
    }

    private boolean isPasswordMatch(){
        return password.equals(confirmPassword);
    }

    private void loginProcess(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        /*Create handle for the RetrofitInstance interface*/
        ApiServiceLogin service = ApiClientLogin.getRetrofitInstance().create(ApiServiceLogin.class);
        Call<ResponseBody> call = service.registerUser(username,fullname,email,password,confirmPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                toastMessage("Something went wrong...Please try later!");
            }
        });
    }

    private void goToLoginActivity(){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}