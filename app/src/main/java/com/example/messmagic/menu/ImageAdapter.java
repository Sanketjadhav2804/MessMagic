package com.example.messmagic.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messmagic.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<UploadWeekMenu> mUploads;

    public ImageAdapter(Context context, List<UploadWeekMenu> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row1, parent, false);
        return new ImageViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        UploadWeekMenu uploadCurrent = mUploads.get(position);
        holder.textViewWeekName.setText(uploadCurrent.getWeekday());
        holder.textViewName.setText(uploadCurrent.getMenuName());
        holder.textViewDescription.setText(uploadCurrent.getMenuDescription());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewWeekName,textViewName , textViewDescription;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewWeekName=itemView.findViewById(R.id.weekDay);
            textViewName = itemView.findViewById(R.id.mneuName);
            imageView = itemView.findViewById(R.id.other);
            textViewDescription=itemView.findViewById(R.id.desc);
        }
    }
}
