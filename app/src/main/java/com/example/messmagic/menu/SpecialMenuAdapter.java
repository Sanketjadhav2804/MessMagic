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


public class SpecialMenuAdapter extends RecyclerView.Adapter<SpecialMenuAdapter.ImageViewHolder> {
    private Context mContext;
    private List<UploadSpecialMenu> mUploads;

    public SpecialMenuAdapter(Context context, List<UploadSpecialMenu> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row, parent, false);
        return new ImageViewHolder(v);
    }




    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        UploadSpecialMenu uploadCurrent = mUploads.get(position);
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
        public TextView textViewName , textViewDescription;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.mneuName);
            imageView = itemView.findViewById(R.id.other);
            textViewDescription=itemView.findViewById(R.id.desc);
        }
    }
}

