package com.example.messmagic;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    EditText et1_email , et2_password ;
    Button login;
    FirebaseAuth mAuth;
    private ProgressDialog pd;
    String userEmail ,userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {// Check for internet connectivity
                    if(inintUser()){
                if(validation()) {
                    loginUser(userEmail, userPassword);
                }

                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                    et1_email.setText(null);
                    et2_password.setText(null);
                } } else {
                Toast.makeText(LoginActivity.this, "Internet connection is not available", Toast.LENGTH_SHORT).show();
            }
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private boolean inintUser() {
        userEmail=et1_email.getText().toString().trim();
        userPassword=et2_password.getText().toString().trim();
        return true;
    }

    private void loginUser(String userEmail, String userPassword) {

        pd.setMessage("Please Wait");
        pd.show();

        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    // Dismiss the dialog when your task is complete
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish(); // Close the start screen activity
                }//else {
                 //   pd.dismiss();
                   // Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
               // }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               // Dismiss the dialog when your task is complete

                pd.dismiss();
                Toast.makeText(LoginActivity.this, "Wrong credentials or may be User does not exist", Toast.LENGTH_SHORT).show();
            }
        });
    }


        private boolean validation() {

            SignUpFormValidation validator = new SignUpFormValidation();
            if(userEmail.isEmpty() || !(validator.isValidEmail(userEmail))){
                Toast.makeText(this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();
                et1_email.requestFocus();
                return false;
            }
                String passwordErrorMessage = validator.isValidPassword(userPassword);
                if (passwordErrorMessage != null) {
                    Toast.makeText(this, passwordErrorMessage, Toast.LENGTH_SHORT).show();
                    et2_password.requestFocus();
                    return false;
                }

            return true;
        }


    private void initViews() {

        et1_email = findViewById(R.id.et_email);
        et2_password =findViewById(R.id.et_pass);
        login =findViewById(R.id.btn_login);
        mAuth=FirebaseAuth.getInstance();
        pd = new ProgressDialog(this);
    }

    public void signupclick(View view) {

        Intent mainIntent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(mainIntent);
        finish(); // Close the start screen activity
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid="";
        if ( currentUser!= null) {
            uid = currentUser.getUid();
            // User is already logged in, redirect to the main activity
            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish(); // Close the login activity
        }else
        {
            Toast.makeText(this, "Please Wait", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}