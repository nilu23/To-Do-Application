package com.example.fnilofar.firstandriodapplication;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.EGLExt;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    ArrayList<Items> items ;
    //ArrayAdapter<Items> itemsAdapter;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    private final int EDIT_REQUEST_CODE = 5;
    private final int ADD_REQUEST_CODE = 1;

    // Get singleton instance of database
    TodoDatabase databaseHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);

        databaseHelper = TodoDatabase.getsInstance(this);

        //databaseHelper.deleteAllItems();
        readItemsFromDB();

        itemsAdapter = new ItemsAdapter(this, items);
        //itemsAdapter = new ArrayAdapter<Items>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {

        databaseHelper = TodoDatabase.getsInstance(this);

        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        databaseHelper.deleteItem(items.get(pos));
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();

                        return true;
                    }
                }
        );


        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent launchEditScreen = new Intent(MainActivity.this, EditActivity.class);
                        launchEditScreen.putExtra("KEY_POS", position);
                        launchEditScreen.putExtra("KEY_EDITITEM", items.get(position));
                        startActivityForResult(launchEditScreen, EDIT_REQUEST_CODE);
                    }
                }

        );
    }
    public void onAddItem(View v) {

        Intent launchAddScreen = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(launchAddScreen, ADD_REQUEST_CODE);

    }


    private  void readItemsFromDB() {

        databaseHelper = TodoDatabase.getsInstance(this);

        // Get all items from database
        items = (ArrayList) databaseHelper.getAllItems();

    }

    private void addItemToDB(Items item) {

       databaseHelper = TodoDatabase.getsInstance(this);

        // Add an item to database
        databaseHelper.addItem(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        databaseHelper = TodoDatabase.getsInstance(this);

        if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE){
            Items savedItem = (Items) data.getSerializableExtra("KEY_EDITITEM");
            int pos = data.getIntExtra("KEY_POS", 0);
            databaseHelper.updateItem(savedItem, items.get(pos));
            items.remove(pos);
            items.add(pos, savedItem);
            itemsAdapter.notifyDataSetChanged();
        }

        if(resultCode == RESULT_OK && requestCode == ADD_REQUEST_CODE){
            Items savedItem = (Items) data.getSerializableExtra("KEY_ADDITEM");
            databaseHelper.addItem(savedItem);
            items.add(savedItem);
            itemsAdapter.notifyDataSetChanged();
        }
    }

}
