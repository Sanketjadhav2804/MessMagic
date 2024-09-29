package com.example.messmagic.Fragment;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.messmagic.MenuAdapter;
import com.example.messmagic.MenuModel;
import com.example.messmagic.R;
import com.example.messmagic.SignUpFormValidation;
import com.example.messmagic.menu.TiffinServiceModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class GuestServiceFragment extends Fragment {
    RecyclerView recyclerView2;
    List<MenuModel> MenuList2;

    private TextView TimeTextView,dateTextView;

    EditText name ,email,mobile,tiffinNo;


    private Button order;

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
  String textName ,textEmail ,textMobile ,textTiffinNo ,textTime;
    private ProgressDialog pd;


    public GuestServiceFragment() {
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
        return inflater.inflate(R.layout.fragment_guest_service, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        initData();
        setRecyclerView();
        init(view);

        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);



        dateTextView.setText(formattedDate);


            TimeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar=Calendar.getInstance();
                    int hours=calendar.get(Calendar.HOUR_OF_DAY);
                    int mins=calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog= new TimePickerDialog(requireContext() , new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar c=Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                            c.set(Calendar.MINUTE,minute);
                            c.setTimeZone(TimeZone.getDefault());
                            SimpleDateFormat format= new SimpleDateFormat("k:mm a");
                            String time = format.format(c.getTime());
                            TimeTextView.setText(time);
                        }
                    },hours ,mins,false);
                    timePickerDialog.show();
                }
            });

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pd.setMessage("Please Wait");
                    pd.show();

                    textName = name.getText().toString().trim();
                    textEmail = email.getText().toString().trim();
                    textMobile = mobile.getText().toString().trim();
                    textTiffinNo = tiffinNo.getText().toString().trim();
                    textTime = TimeTextView.getText().toString().trim();



                    if (!validation()) {
                        pd.dismiss();
                        //Toast.makeText(requireContext(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                    } else {


                        String userId=mAuth.getCurrentUser().getUid();
                        String uploadId = database.getReference().push().getKey();
                        String status ="Pending";
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);

                        TiffinServiceModel order = new TiffinServiceModel(userId ,textName, textEmail, textMobile, textTiffinNo, textTime ,uploadId ,status ,formattedDate, ServerValue.TIMESTAMP);

                        database.getReference().child("guestrequest").child(userId).child(uploadId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    // Dismiss the dialog when your task is complete

                                    pd.dismiss();
                                    Toast.makeText(requireContext(), "Mess Booked For You..", Toast.LENGTH_SHORT).show();

                                    name.setText(null);
                                    email.setText(null);
                                    mobile.setText(null);
                                    tiffinNo.setText(null);
                                    TimeTextView.setText(null);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(requireContext(), "Seat couldn't booked..Try Again", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }
            });

        }

    private boolean validation() {
        SignUpFormValidation validator = new SignUpFormValidation();
        if(!validator.isValidName(textName)){
            Toast.makeText(requireContext(), "Please fill Name Field contains only letters ", Toast.LENGTH_SHORT).show();
            name.requestFocus();
            return false;
        }else  if(textEmail.isEmpty() || !(validator.isValidEmail(textEmail))){
            Toast.makeText(requireContext(), "Please Enter Valid Email ID", Toast.LENGTH_SHORT).show();
            email.requestFocus();
            return false;
        }else  if(textMobile.isEmpty() || !validator.isValidMobileNumber(textMobile)){
            Toast.makeText(requireContext(), "Please enter valid Mobile Number", Toast.LENGTH_SHORT).show();
            mobile.requestFocus();
            return false;
        }else  if(textTiffinNo.isEmpty()){
            Toast.makeText(requireContext(), "Please enter tiffin number", Toast.LENGTH_SHORT).show();
            tiffinNo.requestFocus();
            return false;
        }

        return true;
    }

    private void init(View view) {

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.emailAddress);
        mobile=view.findViewById(R.id.mobile);
        tiffinNo=view.findViewById(R.id.people);
        TimeTextView=view.findViewById(R.id.time);

        dateTextView=view.findViewById(R.id.textView8);
        order=view.findViewById(R.id.btn_reg);
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        pd = new ProgressDialog(requireContext());
    }

    private void initData() {

        MenuList2=new ArrayList<>();
        MenuList2.add(new MenuModel("Puri Bhaji", "", R.drawable.puri_bhaji,"Puri ,Bhaji ,Chutney, Sweet , Raita"));
        MenuList2.add(new MenuModel("Paav Bhaji", "", R.drawable.paav_bhaji,"Paav , bhaji ,Daal , Rice , Sweet , Raita"));
        MenuList2.add(new MenuModel("Puran Poli", "", R.drawable.puranpoli,"Puranpoli, bhaji, sukhi Bhaji, Daal, Rice, Sweet, Raita"));
        MenuList2.add(new MenuModel("Veg Biryani", "", R.drawable.vegbiryani,"Biryani,Rassa ,Raita"));
        MenuList2.add(new MenuModel("Chhole Bhature", "", R.drawable.chhole_bhature,"Chhole Bowl, Bhature ,Sweet, Raita"));
        MenuList2.add(new MenuModel("Dosa", "", R.drawable.dosa,"Dosa, Sambar, Chutney"));
        MenuList2.add(new MenuModel("Idli Sambar", "", R.drawable.idli,"Idli, Sambar, Chutney"));


        // Set expandable to true for all items in the "Special Dishes" section

        for (MenuModel model : MenuList2) {
            model.setExpandable(true);
        }
    }

    private void setRecyclerView() {

        MenuAdapter menuAdapter2 = new MenuAdapter(MenuList2);
        recyclerView2.setAdapter(menuAdapter2);
        recyclerView2.setHasFixedSize(true);

    }

}