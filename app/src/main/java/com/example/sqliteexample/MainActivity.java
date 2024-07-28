package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnMakeDB, btnMakeTb, btnNext, btnPre, btnInsert, btnLoad,
            btnUpdate, btnDelete;
    EditText edtTen;
    SQLiteDatabase DB;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        CreateOrOpenDB();
        Act();

    }

    private void Act() {
        btnMakeDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrOpenDB();
            }
        });

        btnMakeTb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeOrTable();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertData();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadAllData();
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToPreCursor();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToNextCursor();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
            }
        });
    }

    private void DeleteData() {
        if (cursor == null){
            Toast.makeText(this, "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
        }else {
                int ID = cursor.getInt(0);

                String sql_delete = "DELETE FROM DANHSACH WHERE ID = '"+ID+"'";

                DB.execSQL(sql_delete);
                LoadAllData();
        }
    }

    private void UpdateData() {
        if (cursor == null){
            Toast.makeText(this, "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
        }else {
            if(edtTen.getText().toString().isEmpty()){
                Toast.makeText(this, "DATA IS NULL", Toast.LENGTH_SHORT).show();
            }else {
                int ID = cursor.getInt(0);
                String newTEN = edtTen.getText().toString().trim();
                String sql_update = "UPDATE DANHSACH set TENHS ='"+newTEN+"' " +
                        "WHERE ID = '"+ID+"'";
                DB.execSQL(sql_update);
                LoadAllData();
            }
        }
    }

    private void MoveToNextCursor() {
        if(cursor==null){
            Toast.makeText(this, "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
        }else {
            if (!cursor.isLast()){
                cursor.moveToNext();
                edtTen.setText(cursor.getString(1));
            }else {
                Toast.makeText(this, "AT THE END", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void MoveToPreCursor() {
        if (cursor==null){
            Toast.makeText(this, "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
        }else {
            if (!cursor.isFirst()){
                cursor.moveToPrevious();
                edtTen.setText(cursor.getString(1));
            }else {
                Toast.makeText(this, "AT THE FIRST", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void LoadAllData() {
        cursor = DB.rawQuery("SELECT * FROM DANHSACH",null);
        if (cursor == null){
            Toast.makeText(this, "DATA IS EMPTY", Toast.LENGTH_SHORT).show();
        }else {
            if(cursor.getCount() >0){
                cursor.moveToFirst();
                edtTen.setText(cursor.getString(1));
            }else {
                Toast.makeText(this, "DATA IS ZERO", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void InsertData() {
        String TEN = edtTen.getText().toString().trim();
        if(TEN.isEmpty()){
            Toast.makeText(this, "DATA IS NOT EMPTY", Toast.LENGTH_SHORT).show();
        }else {
            String sql_Insert = "INSERT INTO DANHSACH VALUES (NULL,'"+TEN+"')";
            DB.execSQL(sql_Insert);
            edtTen.getText().clear();
        }
    }

    private void MakeOrTable() {
        String sql = "CREATE TABLE IF NOT EXISTS DANHSACH " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENHS VARCHAR(100) )";
        DB.execSQL(sql);
    }

    private void CreateOrOpenDB() {
        DB = openOrCreateDatabase("qlhs.sqlite",MODE_PRIVATE,null);
    }

    private void Init() {
        btnMakeDB = findViewById(R.id.btnMakeDB);
        btnMakeTb = findViewById(R.id.btnMakeTb);
        btnPre = findViewById(R.id.btnPre);
        btnNext = findViewById(R.id.btnNext);
        btnInsert = findViewById(R.id.btnInsert);
        btnLoad = findViewById(R.id.btnLoad);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        edtTen = findViewById(R.id.edtTen);
    }
}