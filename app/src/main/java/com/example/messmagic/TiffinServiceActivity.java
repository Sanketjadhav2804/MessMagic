package com.example.messmagic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.messmagic.menu.TiffinServiceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TiffinServiceActivity extends AppCompatActivity implements PaymentResultListener {

    private TextView TimeTextView , dateTextView , menuDescription;

    EditText name ,email,mobile,tiffinNo;

    ImageView backArrow;
    private Button order;

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;
    FirebaseStorage storage;

    private ProgressDialog pd;
    String userID;
    Double finalamount;
    String textName ,textEmail , textMobile , textTiffinNo, textTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiffin_service);


        init();
        chargesMess();
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });






        dateTextView.setText(formattedDate);

        TimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int hours=calendar.get(Calendar.HOUR_OF_DAY);
                int mins=calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(TiffinServiceActivity.this , new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        TimeTextView.setText(time);
                    }
                },hours ,mins,false);
                timePickerDialog.show();
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd.setMessage("Please Wait");
                pd.show();

                textName = name.getText().toString().trim();
                textEmail = email.getText().toString().trim();
                textMobile = mobile.getText().toString().trim();
                textTiffinNo = tiffinNo.getText().toString().trim();
                textTime = TimeTextView.getText().toString().trim();

                if (!validation()) {
                    pd.dismiss();
                  //  Toast.makeText(TiffinServiceActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                } else {

                    makePayment();
                }
            }
        });

    }

    private boolean validation() {
        SignUpFormValidation validator = new SignUpFormValidation();
        if(!validator.isValidName(textName)){
            Toast.makeText(this, "Please fill Name Field contains only letters ", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        }else  if(textEmail.isEmpty() || !(validator.isValidEmail(textEmail))){
            Toast.makeText(this, "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        }else  if(textMobile.isEmpty() || !validator.isValidMobileNumber(textMobile)){
            Toast.makeText(this, "Please enter valid Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();
            return false;
        }else  if(textTiffinNo.isEmpty() ){
            Toast.makeText(this, "Please enter tiffin number", Toast.LENGTH_SHORT).show();
            tiffinNo.requestFocus();
            return false;
        } else  if(!textTiffinNo.isEmpty()){
            int tiffin = Integer.parseInt(textTiffinNo);
            if(tiffin >= 11) {
                Toast.makeText(this, "Maximum 10 tiffin limit", Toast.LENGTH_SHORT).show();
                tiffinNo.requestFocus();
                return false;
            } else if (tiffin == 0) {
                Toast.makeText(this, "Atleast 1 tiffin required", Toast.LENGTH_SHORT).show();
                tiffinNo.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void makePayment() {



        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_rg0SY0oban0u6n");

        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        String numericPart = textTiffinNo.replaceAll("[^0-9]", ""); // Remove non-numeric characters
        int amount = Integer.parseInt(numericPart);
        double allamount =  amount *80 ;
        finalamount =allamount*100;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "MessMagic");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", ""+finalamount);//300 X 100
            options.put("prefill.email",email);
            options.put("prefill.contact",mobile);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    public void onPaymentSuccess(String s)
    {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();

        savetoDatabase(s);
        saveToPaymentData(s);
    }



    @Override
    public void onPaymentError(int i, String s) {

        pd.dismiss();
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }

    private void init() {
        name=findViewById(R.id.name);
        email=findViewById(R.id.emailAddress);
        mobile=findViewById(R.id.mobile);
        tiffinNo=findViewById(R.id.people);
        TimeTextView=findViewById(R.id.time);
        dateTextView=findViewById(R.id.textView8);
        backArrow=findViewById(R.id.backArrow);
        order=findViewById(R.id.btn_reg);
        menuDescription=findViewById(R.id.menuDesciption);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        pd = new ProgressDialog(this);
    }


    private void saveToPaymentData(String s) {

        String userId=mAuth.getCurrentUser().getUid();
        String uploadId = firebaseDatabase.getReference().push().getKey();
        String status ="Pending";

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        PaymentDataModel order = new PaymentDataModel( userId ,textName, textEmail, textMobile, s , (finalamount)/100,formattedDate);



        firebaseDatabase.getReference().child("payment").child(userId).child(uploadId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){


                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(TiffinServiceActivity.this, "Some Error to add payment Data..Try Again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void savetoDatabase(String s) {
        String userId=mAuth.getCurrentUser().getUid();
        String uploadId = firebaseDatabase.getReference().push().getKey();
        String status ="Pending";

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        TiffinServiceModel order;
        order = new TiffinServiceModel( userId ,textName, textEmail, textMobile, textTiffinNo, textTime,uploadId,status ,formattedDate,(finalamount)/100 ,ServerValue.TIMESTAMP);



        firebaseDatabase.getReference().child("tiffinorder").child(userId).child(uploadId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    // Dismiss the dialog when your task is complete

                    pd.dismiss();
                    Toast.makeText(TiffinServiceActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();

                    name.setText(null);
                    email.setText(null);
                    mobile.setText(null);
                    tiffinNo.setText(null);
                    TimeTextView.setText(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(TiffinServiceActivity.this, "Order couldn't Placed..Try Again", Toast.LENGTH_SHORT).show();
            }
        });




    }


    private void chargesMess() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("messcharges");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tiffinAmount = snapshot.child("tiffinAmount").getValue(String.class);


                menuDescription.setText("chapati, bhaji,\ndaal, rice,\n\nTifiin Charge: Rs."+tiffinAmount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TiffinServiceActivity.this, "Error in Database..", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
