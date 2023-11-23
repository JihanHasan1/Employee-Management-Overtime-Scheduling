package com.example.overtime_scheduling;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SchedulingActivity extends AppCompatActivity {

    private TextView tvTotal;
    private EditText etOvertime, etDate;
    private Button btnApply, btnCancel;
    private int total = 0;

    ArrayList<Employee> Employees = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);

        tvTotal = findViewById(R.id.tvTotal);
        etOvertime = findViewById(R.id.etOvertime);
        etDate = findViewById(R.id.etDate);
        btnApply = findViewById(R.id.btnApply);
        btnCancel = findViewById(R.id.btnCancel);
        countTotal();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String overtime = etOvertime.getText().toString();
                String dateTime = etDate.getText().toString();

                if(dateTime.equals("") || overtime.equals("")) {

                    if(dateTime.equals("")){
                        etDate.setError("This field can't be empty");
                    }
                    if(overtime.equals("")){
                        etOvertime.setError("This field can't be empty");
                    }
                    //Toast.makeText(SchedulingActivity.this, "Insert All Data !", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(overtime) > total) {
                    etOvertime.setError("Overtime Employee must be less than or equal to Total Employee");
                } else {
                        sort_increase();

                        Intent i = new Intent(SchedulingActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SchedulingActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void countTotal(){
        System.out.println("I'm Inside LoadData");
        KeyValueDB db = new KeyValueDB(SchedulingActivity.this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            total = 0;
        }
        else {
            while (rows.moveToNext()) {
                System.out.println("I've completed loading data");
                total++;
            }
        }
        String s = String.valueOf(total);
        tvTotal.setText("Total Employees : "+s);
        db.close();
    }

    public void sort_increase(){
        System.out.println("I'm Inside sort_increase");
        Employees.clear();
        KeyValueDB db = new KeyValueDB(SchedulingActivity.this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            System.out.println("No Data !!!!!!!!!!!");
            return;
        }
        else {
            System.out.println("Data Found !!!");
            while (rows.moveToNext()) {
                String key = rows.getString(0);
                String EmployeeData = rows.getString(1);
                String[] fieldValues = EmployeeData.split("###");

                String name = fieldValues[0];
                String email = fieldValues[1];
                String contact = fieldValues[2];
                String address = fieldValues[3];
                String bloodGroup = fieldValues[4];
                int slot_id = Integer.parseInt(fieldValues[5]);
                String userId = fieldValues[6];
                String imageString = fieldValues[7];

                Employee e = new Employee(key, name, email, contact, address, bloodGroup, slot_id, userId, imageString);
                Employees.add(e);
            }

            Collections.sort(Employees, new Comparator<Employee>() {
                @Override
                public int compare(Employee obj1, Employee obj2) {
                    return Integer.compare(obj1.getSlotId(), obj2.getSlotId());
                }
            });

            Overtime_Data new_db = new Overtime_Data(SchedulingActivity.this);

            Cursor new_rows = new_db.execute("SELECT * FROM overtime_pairs");
            if (new_rows.getCount() != 0) {
                Integer var = new_db.deleteAllData();
                if(var > 0){
                    System.out.println("All Data has been deleted");
                    //Toast.makeText(this, "All Data has been deleted", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Deletion Error");
                    //Toast.makeText(this, "Deletion Error", Toast.LENGTH_SHORT).show();
                }
            }

            String overtime = etOvertime.getText().toString();
            int selected = Integer.parseInt(overtime);
            String dateTime = etDate.getText().toString();


            int flag = 0;
            for(Employee obj : Employees) {
                if (flag == selected) {
                    System.out.println("Breaking");
                    break;
                }
                String key1 = obj.getKey();
                String name1 = obj.getName();
                String email1 = obj.getEmail();
                String contact1 = obj.getContact();
                String address1 = obj.getAddress();
                String bloodGroup1 = obj.getBloodGroup();
                int slot_id1 = obj.getSlotId();
                slot_id1 = slot_id1 + 1;
                String userId1 = obj.getUserId();
                String imageString1 = obj.getImageString();

                String newValue = name1 + "###" + email1 + "###" + contact1 + "###" + address1 + "###" + bloodGroup1 + "###" + slot_id1 + "###" + userId1 + "###" + imageString1 + "###";
                db.updateValueByKey(key1, newValue);

                String newValue1 = name1 + "###" + email1 + "###" + contact1 + "###" + address1 + "###" + bloodGroup1 + "###" + slot_id1 + "###"  + userId1 + "###" + imageString1 + "###" + dateTime + "###";
                new_db.insertKeyValue(key1, newValue1);

                flag++;
            }
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText field with the selected date
                EditText editTextDatePicker = findViewById(R.id.etDate);
                editTextDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, 2023, 9, 17); // Set the initial date to 17 September 2023
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}