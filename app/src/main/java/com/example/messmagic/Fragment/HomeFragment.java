     package com.example.messmagic.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.messmagic.LoginActivity;
import com.example.messmagic.MainActivity;
import com.example.messmagic.MainServices;
import com.example.messmagic.PaymentForm;
import com.example.messmagic.PaymentHistoryActivity;
import com.example.messmagic.R;
import com.example.messmagic.SignupActivity;
import com.example.messmagic.TiffinServiceActivity;
import com.example.messmagic.menu.ImagesActivity;
import com.example.messmagic.order.OrderActivity;

import java.util.ArrayList;

     public class HomeFragment extends Fragment implements ItemClickListener {

  ItemClickListener itemClickListener=this;
   CardView cv1 , cv2 , cv3 , cv4;


    Button fetch  ;
    public HomeFragment() {
        // Required empty public constructor
    }



    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

                    // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


         @Override
         public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
             super.onViewCreated(view, savedInstanceState);

            ScrollView sView = view.findViewById(R.id.scroll);
             sView.setVerticalScrollBarEnabled(false);
             sView.setHorizontalScrollBarEnabled(false);

            HorizontalScrollView hView = view.findViewById(R.id.horiscroll);
             hView.setVerticalScrollBarEnabled(false);
             hView.setHorizontalScrollBarEnabled(false);


             ArrayList imageList=new ArrayList();
             imageList.add(new SlideModel(R.drawable.messbanner1, ScaleTypes.FIT ));
             imageList.add(new SlideModel(R.drawable.messbanner2, ScaleTypes.FIT));
             imageList.add(new SlideModel(R.drawable.messbanner3, ScaleTypes.FIT));

             // Find the ImageSlider in the layout
             ImageSlider imageSlider=view.findViewById(R.id.image_slider) ;


             imageSlider.setImageList(imageList , ScaleTypes.FIT);


             itemClickListener=this;
             imageSlider.setItemClickListener(itemClickListener);



             // Code for CardViews Mothly Mess , Tiffin Service , Guest service , payment service

             init(view);

             cv1.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(requireContext() , PaymentForm.class);
                     intent.putExtra("tag" ,"monthlyMess");
                     startActivity(intent);

                 }
             });

             cv2.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(requireContext() , MainServices.class);
                     intent.putExtra("tag" ,"guestService");
                     startActivity(intent);

                 }
             });

             cv3.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(requireContext() , TiffinServiceActivity.class);
                     //intent.putExtra("tag" ,"tiffinService");
                     startActivity(intent);

                 }
             });

             cv4.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent intent = new Intent(requireContext() , PaymentHistoryActivity.class);
                     intent.putExtra("tag" ,"paymentService");
                     startActivity(intent);

                 }
             });







         }

         private void init(View view) {

        cv1=view.findViewById(R.id.cardView1);
        cv2=view.findViewById(R.id.cardView2);
        cv3=view.findViewById(R.id.cardView3);
        cv4=view.findViewById(R.id.cardView4);


         }

         @Override
         public void doubleClick(int i) {
             }

         @Override
         public void onItemSelected(int i) {

//             Intent mainIntent = new Intent(requireContext(), SignupActivity.class);
//             startActivity(mainIntent);
         }


     }