package com.example.messmagic.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messmagic.MenuAdapter;
import com.example.messmagic.MenuModel;
import com.example.messmagic.R;
import com.example.messmagic.menu.ImageAdapter;
import com.example.messmagic.menu.ImagesActivity;
import com.example.messmagic.menu.SpecialMenuAdapter;
import com.example.messmagic.menu.UploadSpecialMenu;
import com.example.messmagic.menu.UploadWeekMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messmagic.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class MenuFragment extends Fragment {

    private RecyclerView mRecyclerView , mRecyclerView1;
    private ImageAdapter mAdapter;
    private SpecialMenuAdapter sAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef ,sDatabaseRef;
    private List<UploadWeekMenu> mUploads;

    private List<UploadSpecialMenu>sUploads;




    public MenuFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


                mRecyclerView1 = view.findViewById(R.id.recyclerView);

                mRecyclerView1.setLayoutManager(new LinearLayoutManager(requireContext()));

                mRecyclerView = view.findViewById(R.id.recyclerView2);
                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                 mProgressCircle = view.findViewById(R.id.progress_circle);



                mUploads = new ArrayList<>();
                sUploads = new ArrayList<>();

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("weekmenu");

                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            UploadWeekMenu upload = postSnapshot.getValue(UploadWeekMenu.class);
                            mUploads.add(upload);
                        }

                        mAdapter = new ImageAdapter(requireContext(), mUploads);
                        mRecyclerView1.setAdapter(mAdapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(requireContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        mProgressCircle.setVisibility(View.INVISIBLE);
                    }
                });


        sDatabaseRef = FirebaseDatabase.getInstance().getReference("specialmenu");

        sDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    UploadSpecialMenu upload1 = postSnapshot.getValue(UploadSpecialMenu.class);
                    sUploads.add(upload1);
                }

                sAdapter = new SpecialMenuAdapter(requireContext(), sUploads);
                mRecyclerView.setAdapter(sAdapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(requireContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
            }
}



