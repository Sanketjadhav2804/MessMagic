package com.example.messmagic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.example.messmagic.Fragment.AboutUsFragment;
import com.example.messmagic.Fragment.HomeFragment;
import com.example.messmagic.Fragment.SupportFragment;
import com.example.messmagic.Fragment.UserInfoFragment;

public class SettingsActivity extends AppCompatActivity {

    public NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String tag = getIntent().getStringExtra("tag");

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (tag){

                case "primary":

            transaction.replace(R.id.fragmentContainerView, new UserInfoFragment());
            transaction.addToBackStack(null);
            transaction.commit();
                  break;
//                case "notifications":
//                    transaction.replace(R.id.fragmentContainerView, new HomeFragment());
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                    break;

                case "about":

                      transaction.replace(R.id.fragmentContainerView, new AboutUsFragment());
                      transaction.addToBackStack(null);
                      transaction.commit();
                      break;

                 case "paymentHistory":

                    transaction.replace(R.id.fragmentContainerView, new HomeFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case "support":

                    transaction.replace(R.id.fragmentContainerView, new SupportFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

            }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}