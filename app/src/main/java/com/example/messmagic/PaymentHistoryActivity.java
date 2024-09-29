package com.example.messmagic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.messmagic.menu.TiffinServiceModel;
import com.example.messmagic.order.OrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PaymentAdapter mAdapter;

    private ProgressBar mProgressCircle;


    FirebaseAuth mAuth;

    ImageView backArrow;
    private DatabaseReference mDatabaseRef;
    private List<PaymentDataModel> mUploads,orderedUpload;

   Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        mRecyclerView = findViewById(R.id.userListRecyclerView);
        mRecyclerView.setHasFixedSize(true); 
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth=FirebaseAuth.getInstance();
        mProgressCircle = findViewById(R.id.progress_circle);
        context=getApplicationContext();
        backArrow=findViewById(R.id.backArrow);



        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUploads = new ArrayList<>();
        orderedUpload= new ArrayList<>();
        String userId=mAuth.getCurrentUser().getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("payment").child(userId);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();  //clear previous data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PaymentDataModel upload = postSnapshot.getValue(PaymentDataModel.class);
                    mUploads.add(upload);
                }

                //To reverse the list
                orderedUpload.clear(); // Clear previous data
                for (int i = mUploads.size() - 1; i >= 0; i--) {
                    orderedUpload.add(mUploads.get(i));

                } 

                mAdapter = new PaymentAdapter(context, orderedUpload);

                mRecyclerView.setAdapter(mAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PaymentHistoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

}
