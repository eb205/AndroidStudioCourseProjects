package com.example.mysqlapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase readDatebase;
    private SQLiteDatabase writeDatabase;

    private EditText editTextId;
    private EditText editTextFirstName;
    private EditText editTextLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.editTextId = findViewById(R.id.editTextWorkerID);
        this.editTextFirstName = findViewById(R.id.editTextWorkerFirstName);
        this.editTextLastName = findViewById(R.id.editTextWorkerLastName);

        this.myDatabaseHelper = new MyDatabaseHelper(this);
        this.readDatebase = this.myDatabaseHelper.getReadableDatabase();
        this.writeDatabase = this.myDatabaseHelper.getWritableDatabase();
    }

    public void addWorker(View view) {
        String fName=this.editTextFirstName.getText().toString();
        String lName=this.editTextLastName.getText().toString();

        ContentValues values = new ContentValues();
        values.put(MyDatabaseContract .TableWorkers.COLUMN_NAME_FIRST_NAME, fName);
        values.put(MyDatabaseContract .TableWorkers.COLUMN_NAME_LAST_NAME, lName);


        long newRowId;
        //  The first argument for insert() is simply the table name.
        // The second argument provides the name of a column in which the framework can insert NULL
        // in the event that the ContentValues is empty (if you instead set this to "null", then the framework will not insert a row when there are no values).

        newRowId = writeDatabase.insert(
                MyDatabaseContract.TableWorkers.TABLE_NAME,
                null,
                values);
    }

    public void readData(View view) {
        // Define a projection that specifies which columns from the database you will actually use after
        // this query.
        String[] projection = {
                MyDatabaseContract .TableWorkers.COLUMN_NAME_WORKER_ID,
                MyDatabaseContract .TableWorkers.COLUMN_NAME_FIRST_NAME,
                MyDatabaseContract .TableWorkers.COLUMN_NAME_LAST_NAME
        };
        // How you want the results sorted in the resulting Curso
        String sortOrder =MyDatabaseContract.TableWorkers.COLUMN_NAME_LAST_NAME + " DESC";


        
        Cursor c = readDatebase.query(
                MyDatabaseContract.TableWorkers.TABLE_NAME,  // The table to query
                projection, // The columns to return
                null, // The columns for the WHERE clause, all if null
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                sortOrder); // The sort order
        String s ="";
        c.moveToFirst();

        for(int i=0;i<c.getCount(); i++)  { s ="";
            s=s+c.getInt(0)+", " + c.getString(1)+", "+c.getString(2);
            Log.i("sss", s);  c.moveToNext();     }


    }

}