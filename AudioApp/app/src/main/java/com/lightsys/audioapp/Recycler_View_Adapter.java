package com.lightsys.audioapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


/*  class Recycler_View_Adapter
        Creating a Recycling list with the size of "courseName"
        using the layout from "layout_course_listitem.xml"
 */
public class Recycler_View_Adapter extends RecyclerView.Adapter<Recycler_View_Adapter.ViewHolder>{
    private static final String TAG = "Recycler_View_Adapter";

    //The names of the courses to be displayed
    private ArrayList<String> mcourseNames = new ArrayList<>();

    private Context mContext;

    //Default constructor
    public Recycler_View_Adapter(ArrayList<Course> courseName, Context context){
        mContext = context;
        for (int i = 0; i < courseName.size(); i++){
            mcourseNames.add(courseName.get(i).name);
        }
    }

    @NonNull
    @Override
    //Recycling the view holders
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_course_listitem, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: called."); //Log for debugging

        viewHolder.courseName.setText(mcourseNames.get(position));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {

            /**
             *  This function is called when a list item is clicked/tapped.
             *  More functionality should be added to this function
             */
            @Override
            public void onClick(View v) {
                //Logging for debugging
                Log.d(TAG, "onClick: Clicked on : " + mcourseNames.get(position));
                Toast.makeText(mContext, mcourseNames.get(position), Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return mcourseNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        /** Variables */

        //Course name
        TextView courseName;

        //The layout for each list item
        RelativeLayout parentLayout;


        /** Constructor */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
