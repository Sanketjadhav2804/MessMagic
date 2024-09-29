package com.example.messmagic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.service.autofill.Dataset;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messmagic.FaqActivity;
import com.example.messmagic.LoginActivity;
import com.example.messmagic.MainActivity;
import com.example.messmagic.MembershipModel;
import com.example.messmagic.NotificationsActivity;
import com.example.messmagic.PaymentDataModel;
import com.example.messmagic.PaymentHistoryActivity;
import com.example.messmagic.PollActivity;
import com.example.messmagic.R;
import com.example.messmagic.SettingsActivity;
import com.example.messmagic.menu.TiffinServiceModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpCookie;


public class AccountFragment extends Fragment {


    TextView viewUserName, viewMobile ,viewMembership, userPrimaryInfo,usernotifications, userPaymentHistory, aboutUs, support, signout;

    CardView cvforSettings;
    Button loginAuthentication;

    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String userName;
    String mobile , membership;

    public AccountFragment() {

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_account, container, false);


        init(view);
        if (loginCheck()) {

            fetchDataFromFirebase();
            //showDetails();
        }
        allClickListeners();


        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

//    private void showDetails() {
//        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
//
//        // Retrieve data from SharedPreferences
//        userName = sharedPreferences.getString("userName", null);
//        mobile = sharedPreferences.getString("mobile", null);
//        membership = sharedPreferences.getString("membership", null);
//
//        if (userName == null || mobile == null || membership == null) {
//            // Data not available in SharedPreferences or invalid, fetch from Firebase
//            fetchDataFromFirebase();
//        } else {
//            // Data available in SharedPreferences, use it directly
//            viewUserName.setText(userName);
//            viewMobile.setText(mobile);
//            viewMembership.setText(" Membership till: " + membership);
//        }
//    }

    private void fetchDataFromFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child(mAuth.getCurrentUser().getUid()).child("name").getValue(String.class);
                mobile = snapshot.child(mAuth.getCurrentUser().getUid()).child("mobile").getValue(String.class);


                // Update SharedPreferences with the new data
//                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("userName", userName);
//                editor.putString("mobile", mobile);
//                editor.apply();

                viewUserName.setText(userName);
                viewMobile.setText(mobile);
                showMembership();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error in Database..", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showMembership() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("monthlyMember").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                membership = dataSnapshot.child("endDate").getValue(String.class);

                viewMembership.setText(" Membership is valid till: "+membership);

            }
            else{
                viewMembership.setVisibility(View.INVISIBLE);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error in Database..", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void allClickListeners() {

        userPrimaryInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SettingsActivity.class);
                intent.putExtra("tag", "primary");
                startActivity(intent);
            }
        });


        usernotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), NotificationsActivity.class);
               // intent.putExtra("tag", "notifications");
                startActivity(intent);
            }
        });

        userPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), PaymentHistoryActivity.class);
                intent.putExtra("tag", "paymentHistory");
                startActivity(intent);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), FaqActivity.class);
                intent.putExtra("tag", "support");
                startActivity(intent);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), SettingsActivity.class);
                intent.putExtra("tag", "about");
                startActivity(intent);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), "User Logged out Successfully" + mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

                mAuth.signOut();
                Intent mainIntent = new Intent(requireContext(), LoginActivity.class);
                startActivity(mainIntent);
                requireActivity().finish();
            }
        });


    }

    public boolean loginCheck() {

        try {
           String userID= mAuth.getCurrentUser().getUid();
           return true;
        } catch (Exception e) {
            loginAuthentication.setVisibility(View.VISIBLE);
            cvforSettings.setVisibility(View.GONE);
        }
        loginAuthentication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(requireContext(), LoginActivity.class);
                startActivity(mainIntent);
                requireActivity().finish();

            }
        });
        return false;
    }



    private void init(View view) {
        mAuth=FirebaseAuth.getInstance();

        cvforSettings=view.findViewById(R.id.cardView2);
        loginAuthentication=view.findViewById(R.id.btn_login);

        viewUserName=view.findViewById(R.id.userName);
        viewMobile=view.findViewById(R.id.userMobile);
        viewMembership=view.findViewById(R.id.membership);
        userPrimaryInfo=view.findViewById(R.id.info);

        usernotifications=view.findViewById(R.id.notifications);
        userPaymentHistory=view.findViewById(R.id.pHisto);
        aboutUs=view.findViewById(R.id.aboutUs);
        support=view.findViewById(R.id.support);
        signout=view.findViewById(R.id.sOut);
    }
}