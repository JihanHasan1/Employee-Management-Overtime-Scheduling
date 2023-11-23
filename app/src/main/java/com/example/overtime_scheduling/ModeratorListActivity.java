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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ModeratorListActivity extends AppCompatActivity {
    private ListView lvModerators;
    ArrayList<Moderator> Moderators = new ArrayList<>();
    private CustomModeratorAdapter adapter;

    private ImageView btnAdd;
    private TextView btnExit;
    private TextView tvTotalModerators;

    //Codes for Search
    private ImageButton searchButton;
    private ImageButton clearSearchButton;
    private EditText searchEditText;
    private ArrayList<Moderator> originalModerators;

    private int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator_list);

        tvTotalModerators = findViewById(R.id.tvTotalModerators);
        lvModerators = findViewById(R.id.listModerators);
        loadData();

        //Codes for Search
        searchEditText = findViewById(R.id.searchEditText);
        originalModerators = new ArrayList<>(Moderators);
        // Create and set the adapter
        adapter = new CustomModeratorAdapter(this, Moderators);
        lvModerators.setAdapter(adapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString().toLowerCase().trim();
                filterModerators(query);
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

                // Restore the original list of moderators
                Moderators.clear();
                Moderators.addAll(originalModerators);

                // Notify the adapter of the data change
                adapter.notifyDataSetChanged();
            }
        });

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ModeratorListActivity.this, ModeratorFormActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ModeratorListActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        // handle the click on an Moderator-list item
        lvModerators.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //Position = Real Position
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i = new Intent(ModeratorListActivity.this, ModeratorFormActivity.class);
                i.putExtra("MODERATOR_KEY", Moderators.get(position).key);
                startActivity(i);
                finish();
            }
        });
        // handle the long-click on an Moderator-list item
        lvModerators.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "Do you want to delete Moderator - "+Moderators.get(position).name +" ?";
                showDialog(message, "Delete Moderator", Moderators.get(position).key);
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
                Moderator_Data db = new Moderator_Data(getApplicationContext());
                String moderatorData = db.getValueByKey1(key);

                if (moderatorData != null) {
                    String[] values = moderatorData.split("###");

                    // Make sure there are enough values in the array before accessing them
                    if (values.length >= 6) {
                        // Extract the username from the combined data
                        String username = values[5];

                        // Delete the moderator data
                        db.deleteDataByKey(key);

                        // Also Delete from User Database
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        dbHelper.deleteUserByUsername(username);
                        //Toast.makeText(ModeratorListActivity.this, "Deleted from User Successfully !", Toast.LENGTH_SHORT).show();
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
        Moderators.clear();
        Moderator_Data db = new Moderator_Data(ModeratorListActivity.this);
        Cursor rows = db.execute("SELECT * FROM moderator_pairs");
        if (rows.getCount() == 0) {
            return;
        }
        else{
            while (rows.moveToNext()) {
                String key = rows.getString(0);
                String ModeratorData = rows.getString(1);
                String[] fieldValues = ModeratorData.split("###");

                String name = fieldValues[0];
                String email = fieldValues[1];
                String contact = fieldValues[2];
                String address = fieldValues[3];
                String bloodGroup = fieldValues[4];
                String userId = fieldValues[5];
                String imageString = fieldValues[6];

                Moderator e = new Moderator(key, name, email, contact, address, bloodGroup, userId, imageString);
                Moderators.add(e);
                total++;
            }

            System.out.println("I've completed loading data");
            String s = String.valueOf(total);
            tvTotalModerators.setText(s);
            db.close();

            adapter = new CustomModeratorAdapter(this, Moderators);
            lvModerators.setAdapter(adapter);
        }
    }

    //Codes for Search
    private void filterModerators(String query) {
        Moderators.clear();

        if (query.isEmpty()) {
            Moderators.addAll(originalModerators); // Restore the original list
        } else {
            for (Moderator moderator : originalModerators) {
                if (moderator.getName().toLowerCase().contains(query)) {
                    Moderators.add(moderator);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }


    public void onRestart(){
        super.onRestart();
        loadData();

        //Codes for Search
        String query = searchEditText.getText().toString().toLowerCase().trim();
        filterModerators(query);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}