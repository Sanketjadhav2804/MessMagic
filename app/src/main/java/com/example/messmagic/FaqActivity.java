package com.example.messmagic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

// FaqActivity.java
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;




public class FaqActivity extends AppCompatActivity {


    private ProgressBar mProgressCircle;

    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_support);


        mProgressCircle = findViewById(R.id.progress_circle);
        backArrow=findViewById(R.id.backArrow);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Create a list of FAQ items
        List<FaqItem> faqList = new ArrayList<>();
        faqList.add(new FaqItem("How do I sign up for MessMagic?", "To sign up for MessMagic, click on the 'Sign Up' button and follow the prompts."));
        faqList.add(new FaqItem("How do I log in to my MessMagic account?", "You can log in to your MessMagic account by entering your username and password on the login screen."));
        faqList.add(new FaqItem("How can I view the menu schedule?", "You can view the menu schedule by navigating to the Menu section in the app."));
        faqList.add(new FaqItem("How do I place a guest order?", "To place a guest order, go to the Guest Services section and follow the instructions."));
        faqList.add(new FaqItem("How do I subscribe to the Monthly mess service?", "To subscribe to the monthly mess, navigate to the Monthly mess Service section and select your preferred plan."));
        faqList.add(new FaqItem("How can I make an online payment?", "You can make an online payment by selecting the Online Payment option and following the payment process."));

        // Add more FAQ items as needed...

        // Create an adapter to populate the ListView with FAQ items
        FaqAdapter faqAdapter = new FaqAdapter(this, faqList);

        // Set the adapter for the ListView
        ListView faqListView = findViewById(R.id.faqListView);
        faqListView.setAdapter(faqAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);
        // Set click listener for FAQ items to expand/collapse the answer
        faqListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView faqItemTextView = view.findViewById(R.id.faqItemTextView);
                FaqItem faqItem = faqAdapter.getItem(position);
                if (faqItem != null) {
                    String answer = faqItem.getAnswer();
                    if (faqItemTextView.getText().equals(faqItem.getQuestion() + "\n\n" + answer)) {
                        faqItemTextView.setText(faqItem.getQuestion());
                    } else {
                        faqItemTextView.setText(faqItem.getQuestion() + "\n\n" + answer);
                    }
                }
            }
        });
    }
}
