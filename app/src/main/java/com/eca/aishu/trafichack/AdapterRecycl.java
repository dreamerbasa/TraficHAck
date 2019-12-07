package com.eca.aishu.trafichack;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycl extends RecyclerView.Adapter<AdapterRecycl.ViewHolder> {

    private ArrayList<Image> mDataset;
    private MainActivity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public TextView mTextView2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.textView1);
            mTextView = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
    public AdapterRecycl(ArrayList<Image> myDataset, MainActivity activity) {
        mDataset = myDataset;
        mActivity = activity;
    }
    @NonNull
    @Override
    public AdapterRecycl.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_activity, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycl.ViewHolder holder, int position) {

        holder.mTextView.setText("xx");
        holder.mTextView2.setText("xx");
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
