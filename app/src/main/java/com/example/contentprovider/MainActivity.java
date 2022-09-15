package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addUser, retreiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addUser = findViewById(R.id.btnAdd);
        retreiveData = findViewById(R.id.btnRetrieve);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddDetails(view);
            }
        });

        retreiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickShowDetails(view);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    public void onClickAddDetails(View view) {
        ContentValues values = new ContentValues();
        values.put(UsersProvider.name, ((EditText) findViewById(R.id.txtName)).getText().toString());
        getContentResolver().insert(UsersProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
    }
    @SuppressLint("Range")
    public void onClickShowDetails(View view) {
// Retrieve employee records
        TextView resultView= (TextView) findViewById(R.id.res);
        Cursor cursor =
                getContentResolver().query(Uri.parse("content://com.example.contentprovider.UserProvider/users"), null, null,
                        null, null);
        if(cursor.moveToFirst()) {
            StringBuilder strBuild=new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+ "-"+
                        cursor.getString(cursor.getColumnIndex("name")));
                cursor.moveToNext();
            }
            resultView.setText(strBuild);
        }
        else {
            resultView.setText("No Records Found");
        }
    }
}