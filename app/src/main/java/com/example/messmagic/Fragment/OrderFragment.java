package com.example.messmagic.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.messmagic.R;
import com.example.messmagic.menu.TiffinServiceModel;
import com.example.messmagic.order.OrderActivity;
import com.example.messmagic.order.OrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OrderFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;

    private ProgressBar mProgressCircle;


    FirebaseAuth mAuth;

    ImageView backArrow;
    private DatabaseReference mDatabaseRef;
    private List<TiffinServiceModel> mUploads,orderedUpload;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView = view.findViewById(R.id.userListRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        mAuth=FirebaseAuth.getInstance();
        mProgressCircle = view.findViewById(R.id.progress_circle);
        backArrow=view.findViewById(R.id.backArrow);



        mUploads = new ArrayList<>();
        orderedUpload= new ArrayList<>();
        String userId=mAuth.getCurrentUser().getUid();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("tiffinorder").child(userId);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    TiffinServiceModel upload = postSnapshot.getValue(TiffinServiceModel.class);
                    mUploads.add(upload);
                }

                //To reverse the list
                orderedUpload.clear(); // Clear previous data
                for (int i = mUploads.size() - 1; i >= 0; i--) {
                    orderedUpload.add(mUploads.get(i));

                }

                if (getActivity() != null) {
                    mAdapter = new OrderAdapter(getActivity().getApplicationContext(), orderedUpload, mDatabaseRef);
                    mRecyclerView.setAdapter(mAdapter);
                    mProgressCircle.setVisibility(View.INVISIBLE);
                } else {
                    // Fragment is not attached to any activity, handle the situation
                    // For instance, you might log an error or display a message to the user
                    Log.e("OrderFragment", "Fragment is not attached to any activity");
                    // Show a message or perform other appropriate actions
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(requireContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    }
