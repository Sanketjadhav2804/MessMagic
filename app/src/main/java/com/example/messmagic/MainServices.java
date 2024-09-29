package com.example.messmagic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messmagic.Fragment.GuestServiceFragment;



public class MainServices extends AppCompatActivity {
    TextView serviceName ;

    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_services);

        String tag = getIntent().getStringExtra("tag");
        backArrow=findViewById(R.id.backArrow);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        switch (tag){


            case "guestService":
                transaction.replace(R.id.servicesFragmentContainerView, new GuestServiceFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                break;

//            case "tiffinService":
//                serviceName.setText("Tiffin Service");
//                transaction.replace(R.id.servicesFragmentContainerView, new TiffinServiceFragment());
//                transaction.addToBackStack(null);
//                transaction.commit();
//                break;

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}