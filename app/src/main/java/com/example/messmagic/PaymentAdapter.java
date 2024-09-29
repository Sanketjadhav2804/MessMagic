package com.example.messmagic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;


public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private Context mContext;
    private List<PaymentDataModel> mUploads;


    public PaymentAdapter(Context context, List<PaymentDataModel> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public PaymentAdapter.PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_payment, parent, false);
        return new PaymentAdapter.PaymentViewHolder(v);
    }




    @Override
    public void onBindViewHolder(PaymentAdapter.PaymentViewHolder holder, int position) {
        PaymentDataModel data = mUploads.get(position);
        holder.textViewName.setText("Name: "+data.getName());
        holder.textViewPayment.setText("Payment: "+data.getAmount());
        holder.textViewMobile.setText("Mob: "+data.getMobile());
        holder.textViewPaymentId.setText("PaymentID: "+data.getPaymnetId());
        holder.textViewDate.setText("Date: "+data.getDate());

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName , textViewPayment ,textViewMobile, textViewPaymentId , textViewDate;
        //public ImageView imageView;

        public PaymentViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.userName);
            textViewPayment = itemView.findViewById(R.id.payment);
            textViewMobile=itemView.findViewById(R.id.userMobile);
            textViewPaymentId=itemView.findViewById(R.id.paymentId);
            textViewDate=itemView.findViewById(R.id.date);

        }
    }
}
