package com.example.messmagic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PollActivity extends AppCompatActivity {

    TextView textQuestion ,result;

    Button option1 , option2, option3 , option4;

    private DatabaseReference mDatabaseRef;
    private List<PollDataModel> mUploads , orderedUpload;

    private ProgressBar mProgressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        textQuestion=findViewById(R.id.tv_question);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        result=findViewById(R.id.tv_result);
        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();
        orderedUpload=new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("polls");


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear(); // Clear the previous data to avoid duplicates
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    PollDataModel upload = postSnapshot.getValue(PollDataModel.class);
                    mUploads.add(upload);

                }


                if (!mUploads.isEmpty()) {
                    PollDataModel pollData = mUploads.get(0); // Assuming you have only one poll data, change as needed

                    // Set the question and options to TextViews and Buttons
                    textQuestion.setText(pollData.getQuestion());
                    option1.setText(pollData.getOption1());
                    option2.setText(pollData.getOption2());
                    option3.setText(pollData.getOption3());
                    option4.setText(pollData.getOption4());
                }


//                // Don't reverse the list here, instead, reverse it separately
//                // Reversing outside the onDataChange method
//                orderedUpload.clear(); // Clear previous data
//                for (int i = mUploads.size() - 1; i >= 0; i--) {
//                    orderedUpload.add(mUploads.get(i));
//
//                }

                mProgressCircle.setVisibility(View.INVISIBLE);

                allClickListener();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PollActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });





    }

    private void allClickListener() {

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}