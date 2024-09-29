//package com.example.messmagic.Fragment;
//
//import android.app.ProgressDialog;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.example.messmagic.LoginActivity;
//import com.example.messmagic.R;
//import com.example.messmagic.SignupActivity;
//import com.example.messmagic.menu.TiffinServiceModel;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.TimeZone;
//
//
//public class TiffinServiceFragment extends Fragment {
//
//    private TextView TimeTextView;
//
//    EditText name ,email,mobile,tiffinNo;
//
//    Double amount;
//
//    private Button order;
//
//    FirebaseAuth mAuth;
//    FirebaseDatabase database;
//    FirebaseStorage storage;
//
//    private ProgressDialog pd;
//    public TiffinServiceFragment() {
//        // Required empty public constructor
//    }
//
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tiffin_service, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        init(view);
//        TimeTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar=Calendar.getInstance();
//                int hours=calendar.get(Calendar.HOUR_OF_DAY);
//                int mins=calendar.get(Calendar.MINUTE);
//                TimePickerDialog timePickerDialog= new TimePickerDialog(requireContext() , new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                        Calendar c=Calendar.getInstance();
//                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
//                        c.set(Calendar.MINUTE,minute);
//                        c.setTimeZone(TimeZone.getDefault());
//                        SimpleDateFormat format= new SimpleDateFormat("k:mm a");
//                        String time = format.format(c.getTime());
//                        TimeTextView.setText(time);
//                    }
//                },hours ,mins,false);
//                timePickerDialog.show();
//            }
//        });
//
//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                pd.setMessage("Please Wait");
//                pd.show();
//
//                String textName = name.getText().toString().trim();
//                String textEmail = email.getText().toString().trim();
//                String textMobile = mobile.getText().toString().trim();
//                String textTiffinNo = tiffinNo.getText().toString().trim();
//                String textTime = TimeTextView.getText().toString().trim();
//                 amount=80*Double.parseDouble(textTiffinNo);
//
//                if (textName.isEmpty() ||
//                        textEmail.isEmpty() ||
//                        textMobile.isEmpty() ||
//                        textTiffinNo.isEmpty() ||
//                        textTime.isEmpty()) {
//                    pd.dismiss();
//                    Toast.makeText(requireContext(), "Please Enter All Fields", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    String userId=mAuth.getCurrentUser().getUid();
//                    String uploadId = database.getReference().push().getKey();
//                    String status ="Pending";
//
//                    Date c = Calendar.getInstance().getTime();
//                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//                    String formattedDate = df.format(c);
//
//                    TiffinServiceModel order = new TiffinServiceModel( userId ,textName, textEmail, textMobile, textTiffinNo, textTime,uploadId,status ,formattedDate,amount);
//
//
//
//                    database.getReference().child("tiffinorder").child(userId).child(uploadId).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//
//                                // Dismiss the dialog when your task is complete
//
//                                pd.dismiss();
//                                Toast.makeText(getActivity().getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
//
//                                name.setText(null);
//                                email.setText(null);
//                                mobile.setText(null);
//                                tiffinNo.setText(null);
//                                TimeTextView.setText(null);
//                            }
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            pd.dismiss();
//                            Toast.makeText(requireContext(), "Order couldn't Placed..Try Again", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//        });
//
//    }
//    private void init(View view) {
//        name=view.findViewById(R.id.name);
//        email=view.findViewById(R.id.emailAddress);
//        mobile=view.findViewById(R.id.mobile);
//        tiffinNo=view.findViewById(R.id.people);
//        TimeTextView=view.findViewById(R.id.time);
//        order=view.findViewById(R.id.btn_reg);
//        mAuth=FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        pd = new ProgressDialog(requireContext());
//    }
//}