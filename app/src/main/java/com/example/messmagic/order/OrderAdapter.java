package com.example.messmagic.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messmagic.R;
import com.example.messmagic.menu.TiffinServiceModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context mContext;
    private List<TiffinServiceModel> mUploads;

    private DatabaseReference mDatabaseRef;

    public OrderAdapter(Context context, List<TiffinServiceModel> uploads , DatabaseReference databaseReference) {
        mContext = context;
        mUploads = uploads;
        mDatabaseRef=databaseReference;

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.roworder, parent, false);
        return new OrderViewHolder(v);
    }




    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        TiffinServiceModel order = mUploads.get(position);
       holder.textViewPayment.setText("Payment: "+order.getAmount());
        holder.textViewTiffinNo.setText("Tffin: "+order.getTiffinNo());
        holder.textViewMobile.setText("Mob: "+order.getMobile());
        holder.textViewTime.setText("Time: "+order.getTime());
        holder.textViewDate.setText(""+order.getDate());


        // Change button visibility based on status
        if ("Accepted".equals(order.getStatus())) { // Use .equals for string comparison
            holder.pending.setVisibility(View.INVISIBLE);
            holder.accept.setVisibility(View.VISIBLE);
        } else if("Rejected".equals(order.getStatus())){
            holder.pending.setVisibility(View.INVISIBLE);
            holder.accept.setVisibility(View.INVISIBLE);
            holder.reject.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPayment , textViewTiffinNo ,textViewMobile , textViewTime ,textViewDate;
        public ImageView imageView;

        AppCompatButton accept , pending , reject;
        public OrderViewHolder(View itemView) {
            super(itemView);

            textViewPayment = itemView.findViewById(R.id.payment);
            textViewTiffinNo = itemView.findViewById(R.id.tiffinNo);
            textViewMobile=itemView.findViewById(R.id.userMobile);
            textViewTime=itemView.findViewById(R.id.time);
            textViewDate=itemView.findViewById(R.id.date);
            accept=itemView.findViewById(R.id.button_accept);
            pending=itemView.findViewById(R.id.btn_pending);
            reject=itemView.findViewById(R.id.button_reject);

        }
    }
}

