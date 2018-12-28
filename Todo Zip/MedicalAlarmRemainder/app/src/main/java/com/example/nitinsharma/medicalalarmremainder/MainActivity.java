package com.example.nitinsharma.medicalalarmremainder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    AlarmDatabase alarmDatabase;
    SQLiteDatabase database;

    ArrayList<Data> arrayList;
    RecyclerView recyclerView;
    List list;
    int update;
    LinearLayout relativeLayout;
    AddAlarm addAlarm;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    boolean disable;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (arrayList.size() == 0) {
            relativeLayout.setVisibility(View.VISIBLE);
        } else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set status bar background color
        Window window = getWindow();
        Drawable background = getResources().getDrawable(R.drawable.bg);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));
        window.setBackgroundDrawable(background);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        addAlarm = new AddAlarm();

        alarmDatabase = new AlarmDatabase(getApplicationContext(), "Alarm Database", null, 1);
        database = alarmDatabase.getWritableDatabase();
        database = alarmDatabase.getReadableDatabase();
        relativeLayout = findViewById(R.id.alarm);
        arrayList = new ArrayList<>();
        list = new List(this, arrayList);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String tutorialKey = "SOME_KEY";
        Boolean firstTime = getPreferences(MODE_PRIVATE).getBoolean(tutorialKey, true);
        if (firstTime) {

            // here you do what you want to do - an activity tutorial in my case
            TapTargetView.showFor(this,                 // `this` is an Activity
                    TapTarget.forView(findViewById(R.id.fab), "Add Your First Remainder", "Tap this button to start adding remainder")
                            // All options below are optional
                            .outerCircleColor(R.color.colorPrimaryDark)      // Specify a color for the outer circle
                            .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                            .targetCircleColor(R.color.iconcolor)   // Specify a color for the target circle
                            .titleTextSize(20)                  // Specify the size (in sp) of the title text
                            .titleTextColor(R.color.white)      // Specify the color of the title text
                            .descriptionTextSize(16)            // Specify the size (in sp) of the description text
                            .descriptionTextColor(R.color.white)  // Specify the color of the description text
                            // Specify a color for both the title and description text
                            .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                            .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                            .drawShadow(true)                   // Whether to draw a drop shadow or not
                            .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                            .tintTarget(true)                   // Whether to tint the target view's color
                            .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                            // Specify a custom drawable to draw as the target
                            .targetRadius(60),                  // Specify the target radius (in dp)
                    new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);      // This call is optional

                        }
                    });

            getPreferences(MODE_PRIVATE).edit().putBoolean(tutorialKey, false).apply();
        } else {
        }


        Cursor cursor = database.rawQuery("SELECT * FROM Alarm;", null);


        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("Name");
            int indexx = cursor.getColumnIndex("Column_Id");

            int index2 = cursor.getColumnIndex("Hour");
            int index3 = cursor.getColumnIndex("Minute");
            int index4 = cursor.getColumnIndex("Status");
            int index5 = cursor.getColumnIndex("Time_Status");
            int index6 = cursor.getColumnIndex("Title_Status");

            String title = cursor.getString(index);
            int id = cursor.getInt(indexx);
            Log.v("id", "" + id);
            int hour = cursor.getInt(index2);
            int minute = cursor.getInt(index3);
            String status = cursor.getString(index4);
            String timeStatus = cursor.getString(index5);
            String titleStatus = cursor.getString(index6);

            Data bean = new Data(id, title, hour, minute, status, timeStatus, titleStatus);
            arrayList.add(bean);
        }
        if (arrayList.size() == 0) {
            relativeLayout.setVisibility(View.VISIBLE);

        } else {
            relativeLayout.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(list);


        ImageButton fab = findViewById(R.id.fab);
        list.onItemclick(new List.onClick() {
            @Override
            public void switchClick(int pos, int bc, boolean ab) {

                Log.v("pos", pos + " ");

                if (ab) {

                    Log.v("int", bc + " true");
                    ContentValues values = new ContentValues();
                    values.put("Status", "true");
                    values.put("Title_Status", "true");
                    values.put("Time_Status", "true");
                    database.update("Alarm", values, "Column_Id=" + bc, null);
                    Data data = arrayList.get(pos);
                    Log.v("time", data.getHour() + "M" +
                            data.getMinute() + "");


                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, data.getHour());
                    calendar.set(Calendar.MINUTE, data.getMinute());
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    if (disable) {
                        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
                        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
                        if (Build.VERSION.SDK_INT >= 23) {
                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else if (Build.VERSION.SDK_INT >= 19) {
                            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        } else {
                            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        }
                        Toast.makeText(MainActivity.this, "Remainder Enabled", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Log.v("int", bc + " false");
                    String query = "UPDATE Alarm SET Status='false',Title_Status='false',Time_Status='false' WHERE Column_Id=" + bc + ";";
                    database.execSQL(query);
                    Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.cancel(pendingIntent);
                    disable = true;
                    Toast.makeText(MainActivity.this, "Remainder Disabled", Toast.LENGTH_SHORT).show();


                }


            }

            @Override
            public void itemClick(int pos) {
                update = pos;

                Data data = arrayList.get(pos);
                String title = data.getTitle();
                int hour = data.getHour();
                int minute = data.getMinute();
                Intent i = new Intent(MainActivity.this, AddAlarm.class);
                i.putExtra("title", title);
                i.putExtra("hour", hour);
                i.putExtra("minute", minute);
                i.putExtra("position", update + 1);
                startActivityForResult(i, update);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddAlarm.class);
                startActivityForResult(i, 22);


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 22 && resultCode == RESULT_OK) {
            Log.v("msg", "success");

            String title1 = data.getStringExtra("Title");
            Integer hour = data.getIntExtra("Hour", 0);
            Integer min = data.getIntExtra("Min", 0);

            Data bean = new Data(title1, hour, min, "true", "true", "true");
            arrayList.add(bean);
            recyclerView.setAdapter(list);
            Toast.makeText(this, "Remainder Added Successfully", Toast.LENGTH_SHORT).show();


        }
        if (requestCode == update && resultCode == 101) {
            Toast.makeText(this, "Remainder Updated successfully", Toast.LENGTH_SHORT).show();
            String title1 = data.getStringExtra("Title");
            Integer hour = data.getIntExtra("Hour", 0);
            Integer min = data.getIntExtra("Min", 0);
            Data data1 = arrayList.get(update);
            data1.setTitle(title1);
            data1.setHour(hour);
            data1.setMinute(min);

            list.notifyItemChanged(update);
            recyclerView.invalidate();

        }
    }


}
