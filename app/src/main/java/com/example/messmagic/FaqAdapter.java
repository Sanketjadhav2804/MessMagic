package com.example.messmagic;

// FaqAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FaqAdapter extends ArrayAdapter<FaqItem> {

    public FaqAdapter(Context context, List<FaqItem> faqList) {
        super(context, 0, faqList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FaqItem faqItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_faq, parent, false);
        }

        TextView faqItemTextView = convertView.findViewById(R.id.faqItemTextView);
        faqItemTextView.setText(faqItem.getQuestion());

        return convertView;
    }
}
