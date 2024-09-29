package com.example.messmagic.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messmagic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserInfoFragment extends Fragment {

    TextView name , mobile ,email , address ;

    ImageView backArrow;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
FirebaseAuth mAuth;

    public UserInfoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void init(View view) {

        name= view.findViewById(R.id.userName);
        mobile=view.findViewById(R.id.userMobile);
        email=view.findViewById(R.id.userEmail);
        backArrow = view.findViewById(R.id.backArrow);
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        showDetails();



            backArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().finish();
                }
            });
        }

        public void inita(View v){
            backArrow= v.findViewById(R.id.backArrow);
        }


    private void showDetails() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userName = snapshot.child(mAuth.getCurrentUser().getUid()).child("name").getValue(String.class);
                String usermobile = snapshot.child(mAuth.getCurrentUser().getUid()).child("mobile").getValue(String.class);
                String useremail = snapshot.child(mAuth.getCurrentUser().getUid()).child("email").getValue(String.class);
                //String usermobile = snapshot.child(mAuth.getCurrentUser().getUid()).child("mobile").getValue(String.class);

                name.setText("Name : "+userName);
               mobile.setText("Mobile : "+usermobile);
               email.setText("Email : "+useremail);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error in Database..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}