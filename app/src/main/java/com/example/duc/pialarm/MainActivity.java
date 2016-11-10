package com.example.duc.pialarm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.VoiceInteractor;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        int hour = -1;
        int minute = -1;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        int day = -1;
        int month = -1;
        int year = -1;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public int getDay() {
            return day;
        }

        public int getMonth() {
            return month;
        }

        public int getYear() {
            return year;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editLink = (EditText) findViewById(R.id.editLink);
        CheckBox chkBox = (CheckBox) findViewById(R.id.chkBox);
        Button btnZeit = (Button) findViewById(R.id.btnZeit);
        Button btnDatum = (Button) findViewById(R.id.btnDatum);
        Button btnFinish = (Button) findViewById(R.id.btnFinish);
        Button btnStop = (Button) findViewById(R.id.btnStop);
        int minute, hour, day, month;


        final DialogFragment newFragment = new TimePickerFragment();
        btnZeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        final DialogFragment newDateFragment = new DatePickerFragment();
        btnDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newDateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandRequest stop = new CommandRequest();
                stop.setCommand("stop");
                stop.setValue("");

                Ion.with(MainActivity.this)
                        .load("192.168.178.219")
                        .setJsonPojoBody(stop)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {

                            }
                        });

            }
        });

    }
}
