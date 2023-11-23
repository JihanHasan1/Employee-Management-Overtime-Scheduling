package com.example.overtime_scheduling;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OvertimeListActivity extends AppCompatActivity {
    private ListView lvEmployees;
    private ArrayList<Employee> Employees = new ArrayList<>();
    private CustomEmployeeAdapter adapter;

    private TextView tvDateTime, tvSelectedEmployees;
    private TextView btnBack;
    String date = "";

    //Codes for Search
    private ImageButton searchButton;
    private ImageButton clearSearchButton;
    private EditText searchEditText;
    private ArrayList<Employee> originalEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overtime_list);

        tvDateTime = findViewById(R.id.tvDateTime);
        tvSelectedEmployees = findViewById(R.id.tvSelectedEmployees);
        btnBack = findViewById(R.id.btnBack);
        lvEmployees = findViewById(R.id.listOvertime);
        loadData();

        //Codes for Search
        searchEditText = findViewById(R.id.searchEditText);
        originalEmployees = new ArrayList<>(Employees);
        // Create and set the adapter
        adapter = new CustomEmployeeAdapter(this, Employees);
        lvEmployees.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString().toLowerCase().trim();
                filterEmployees(query);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        clearSearchButton = findViewById(R.id.clearSearchButton);
        clearSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clear the search input
                searchEditText.setText("");

                // Restore the original list of employees
                Employees.clear();
                Employees.addAll(originalEmployees);

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OvertimeListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        lvEmployees.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Do you want to delete Employee - "+Employees.get(position).name +" ?";
                showDialog(message, "Delete Employee", Employees.get(position).key);
                return true;
            }
        });
    }
    private void showDialog(String message, String title, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Overtime_Data db = new Overtime_Data(getApplicationContext());
                db.deleteDataByKey(key);
                dialog.cancel();
                loadData();
            }

        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }
    private void loadData() {
        System.out.println("I'm Inside LoadData of Overtime List");
        Employees.clear();
        Overtime_Data db = new Overtime_Data(OvertimeListActivity.this);
        Cursor rows = db.execute("SELECT * FROM overtime_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        else{
            int total = 0;
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
                date = fieldValues[8];

                Employee e = new Employee(key, name, email, contact, address, bloodGroup, slot_id, userId, imageString);
                Employees.add(e);
                total++;
            }

            Collections.sort(Employees, new Comparator<Employee>() {
                @Override
                public int compare(Employee obj1, Employee obj2) {
                    return Integer.compare(obj1.getSlotId(), obj2.getSlotId());
                }
            });

            System.out.println("I've completed sorting data");
            String s = String.valueOf(total);
            tvSelectedEmployees.setText(s);
            tvDateTime.setText(date);

            //db.close();

            adapter = new CustomEmployeeAdapter(this, Employees);
            lvEmployees.setAdapter(adapter);

        }
    }

    //Codes for Search
    private void filterEmployees(String query) {
        Employees.clear();

        if (query.isEmpty()) {
            Employees.addAll(originalEmployees); // Restore the original list
        } else {
            for (Employee employee : originalEmployees) {
                if (employee.getName().toLowerCase().contains(query)) {
                    Employees.add(employee);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();

        //Codes for Search
        String query = searchEditText.getText().toString().toLowerCase().trim();
        filterEmployees(query);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}