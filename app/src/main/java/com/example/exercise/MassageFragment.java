package com.example.exercise;

import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MassageFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_massage, container, false);
        final CardView cardView1 = rootView.findViewById(R.id.cardView1);
        final CardView cardView2 = rootView.findViewById(R.id.cardView2);
        final CardView cardView3 = rootView.findViewById(R.id.cardView3);
        final CardView cardView4 = rootView.findViewById(R.id.cardView4);
        final CardView cardView5 = rootView.findViewById(R.id.cardView5);
        final CardView cardView6 = rootView.findViewById(R.id.cardView6);
        GlobalData globalData = GlobalData.getInstance();

        ArrayList<String> array = globalData.getGlobalArray();
        Map<String, String> dict = new HashMap<>();

        // Add room statuses to the map
        dict.put("Room 1", "Block A1, floor I.");
        dict.put("Room 2", "Block A2, floor II.");
        dict.put("Room 3", "Block A2, floor II.");
        dict.put("Room 4", "Block B1, floor I.");
        dict.put("Room 5", "Block B2, floor I.");
        dict.put("Room 6", "Block B2, floor II.");






        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = cardView1.findViewById(R.id.room1);
                String name = tx.getText().toString();
                String loc = dict.get(name);
                showPopupWindow("Name: " + name+"\nLocation: " + loc);

                array.add("room1");
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = cardView2.findViewById(R.id.room2);
                String name = tx.getText().toString();
                String loc = dict.get(name);
                showPopupWindow("Name: " + name+"\nLocation: " + loc);
                array.add("room2");
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = cardView3.findViewById(R.id.room3);
                String name = tx.getText().toString();
                String loc = dict.get(name);
                showPopupWindow("Name: " + name+"\nLocation: " + loc);
                array.add("room3");
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = cardView4.findViewById(R.id.room4);
                String name = tx.getText().toString();
                String loc = dict.get(name);
                showPopupWindow("Name: " + name+"\nLocation: " + loc);
                array.add("room4");
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = cardView5.findViewById(R.id.room5);
                String name = tx.getText().toString();
                String loc = dict.get(name);
                showPopupWindow("Name: " + name+"\nLocation: " + loc);
                array.add("room5");
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tx = cardView6.findViewById(R.id.room6);
                String name = tx.getText().toString();
                String loc = dict.get(name);
                showPopupWindow("Name: " + name+"\nLocation: " + loc);
                array.add("room6");
            }
        });


        return rootView;
    }
    private void showPopupWindow(String content) {

        // Inflate the pop-up layout
        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_layout, null);



        // Create a Dialog and set its content view
        final Dialog popupDialog = new Dialog(requireContext());
        popupDialog.setContentView(popupView);

        // Customize the pop-up content
        TextView popupTextView = popupView.findViewById(R.id.popupTextView);

        popupTextView.setText(content);


        CheckBox checkBox = popupView.findViewById(R.id.checkBox);

        // Set onClickListener for CheckBox
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    // Access the DatePicker and TimePicker after the checkbox is checked
                    DatePicker datePicker = popupDialog.findViewById(R.id.datePicker);
                    TimePicker timePicker = popupDialog.findViewById(R.id.timePicker);

                    // Get selected date
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);

                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);


                    // Get selected time
                    int hour = timePicker.getHour();
                    int minute = timePicker.getMinute();

                    ArrayList<Integer> dateTimeList = new ArrayList<>();
                    dateTimeList.add(year);
                    dateTimeList.add(month);
                    dateTimeList.add(day);
                    dateTimeList.add(dayOfWeek);
                    dateTimeList.add(hour);
                    dateTimeList.add(minute);

                    GlobalData globalData = GlobalData.getInstance();
                    ArrayList<ArrayList<Integer>> array2 = globalData.getNestedGlobalArray();
                    array2.add(dateTimeList);
                }
            }
        });

        popupDialog.show();
    }
//    private void showPopupWindow2(String content) {
//        Map<String, String> dict = new HashMap<>();
//
//        // Add room statuses to the map
//        dict.put("Room 1", "Block A1, floor I.");
//        dict.put("Room 2", "Block A2, floor II.");
//        dict.put("Room 3", "Block A2, floor II.");
//        dict.put("Room 4", "Block B1, floor I.");
//        dict.put("Room 5", "Block B2, floor I.");
//        dict.put("Room 6", "Block B2, floor II.");
//        // Inflate the pop-up layout
//        View popupView = LayoutInflater.from(requireContext()).inflate(R.layout.popup_layout, null);
//
//
//        // Create a Dialog and set its content view
//        final Dialog popupDialog = new Dialog(requireContext());
//        popupDialog.setContentView(popupView);
//
//        TextView popupTextView2 = popupView.findViewById(R.id.popupTextView2);
//        String roomName = content.substring("Name: ".length()).trim();
//        String loc = dict.get(roomName);
//
//
//        popupTextView2.setText(loc);
//        popupDialog.show();
//    }
}