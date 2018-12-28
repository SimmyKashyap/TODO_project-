package com.example.nitinsharma.medicalalarmremainder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nitin sharma on 28-Nov-18.
 */

public class List extends RecyclerView.Adapter<List.ViewHolder> {

    ArrayList<Data> arrayList = new ArrayList<Data>();
    Context context;
    onClick listener;
    int id;
    Data data;
    String hour,min;



    public List(Context context, ArrayList<Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listitem, null, false);
        return new ViewHolder(v,listener);


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull List.ViewHolder holder, int position) {
        Data data = arrayList.get(position);
        id=data.getId();

        holder.title.setText(data.getTitle());
        if(data.getHour()<10){
             hour="0" + data.getHour();
        }
        else{
           hour=""+data.getHour();

        }
        if(data.getMinute()<10){
            min="0"+data.getMinute();

        }else {
           min="" + data.getMinute();
        }
        holder.time.setText(hour+":"+min);
        holder.title.setText(data.getTitle());

        if(data.getTitle_status().equals("true")){

            holder.title.setTextColor(Color.BLACK);
        }
        else{
            holder.title.setTextColor(R.color.disabledColor);
        }

        if(data.getTime_status().equals("true")){
            holder.time.setTextColor(Color.BLACK);
        }
        else{
            holder.time.setTextColor(R.color.disabledColor);
        }
        if(data.getStatus().equals("true")){
            holder.aSwitch.setChecked(true);
            Log.v("msg","hit");
        }
        else{
            holder.aSwitch.setChecked(false);
        }



    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public   class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;
        Switch aSwitch;


        public ViewHolder(@NonNull View itemView, final onClick listener) {
            super(itemView);
            title = itemView.findViewById(R.id.listtitle);
            time = itemView.findViewById(R.id.listtime);
            AlarmDatabase alarmDatabase;
            final SQLiteDatabase database;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String t = title.getText().toString().trim();
                            listener.itemClick(position);

                        }}

                }
            });


            aSwitch = itemView.findViewById(R.id.aSwitch);
            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String t = title.getText().toString().trim();
                            listener.switchClick(position, position+1,isChecked);
                            if(isChecked){
                                Log.v("njfdg","ch");
                                title.setTextColor(Color.BLACK);
                                time.setTextColor(Color.BLACK);

                            }else{
                                title.setTextColor(R.color.disabledColor);
                                time.setTextColor(R.color.disabledColor);
                            }

                        }
                    }

                }
            });
           /* aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            String t = title.getText().toString().trim();
                            listener.switchClick(position, id);

                        }
                    }
                }
            });*/


        }
    }


    public interface onClick {
        void switchClick(int pos,int bc,boolean ab);
        void itemClick(int pos);
    }

    public void onItemclick(onClick listener) {
        this.listener=listener;

    }


}
