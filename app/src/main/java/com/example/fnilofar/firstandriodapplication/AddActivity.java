package com.example.fnilofar.firstandriodapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Random;


public class AddActivity extends FragmentActivity {

    EditText newEditText ;
    Button saveBtn ;

    EditText text_date;
    private DatePicker date_picker;
    private Button button;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setCurrentDate();
        addButtonListener();

    }

    public void onAddItem(View v) {

        newEditText = (EditText) findViewById(R.id.newItemText);
        text_date = (EditText) findViewById(R.id.date);

        Items newItem = new Items();
        Random random =  new Random();
        Intent homeScreen = new Intent();
        newItem.id = random.nextInt(100);

        newItem.text = newEditText.getText().toString();
        newItem.dueDate = text_date.getText().toString();

        homeScreen.putExtra("KEY_ADDITEM", newItem);
        setResult(RESULT_OK, homeScreen);
        finish();
    }


    // display current date both on the text view and the Date Picker when the application starts.
    public void setCurrentDate() {

        text_date = (EditText) findViewById(R.id.date);
       // date_picker = (DatePicker) findViewById(R.id.datePicker);

        final Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        text_date.setText(new StringBuilder()
                // Month is 0 based, so you have to add 1
                .append(month + 1).append("-")
                .append(day).append("-")
                .append(year).append(" "));

        // set current date into Date Picker
       // date_picker.init(year, month, day, null);

    }

    public void addButtonListener() {

        button = (Button) findViewById(R.id.changeDate);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(DATE_DIALOG_ID);

            }

        });

    }
    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into Text View
            text_date.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year).append(" "));

            // set selected date into Date Picker
            //date_picker.init(year, month, day, null);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
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
