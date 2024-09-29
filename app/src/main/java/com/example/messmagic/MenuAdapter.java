package com.example.messmagic;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuVH> {
    List<MenuModel> MenuList ;
    public MenuAdapter(List<MenuModel> menuList) {
        MenuList = menuList;
    }
    @NonNull
    @Override
    public MenuAdapter.MenuVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row2, parent ,false);
        return new MenuVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.MenuVH holder, int position) {
        MenuModel menuModel= (MenuModel) MenuList.get(position);
        holder.weekDayTxt.setText(menuModel.getWeekDay());
        holder.menuNAmeTxt.setText(menuModel.getMenuNAme());
        holder.otherTxt.setImageResource(menuModel.getOther());
        holder.descTxt.setText(menuModel.getDesc());
        boolean isExpandable =((MenuModel) MenuList.get(position)).isExpandable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
        holder.relativeLayout2.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }
    @Override
    public int getItemCount() {
        return MenuList.size();
    }
    public class MenuVH extends RecyclerView.ViewHolder{
       TextView weekDayTxt , menuNAmeTxt  , descTxt;
      ImageView otherTxt;
       LinearLayout linearLayout,linearLayout2;
       RelativeLayout relativeLayout, relativeLayout2;
        public MenuVH(@NonNull View itemView) {
            super(itemView);
            weekDayTxt=itemView.findViewById(R.id.weekDay);
            menuNAmeTxt=itemView.findViewById(R.id.mneuName);
            otherTxt=itemView.findViewById(R.id.other);
            descTxt=itemView.findViewById(R.id.desc);
            linearLayout=itemView.findViewById(R.id.linear_layout1);
            linearLayout2=itemView.findViewById(R.id.linear_layout1);
            relativeLayout=itemView.findViewById(R.id.exapndable_relative);
            relativeLayout2=itemView.findViewById(R.id.exapndable_relative);

            linearLayout.setOnClickListener(v -> {
                MenuModel menuModel= MenuList.get(getAdapterPosition());
                menuModel.setExpandable(!menuModel.isExpandable());
                notifyItemChanged(getAdapterPosition());

            });

            linearLayout2.setOnClickListener(v -> {
                MenuModel menuModel= MenuList.get(getAdapterPosition());
               // menuModel.setExpandable(!menuModel.isExpandable());
                notifyItemChanged(getAdapterPosition());

            });
        }
    }
}
