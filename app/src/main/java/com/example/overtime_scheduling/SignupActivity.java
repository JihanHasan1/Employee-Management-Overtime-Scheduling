package com.example.overtime_scheduling;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    EditText username, password, repassword;
    Button signup;
    TextView signin;
    DBHelper DB;
    UserDetails userDetails;
    String userId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.rePassword);

        signup = findViewById(R.id.signup);
        signin = findViewById(R.id.signin);
        DB = new DBHelper(this);
        DB.getWritableDatabase();


        userDetails = new UserDetails();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                userId = Username;
                String Password = password.getText().toString();
                String Repass = repassword.getText().toString();

                userDetails.setUsername(Username);
                userDetails.setUsername(Password);

                if(TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password) || TextUtils.isEmpty(Repass)){
                    if(TextUtils.isEmpty(Username)){
                        username.setError("Username can't be empty");
                    }
                    if(TextUtils.isEmpty(Password)){
                        password.setError("Password can't be empty");
                    }
                    if(TextUtils.isEmpty(Password)){
                        repassword.setError("Re-enter Password can't be empty");
                    }
                    //Toast.makeText(SignupActivity.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }

                else{
                    if(Password.equals(Repass)) {
                        if (checkStoredUserIdForEmployee(Username) || checkStoredUserIdForModerator(Username)) {
                            Boolean checkuser = DB.checkusername(Username);
                            if (!checkuser) {
                                Boolean insert = DB.insertData(Username, Password);
                                if (insert) {
                                    Toast.makeText(SignupActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                username.setError("Username Already Exists");
                                //Toast.makeText(SignupActivity.this, "Username Already Exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            username.setError("No such Username !");
                            //Toast.makeText(SignupActivity.this, "No Such User ID !", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        repassword.setError("Re-entered password isn't matching");
                        //Toast.makeText(SignupActivity.this, "Passwords aren't Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public Boolean checkStoredUserIdForEmployee(String userId){
        System.out.println("I'm Inside checkStoredUserIdForEmployee");
        KeyValueDB db = new KeyValueDB(SignupActivity.this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return false;
        }
        else {
            while (rows.moveToNext()) {
                //String key = rows.getString(0);
                String EmployeeData = rows.getString(1);
                String[] fieldValues = EmployeeData.split("###");

                String userId1 = fieldValues[6];

                if (userId.equals(userId1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkStoredUserIdForModerator(String userId){
        System.out.println("I'm Inside checkStoredUserIdForModerator");
        Moderator_Data db = new Moderator_Data(SignupActivity.this);
        Cursor rows = db.execute("SELECT * FROM moderator_pairs");
        if (rows.getCount() == 0) {
            return false;
        }
        else {
            while (rows.moveToNext()) {
                //String key = rows.getString(0);
                String ModeratorData = rows.getString(1);
                String[] fieldValues = ModeratorData.split("###");

                String userId1 = fieldValues[5];

                if (userId.equals(userId1)) {
                    return true;
                }
            }
        }
        return false;
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
