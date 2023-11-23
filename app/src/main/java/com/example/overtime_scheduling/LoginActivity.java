package com.example.overtime_scheduling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button signin;
    TextView signup, forgotPassword;
    DBHelper DB;
    CheckBox showPassword;
    CheckBox rememberLoginCheckBox;
    boolean isLoginChecked = false;
    boolean isLoginChecked1 = false;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        signin = findViewById(R.id.login_signin);
        signup = findViewById(R.id.login_signup);
        forgotPassword = findViewById(R.id.forgotPassword);
        showPassword = findViewById(R.id.showPassword);

        // Remember Login Checkbox Feature
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        rememberLoginCheckBox = findViewById(R.id.remLogin);

        // Check if "Remember Login" was previously selected
        boolean rememberLogin = sharedPreferences.getBoolean("rememberLogin", false);
        rememberLoginCheckBox.setChecked(rememberLogin);

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show password
                    password.setTransformationMethod(null);
                } else {
                    // Hide password
                    password.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        DB = new DBHelper(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Password = password.getText().toString();

                //Codes for Remember Me Checkbox
                // Check the "Remember Me" checkbox state
                boolean rememberLogin = rememberLoginCheckBox.isChecked();

                // Store the login state and username in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("rememberLogin", rememberLogin);
                editor.putString("userId", Username);
                editor.apply();

                String adminUsername = "admin";
                String adminPassword = "admin123";

                if(TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password)) {
                    if(TextUtils.isEmpty(Username)){
                        username.setError("Username can't be empty");
                    }
                    if(TextUtils.isEmpty(Password)){
                        password.setError("Password can't be empty");
                    }
                } else{
                    if (Username.equals(adminUsername)) {
                        if (Password.equals(adminPassword)) {
                            SharedPreferences sp = getSharedPreferences("rememberLogin", MODE_PRIVATE);
                            SharedPreferences.Editor spEditor = sp.edit();
                            if(isLoginChecked){
                                spEditor.putBoolean("rememberLogin", true);
                            } else {
                                spEditor.putBoolean("rememberLogin", false);
                            }
                            spEditor.apply();

                            //Shared Preference for just passing username to Main Activity
                            SharedPreferences sp1 = getSharedPreferences("PASS_USERNAME", MODE_PRIVATE);
                            SharedPreferences.Editor spEditor1 = sp1.edit();
                            spEditor1.putString("userId", Username);
                            spEditor1.apply();

                            //Shared Preference for passing Login Status
                            SharedPreferences sp2 = getSharedPreferences("LoginStatus", MODE_PRIVATE);
                            SharedPreferences.Editor spEditor2 = sp2.edit();
                            spEditor2.putBoolean("loginStatus", true);
                            spEditor2.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Boolean checkuserpass = DB.checkusernamepassword(Username, Password);
                        if(checkuserpass){
                            SharedPreferences sp = getSharedPreferences("USER_ID_INFO", MODE_PRIVATE);
                            SharedPreferences.Editor spEditor = sp.edit();
                            spEditor.putString("username", Username);
                            if(isLoginChecked){
                                spEditor.putBoolean("rememberLogin", true);
                            } else {
                                spEditor.putBoolean("rememberLogin", false);
                            }
                            spEditor.apply();

                            //Shared Preference for just passing username to Main Activity
                            SharedPreferences sp1 = getSharedPreferences("PASS_USERNAME", MODE_PRIVATE);
                            SharedPreferences.Editor spEditor1 = sp1.edit();
                            spEditor1.putString("userId", Username);
                            spEditor1.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //intent.putExtra("USER_ID", Username);
                            startActivity(intent);
                            finish();
                        } else{
                            username.setError("Invalid Username or Password !");
                            password.setError("Invalid Username or Password !");
                            Toast.makeText(LoginActivity.this, "Invalid Username or Password !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
