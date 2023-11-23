package com.example.overtime_scheduling;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class EmployeeFormActivity extends AppCompatActivity {

    private ImageView imgView;
    private EditText etName, etEmail, etContact, etAddress, etBloodGroup, etUserId;
    private TextView textTop;
    //private EditText etSlotId;

    private Button btnRegister, btnCancel;
    private String imageString="";
    private String existingKey = "";
    Integer slot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_form);

        textTop = findViewById(R.id.textTop);
        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etContact = findViewById(R.id.contact);
        etAddress = findViewById(R.id.address);
        etBloodGroup = findViewById(R.id.bloodGroup);
        etUserId = findViewById(R.id.userId);

        imgView = findViewById(R.id.imgView);

        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        if(i.hasExtra("EMPLOYEE_KEY")){
            existingKey = i.getStringExtra("EMPLOYEE_KEY");
            KeyValueDB db = new KeyValueDB(EmployeeFormActivity.this);
            String value = db.getValueByKey(existingKey);
            String values[] = value.split("###");

            etName.setText(values[0]);
            etEmail.setText(values[1]);
            etContact.setText(values[2]);
            etAddress.setText(values[3]);
            etBloodGroup.setText(values[4]);
            slot = Integer.parseInt(values[5]);
            etUserId.setText(values[6]);
            //etSlotId.setText(String.valueOf(values[5]));

            imageString = values[7];
            byte[] bytes= Base64.decode(imageString,Base64.DEFAULT);
            // Initialize bitmap
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            // set bitmap on imageView
            imgView.setImageBitmap(bitmap);

            btnRegister.setText("Update");

            //db.close();
        }


        Intent i2 = getIntent();
        if(i2.hasExtra("EMPLOYEE_PROFILE_KEY")){
            existingKey = i.getStringExtra("EMPLOYEE_PROFILE_KEY");
            KeyValueDB db = new KeyValueDB(EmployeeFormActivity.this);
            String value = db.getValueByKey(existingKey);
            String values[] = value.split("###");

            etName.setText(values[0]);
            etEmail.setText(values[1]);
            etContact.setText(values[2]);
            etAddress.setText(values[3]);
            etBloodGroup.setText(values[4]);
            slot = Integer.parseInt(values[5]);
            etUserId.setText(values[6]);
            //etSlotId.setText(String.valueOf(values[5]));

            imageString = values[7];
            byte[] bytes= Base64.decode(imageString,Base64.DEFAULT);
            // Initialize bitmap
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            // set bitmap on imageView
            imgView.setImageBitmap(bitmap);

            textTop.setText("User Details");
            btnRegister.setVisibility(View.GONE);
            etName.setFocusable(false);
            etEmail.setFocusable(false);
            etContact.setFocusable(false);
            etAddress.setFocusable(false);
            etBloodGroup.setFocusable(false);
            etUserId.setFocusable(false);
            imgView.setFocusable(false);
            imgView.setClickable(false);

            //db.close();
        }

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String contact = etContact.getText().toString();
                String address = etAddress.getText().toString();
                String bloodGroup = etBloodGroup.getText().toString();
                int slot_id = slot;
                String userId = etUserId.getText().toString();
                String image = imageString;

                if(name.equals("") || email.equals("") || contact.equals("") || address.equals("") || bloodGroup.equals("") || userId.equals("") || image.equals("")){
                    if(name.equals("")){
                        etName.setError("Name Can't be empty");
                        //Toast.makeText(EmployeeFormActivity.this, "Insert Name", Toast.LENGTH_SHORT).show();
                    }
                    if (email.equals("")) {
                        etEmail.setError("Email Can't be empty");
                        //Toast.makeText(EmployeeFormActivity.this, "Insert Email", Toast.LENGTH_SHORT).show();
                    }
                    if (contact.equals("")) {
                        etContact.setError("Contact No. Can't be empty");
                        //Toast.makeText(EmployeeFormActivity.this, "Insert Contact No.", Toast.LENGTH_SHORT).show();
                    }
                    if (address.equals("")) {
                        etAddress.setError("Home Address Can't be empty");
                        //Toast.makeText(EmployeeFormActivity.this, "Insert Address", Toast.LENGTH_SHORT).show();
                    }
                    if (bloodGroup.equals("")) {
                        etBloodGroup.setError("Blood Group Can't be empty");
                        //Toast.makeText(EmployeeFormActivity.this, "Insert Blood Group", Toast.LENGTH_SHORT).show();
                    }
                    if (userId.equals("")) {
                        etUserId.setError("Username Can't be empty");
                        //Toast.makeText(EmployeeFormActivity.this, "Insert User ID", Toast.LENGTH_SHORT).show();
                    }
                    if (image.equals("")) {
                        Toast.makeText(EmployeeFormActivity.this, "Insert Image", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    if(isValid(email)){
                        if(isValidContact(contact)){
                            if(userId.startsWith("e")){
                                if (!checkStoredUserIdForEmployee(userId) || btnRegister.getText().toString() == "Update") {
                                    String value = name+"###"+email+"###"+contact+"###"+address+"###"+bloodGroup+"###"+slot_id+"###"+userId+"###"+image+"###";
                                    //System.out.println(value);
                                    KeyValueDB db = new KeyValueDB(EmployeeFormActivity.this);
                                    if(existingKey.length()==0){
                                        String key = name + System.currentTimeMillis();
                                        existingKey = key;
                                        //Write Code to Save Information
                                        db.insertKeyValue(key, value);
                                        Toast.makeText(EmployeeFormActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(EmployeeFormActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        db.updateValueByKey(existingKey, value);
                                        Toast.makeText(EmployeeFormActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(EmployeeFormActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    db.close();
                                } else {
                                    etUserId.setError("Username Already Exist");
                                }
                            } else {
                                etUserId.setError("The username must start with 'e'");
                            }
                        } else {
                            etContact.setError("Invalid Contact No.");
                            //Toast.makeText(EmployeeFormActivity.this, "Invalid Contact No. !", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        etEmail.setError("Invalid Email");
                        //Toast.makeText(EmployeeFormActivity.this, "Invalid Email !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            public boolean isValid(String email) {
                String validity = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";

                Pattern pat = Pattern.compile(validity);
                if (email == null)
                    return true;
                return pat.matcher(email).matches();
            }

            public boolean isValidContact(String validity) {
                return validity.matches("^(?:\\+88|88)?(01[3-9]\\d{8})$");
            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnRegister.getText().toString() == "Update"){
                    Intent i = new Intent(EmployeeFormActivity.this, EmployeeListActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i = new Intent(EmployeeFormActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
    }

    public Boolean checkStoredUserIdForEmployee(String userId){
        System.out.println("I'm Inside checkStoredUserIdForEmployee");
        KeyValueDB db = new KeyValueDB(EmployeeFormActivity.this);
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
                System.out.println("hi------+++-------");

                if (userId.equals(userId1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        // start activity result
        //noinspection deprecation
        startActivityForResult(Intent.createChooser(intent,"Select Image"),100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==100 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectImage(); // when permission is granted call method
        } else
        {
            // when permission is denied
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            Uri uri=data.getData(); // when result is ok, initialize uri
            new ImagePickerTask(uri).execute();
        }
    }

    //AsyncTask Implementation for Multithreading
    private class ImagePickerTask extends AsyncTask<Void, Void, Bitmap> {
        private final Uri uri;

        public ImagePickerTask(Uri uri) {
            this.uri = uri;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmapImage;
                bitmapImage = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream stream=new ByteArrayOutputStream(); // initialize byte stream
                bitmapImage.compress(Bitmap.CompressFormat.JPEG,50,stream); // compress Bitmap
                byte[] bytes=stream.toByteArray(); // Initialize byte array
                imageString= Base64.encodeToString(bytes, Base64.DEFAULT); // get base64 encoded string
                //System.out.println(imageString);

                return bitmapImage;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imgView.setImageBitmap(bitmap);
            }
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
        finish();
    }

}