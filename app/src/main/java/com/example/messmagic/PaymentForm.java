package com.example.messmagic;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PaymentForm extends AppCompatActivity implements PaymentResultListener {
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
   String amount,userID , userName , mobile,email, formattedDate , endDate;

    Double finalamount;
    Button messButton;
    TextView tv_oneTime , tv_fullTime;

    ImageView backArrow;

    LocalDate expiryDate;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_form);


        messButton=(Button)findViewById(R.id.messButton);
        tv_oneTime=(TextView)findViewById(R.id.oneTime);
        tv_fullTime=(TextView)findViewById(R.id.fullTime);
        backArrow=findViewById(R.id.backArrow);
        pd = new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        setCharges();


        messButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Please Wait");
                pd.show();
                makepayment();
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCharges() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userID=mAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                amount = snapshot.child("messcharges").child("messAmount").getValue(String.class);

                tv_fullTime.setText("Full Time Mess: INR Rs."+amount);

                double halfAmount= Double.parseDouble(amount);
                tv_oneTime.setText("One Time Mess: INR Rs."+(int)(halfAmount/2));


                // tv.setText(""+userName+mobile+email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentForm.this, "Error in Database..", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void makepayment()
    {


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        userID=mAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child(userID).child("name").getValue(String.class);
                mobile = snapshot.child(userID).child("mobile").getValue(String.class);
                email=snapshot.child(userID).child("email").getValue(String.class);

               // tv.setText(""+userName+mobile+email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentForm.this, "Error in Database..", Toast.LENGTH_SHORT).show();

            }
        });


        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_rg0SY0oban0u6n");

        checkout.setImage(R.drawable.logo);
        final Activity activity = this;
        finalamount = Double.parseDouble(amount)*100;
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
            options.put("contact",""+mobile);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s)
    {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show();
        try {
            savetoDatabase(s);
            saveToPaymentData(s);
        }
        catch (Exception e){
            Toast.makeText(this, "erro : "+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        pd.dismiss();
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }



    private void saveToPaymentData(String s) {

        String userId=mAuth.getCurrentUser().getUid();
        String uploadId = firebaseDatabase.getReference().push().getKey();
        //String status ="Pending";

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        PaymentDataModel order = new PaymentDataModel( userId ,userName, email, mobile, s , (finalamount)/100, formattedDate);



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
                Toast.makeText(PaymentForm.this, "Some Error to add payment Data..Try Again", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void savetoDatabase(String s) {
        String userId=mAuth.getCurrentUser().getUid();
        String uploadId = firebaseDatabase.getReference().push().getKey();



        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        formattedDate = df.format(c);

        calculateExpiryDate();

        MonthlyMemberModel member = new MonthlyMemberModel( userId ,userName, email, mobile, formattedDate , endDate);



        firebaseDatabase.getReference().child("monthlyMember").child(userId).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    saveToMembershipData();
                    // Dismiss the dialog when your task is complete
                    pd.dismiss();
                    Toast.makeText(PaymentForm.this, "Registered to Mess", Toast.LENGTH_SHORT).show();


                                   }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(PaymentForm.this, "Registration couldn't completed..Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveToMembershipData() {



        String userId = mAuth.getCurrentUser().getUid();
        String uploadId = firebaseDatabase.getReference().push().getKey();

        MembershipModel membership = new MembershipModel(formattedDate, endDate);

        firebaseDatabase.getReference().child("membershipData").child(userId).child(uploadId).setValue(membership)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Handle successful completion
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(PaymentForm.this, "Registration could not be completed. Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void calculateExpiryDate() {

        DateTimeFormatter outputFormatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault());
            outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Parsing the input string to obtain a LocalDate object
            LocalDate parsedDate = LocalDate.parse(formattedDate, inputFormatter);
           // Toast.makeText(this, "" + parsedDate, Toast.LENGTH_SHORT).show();

            // Formatting the LocalDate object to the desired output format
            formattedDate = parsedDate.format(outputFormatter);
        }







        LocalDate startDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE);
        }

        // Example: Duration of the subscription in months
        int subscriptionMonths = 1;

        // Calculate the end date
      expiryDate = calculateEndDate(startDate, subscriptionMonths);


        // Convert expiryDate to String using the outputFormatter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endDate = outputFormatter.format(expiryDate);
        }

    }


    private void calculateExpiryDateFormembershipData() {

        LocalDate startDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = LocalDate.parse(formattedDate, DateTimeFormatter.ISO_DATE);
        }

        // Example: Duration of the subscription in months
        int subscriptionMonths = 1;

        // Calculate the end date
        expiryDate = calculateEndDate(startDate, subscriptionMonths);

            }


    private static LocalDate calculateEndDate(LocalDate startDate, int subscriptionMonths) {
        // Add the specified number of months to the start date

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return startDate.plusMonths(subscriptionMonths);
        }
        return calculateEndDate(startDate, subscriptionMonths);

    }


}