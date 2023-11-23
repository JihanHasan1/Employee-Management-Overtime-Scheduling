package com.example.overtime_scheduling;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class EmployeeListActivity extends AppCompatActivity {
    private ListView lvEmployees;
    ArrayList<Employee> Employees = new ArrayList<>();
    private CustomEmployeeAdapter adapter;

    private ImageView btnAdd;
    private TextView btnExit;
    private TextView tvTotalEmployees;

    //Codes for Search
    private ImageButton searchButton;
    private ImageButton clearSearchButton;
    private EditText searchEditText;
    private ArrayList<Employee> originalEmployees;

    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        tvTotalEmployees = findViewById(R.id.tvTotalEmployees);
        lvEmployees = findViewById(R.id.listEmployees);
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


        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeListActivity.this, EmployeeFormActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EmployeeListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // handle the click on an Employee-list item
        lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Position = Real Position
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i = new Intent(EmployeeListActivity.this, EmployeeFormActivity.class);
                i.putExtra("EMPLOYEE_KEY", Employees.get(position).key);
                startActivity(i);
                finish();
            }
        });
        // handle the long-click on an Employee-list item
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
                KeyValueDB db = new KeyValueDB(getApplicationContext());
                String employeeData = db.getValueByKey1(key);

                if (employeeData != null) {
                    String[] values = employeeData.split("###");

                    // Make sure there are enough values in the array before accessing them
                    if (values.length >= 6) {
                        // Extract the username from the combined data
                        String username = values[6];

                        // Delete the employee data
                        db.deleteDataByKey(key);

                        System.out.println("-------------------");
                        System.out.println(username);


                        // Also Delete from User Database
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        dbHelper.deleteUserByUsername(username);
                        Toast.makeText(EmployeeListActivity.this, "Deleted from User Successfully !", Toast.LENGTH_SHORT).show();
                    }
                }

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

    private void loadData(){
        System.out.println("I'm Inside LoadData");
        Employees.clear();
        KeyValueDB db = new KeyValueDB(EmployeeListActivity.this);
        Cursor rows = db.execute("SELECT * FROM key_value_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        else{
            while (rows.moveToNext()) {
                String key = rows.getString(0);
                String EmployeeData = rows.getString(1);
                String[] fieldValues = EmployeeData.split("###");

                System.out.println("FieldValues: "+fieldValues);

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
                System.out.println("Arraylist: " +e);
                total++;
            }


            Collections.sort(Employees, new Comparator<Employee>() {
                @Override
                public int compare(Employee obj1, Employee obj2) {
                    return Integer.compare(obj1.getSlotId(), obj2.getSlotId());
                }
            });

            System.out.println("I've completed loading data");
            String s = String.valueOf(total);
            tvTotalEmployees.setText(s);
            db.close();

            SharedPreferences sp = getSharedPreferences("total_emloyees", MODE_PRIVATE);
            SharedPreferences.Editor spEditor = sp.edit();
            spEditor.putString("total", s);
            spEditor.apply();

            adapter = new CustomEmployeeAdapter(this, Employees);
            lvEmployees.setAdapter(adapter);
            lvEmployees.setTextFilterEnabled(true);
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

    public void onRestart(){
        super.onRestart();
        //adapter.notifyDataSetChanged();
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