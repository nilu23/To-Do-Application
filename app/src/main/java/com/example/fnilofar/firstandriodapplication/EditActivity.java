package com.example.fnilofar.firstandriodapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;


public class EditActivity extends ActionBarActivity {

    EditText editText ;
    Button saveBtn ;
    int pos;
    Items editItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveBtn = (Button)findViewById(R.id.savebtn);

        Intent intent =  getIntent();
        if  (null != intent){
            editItem = (Items) intent.getSerializableExtra("KEY_EDITITEM");
            pos = intent.getIntExtra("KEY_POS", 0);
            editText = (EditText) findViewById(R.id.editText);
            editText.setText(editItem.text);
            editText.setSelection(editText.getText().length());
        }
    }

    public void onSaveItem(View v) {

        Intent homeScreen = new Intent();
        editItem.text = editText.getText().toString();
        homeScreen.putExtra("KEY_EDITITEM", editItem);
        homeScreen.putExtra("KEY_POS", pos);
        setResult(RESULT_OK,homeScreen);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);

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
}
