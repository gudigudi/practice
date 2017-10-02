package com.gudigudigudi.mdtemplate;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataStoreActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DataStoreActivity";

    private EditText editText;

    private Button btn_save_data_by_sharedpreferences;
    private Button btn_restore_data_by_sharedpreferences;


    private BookDBHelper dbHelper;
    private Button btn_create_db;
    private Button btn_add_data_to_db;
    private Button btn_update_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_store);

        editText = (EditText) findViewById(R.id.edit);

        String inputText = load();

        if (!TextUtils.isEmpty(inputText)) {
            editText.setText(inputText);
            editText.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }

        btn_save_data_by_sharedpreferences = (Button) findViewById(R.id.btn_save_data_by_sharedpreferences);
        btn_restore_data_by_sharedpreferences = (Button) findViewById(R.id.btn_restore_data_by_sharedpreferences);


        dbHelper = new BookDBHelper(this, "Book.db", null, 2);
        btn_create_db = (Button) findViewById(R.id.btn_create_db);
        btn_add_data_to_db = (Button) findViewById(R.id.btn_add_data_to_db);
        btn_update_db = (Button) findViewById(R.id.btn_update_db);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String input = editText.getText().toString();
        save(input);
    }

    private void save(String input) {

        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String load() {

        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return content.toString();
    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase database;
        ContentValues values;

        switch (view.getId()) {
            case R.id.btn_save_data_by_sharedpreferences:
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                editor.apply();
                break;
            case R.id.btn_restore_data_by_sharedpreferences:
                SharedPreferences prefs = getSharedPreferences("data", MODE_PRIVATE);
                String name = prefs.getString("name", "");
                int age = prefs.getInt("age", 0);
                boolean married = prefs.getBoolean("married", false);

                Log.d(TAG, "name is " + name);
                Log.d(TAG, "age is " + age);
                Log.d(TAG, "married is " + married);
                break;
            case R.id.btn_create_db:
                dbHelper.getWritableDatabase();
                break;
            case R.id.btn_add_data_to_db:
                database = dbHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                database.insert("Book", null, values);

                values.clear();

                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                database.insert("Book", null, values);

                values.clear();

                break;
            case R.id.btn_update_db:
                database = dbHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("price", 10.99);
                database.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});
                values.clear();

                break;
            default:
                Log.d(TAG, "Unknown view is clicked");
        }

    }
}
