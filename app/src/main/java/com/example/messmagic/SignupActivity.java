package com.example.messmagic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

   private Button btn_signup;
   private TextView btn_login;
    private  EditText et_name , et_email , et_mobile , et_pass , et_compass;
       FirebaseAuth mAuth;
    private  String name,email , password, mobile , confirmPassword ,userId;

   private FirebaseDatabase database;
     private  DatabaseReference dataBaseReference;

     private ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        inintViews();
        someClickListener();

        //firebase authentication
        mAuth =FirebaseAuth.getInstance();
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        pd = new ProgressDialog(this);

    }

    private void inintViews() {
        btn_signup = findViewById(R.id.btn_reg);
        btn_login = findViewById(R.id.tv_alreadyAccount);
        et_name=findViewById(R.id.name);
        et_email=findViewById(R.id.emailAddress);
        et_mobile=findViewById(R.id.mobile);
        et_pass=findViewById(R.id.pass);
        et_compass =findViewById(R.id.confirmpass);

            }


    private void someClickListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if( initUser()){
                   if(validation()){
                       registerUser(name , email , mobile , password);
                   }

               }else {
                   Toast.makeText(SignupActivity.this, "Problem Occured! Fill Details Again.", Toast.LENGTH_SHORT).show();
                   et_name.setText(null);
                   et_email.setText(null);
                   et_mobile.setText(null);
                   et_pass.setText(null);
                   et_compass.setText(null);
               }


            }
        });
    }

    private void registerUser(String name, String email, String mobile, String password) {

        pd.setMessage("Please Wait");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {


                   initUser();
                   userId=mAuth.getCurrentUser().getUid();
                   Helper helper = new Helper(name , email , mobile ,password ,userId);
                   dataBaseReference.child("Users").child(userId).setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){

                               // Dismiss the dialog when your task is complete

                               pd.dismiss();
                               Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                               Intent mainIntent = new Intent(SignupActivity.this, LoginActivity.class);

                               startActivity(mainIntent);
                               finish(); // Close the start screen activity
                           }
                       }
                   });
               }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(SignupActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }
//                HashMap<String , Object>map=new HashMap<>();
//                map.put("name",name);
//                map.put("email", email);
//                map.put("mobile", mobile);
//                map.put("password",password);
//                map.put("id",mAuth.getCurrentUser().getUid());

    private boolean validation() {

        SignUpFormValidation validator = new SignUpFormValidation();
        if(!validator.isValidName(name)){
            Toast.makeText(this, "Please fill Name Field contains only letters ", Toast.LENGTH_SHORT).show();
            et_name.requestFocus();
            return false;
        }else  if(email.isEmpty() || !(validator.isValidEmail(email))){
            Toast.makeText(this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();
            et_email.requestFocus();
            return false;
        }else  if(mobile.isEmpty() || !validator.isValidMobileNumber(mobile)){
            Toast.makeText(this, "Please enter valid Mobile Number", Toast.LENGTH_SHORT).show();
            et_mobile.requestFocus();
            return false;
        }
        String passwordErrorMessage = validator.isValidPassword(password);
        if (passwordErrorMessage != null) {
            Toast.makeText(this, passwordErrorMessage, Toast.LENGTH_SHORT).show();
            et_pass.requestFocus();
            return false;
        }

        else  if(!validator.isValidConfirmPassword(password, confirmPassword)){
            Toast.makeText(this, "Password should not match with Confirm Password", Toast.LENGTH_SHORT).show();
            et_compass.requestFocus();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password too short ", Toast.LENGTH_SHORT).show();
            et_pass.requestFocus();

        }
        return true;
    }


    private boolean initUser() {
        name = et_name.getText().toString().trim();
        email =et_email.getText().toString().trim();
        mobile=et_mobile.getText().toString().trim();
        password=et_pass.getText().toString().trim();
        confirmPassword= et_compass.getText().toString().trim();
        return true;
    }



}