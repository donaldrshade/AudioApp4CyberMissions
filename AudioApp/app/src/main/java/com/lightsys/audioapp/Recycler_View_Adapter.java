package com.lightsys.audioapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class Recycler_View_Adapter extends RecyclerView.Adapter<Recycler_View_Adapter.ViewHolder>{
    private static final String TAG = "Recycler_View_Adapter";

    private ArrayList<String> mTextNames = new ArrayList<>();
    private Context mContext;

    public Recycler_View_Adapter(ArrayList<String> textName, Context context){
        mContext = context;
        mTextNames = textName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_course_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.textName.setText(mTextNames.get(position));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on : " + mTextNames.get(position));

                Toast.makeText(mContext, mTextNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTextNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textName;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
