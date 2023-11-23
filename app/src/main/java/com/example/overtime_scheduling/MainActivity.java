package com.example.overtime_scheduling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    ImageView imgProfile;
    TextView tvName;
    CardView addModeratorCard, moderatorListCard, addEmployeeCard, employeeListCard, schedulingCard, overtimeCard, contactCard, logoutCard;
    LinearLayout ll1, ll2, ll3, ll4;
    String userId = "";
    String storedName = "";
    String storedImageString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgProfile = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);

        ll1 = findViewById(R.id.ll1);
        ll2 = findViewById(R.id.ll2);
        ll3 = findViewById(R.id.ll3);
        ll4 = findViewById(R.id.ll4);

        addModeratorCard = findViewById(R.id.addModeratorCard);
        moderatorListCard = findViewById(R.id.moderatorListCard);
        addEmployeeCard = findViewById(R.id.addEmployeeCard);
        employeeListCard = findViewById(R.id.employeeListCard);
        schedulingCard = findViewById(R.id.schedulingCard);
        overtimeCard = findViewById(R.id.overtimeCard);
        contactCard = findViewById(R.id.contactCard);
        logoutCard = findViewById(R.id.logoutCard);


        // Remember Login Checkbox Feature
        SharedPreferences sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("LoginStatus", MODE_PRIVATE);

        // Check if "Remember Login" is selected
        boolean rememberLogin = sharedPreferences.getBoolean("rememberLogin", false);

        //Check if user is logged in
        boolean isLoggedIn = sharedPreferences.getBoolean("loginStatus", true);

        if (!isLoggedIn && !rememberLogin) {
            // "Remember Login" is not selected, show the login page
            System.out.println("I'm Here----------------------");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            SharedPreferences sp = getSharedPreferences("PASS_USERNAME", MODE_PRIVATE);
            userId = sp.getString("userId", "");

            if(userId.startsWith("m")){
                ll1.setVisibility(View.GONE);
                setDataForModerator();
            }
            if (userId.startsWith("e")) {
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                schedulingCard.setVisibility(View.GONE);
                setDataForEmployee();
            }

            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (userId.startsWith("e")) {
                        Intent i = new Intent(MainActivity.this, EmployeeFormActivity.class);
                        String key = getEmployeeKey(userId);
                        i.putExtra("EMPLOYEE_PROFILE_KEY", key);
                        startActivity(i);
                        finish();
                    }
                    if (userId.startsWith("m")) {
                        Intent i = new Intent(MainActivity.this, ModeratorFormActivity.class);
                        String key = getModeratorKey(userId);
                        i.putExtra("MODERATOR_PROFILE_KEY", key);
                        startActivity(i);
                        finish();
                    }
                }
            });

            addModeratorCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, ModeratorFormActivity.class);
                    startActivity(i);
                }
            });

            moderatorListCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, ModeratorListActivity.class);
                    startActivity(i);
                }
            });

            addEmployeeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, EmployeeFormActivity.class);
                    startActivity(i);
                    //finish();
                }
            });

            employeeListCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, EmployeeListActivity.class);
                    startActivity(i);
                    //finish();
                }
            });

            schedulingCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, SchedulingActivity.class);
                    startActivity(i);
                    //finish();
                }
            });

            overtimeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, OvertimeListActivity.class);
                    startActivity(i);
                    //finish();
                }
            });

            contactCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, ContactUsActivity.class);
                    startActivity(i);
                    //finish();
                }
            });

            logoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    SharedPreferences preferences1 = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor editor1 = preferences1.edit();
                    editor1.clear();
                    editor1.apply();

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }

    String getEmployeeKey(String userId){
        KeyValueDB db = new KeyValueDB(MainActivity.this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            System.out.println("Error Occurred !");
        }
        else{
            while (rows.moveToNext()) {
                String key = rows.getString(0);
                String EmployeeData = rows.getString(1);
                String[] fieldValues = EmployeeData.split("###");

                String userId1 = fieldValues[6];

                if(userId.equals(userId1)){
                    return key;
                }
            }
        }
        return null;
    }

    String getModeratorKey(String userId){
        Moderator_Data db = new Moderator_Data(MainActivity.this);
        Cursor rows = db.execute("SELECT * FROM moderator_pairs");
        if (rows.getCount() == 0) {
            System.out.println("Error Occurred !");
        }
        else{
            while (rows.moveToNext()) {
                String key = rows.getString(0);
                String ModeratorData = rows.getString(1);
                String[] fieldValues = ModeratorData.split("###");

                String userId1 = fieldValues[5];

                if(userId.equals(userId1)){
                    return key;
                }
            }
        }
        return null;
    }


    private void setDataForEmployee(){
        System.out.println("I'm Inside getdata");
        KeyValueDB db = new KeyValueDB(MainActivity.this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        else{
            while (rows.moveToNext()) {
                //String key = rows.getString(0);
                String EmployeeData = rows.getString(1);
                String[] fieldValues = EmployeeData.split("###");

                String name = fieldValues[0];
                String userId1 = fieldValues[6];
                String imageString = fieldValues[7];

                if(userId.equals(userId1)){
                    storedName = name;
                    storedImageString = imageString;
                    break;
                }
            }
            tvName.setText(storedName);

            byte[] bytes= Base64.decode(storedImageString,Base64.DEFAULT);
            // Initialize bitmap
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            // set bitmap on imageView
            imgProfile.setImageBitmap(bitmap);
        }
    }

    private void setDataForModerator(){
        System.out.println("I'm Inside getdata");
        Moderator_Data db1 = new Moderator_Data(MainActivity.this);
        Cursor rows = db1.execute("SELECT * FROM moderator_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        else{
            while (rows.moveToNext()) {
                //String key = rows.getString(0);
                String EmployeeData = rows.getString(1);
                String[] fieldValues = EmployeeData.split("###");

                String name = fieldValues[0];
                String userId1 = fieldValues[5];
                String imageString = fieldValues[6];

                if(userId.equals(userId1)){
                    storedName = name;
                    storedImageString = imageString;
                    break;
                }
            }
            tvName.setText(storedName);

            byte[] bytes= Base64.decode(storedImageString,Base64.DEFAULT);
            // Initialize bitmap
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            // set bitmap on imageView
            imgProfile.setImageBitmap(bitmap);
        }
    }

    private static final long DOUBLE_CLICK_INTERVAL = 1000;
    private long lastBackPressTime = 0;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastBackPressTime <= DOUBLE_CLICK_INTERVAL) {
            finishAffinity();
        } else {
            Toast.makeText(this, "Double Press Back to Exit App", Toast.LENGTH_SHORT).show();
        }

        lastBackPressTime = currentTime;
    }
}