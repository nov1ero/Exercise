package com.example.exercise;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    TextView tx,tx2;
    FirebaseAuth auth;
    FirebaseUser user;



    String allrooms = "";


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        View v =  inflater.inflate(R.layout.fragment_home, container, false);
        tx = v.findViewById(R.id.dashboard);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated

                // Iterate through the users
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String userName = userSnapshot.child("fullName").getValue(String.class);
                    String userEmail = userSnapshot.child("userName").getValue(String.class);
                    String userPhone = userSnapshot.child("phone").getValue(String.class);

                    if (userEmail.equals(user.getEmail())){
                        tx.setText("Welcome "+userName+"!");
                        break;
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                // ...
            }
        });

        Map<Integer, String> day = new HashMap<>();

        day.put(1,"Sunday");
        day.put(2,"Monday");
        day.put(3,"Tuesday");
        day.put(4,"Wednesday");
        day.put(5,"Thursday");
        day.put(6,"Friday");
        day.put(7,"Saturday");


        TextView textViewBookedRooms = v.findViewById(R.id.room);


        String currentUserName = user != null ? user.getEmail() : null;

        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");

        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder bookedRoomsStringBuilder = new StringBuilder();
                int pr = 0;
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {


                    String roomName = roomSnapshot.child("name").getValue(String.class);
                    if (roomSnapshot.hasChild("booked-by") && currentUserName != null && currentUserName.equals(roomSnapshot.child("booked-by").getValue(String.class))) {


                        DataSnapshot timeSnapshot = roomSnapshot.child("time");
                        int timeValue = timeSnapshot.child("3").getValue(Integer.class);
                        int hourInt = (timeSnapshot.child("4").getValue(Integer.class));
                        int minuteInt = (timeSnapshot.child("5").getValue(Integer.class));
                        String hour = String.valueOf(hourInt);
                        String minute = String.valueOf(minuteInt);

                        String hour2 = String.valueOf((hourInt+2)%24);

                        if (timeValue != pr){
                            bookedRoomsStringBuilder.append("\n").append(day.get(timeValue)).append(":").append("\n");
                            bookedRoomsStringBuilder.append(roomName+" ("+hour+":"+minute+" - "+hour2+":"+minute+")").append("\n");
                            pr = timeValue;
                        }
                        else{

                            bookedRoomsStringBuilder.append(roomName+" ("+hour+":"+minute+" - "+hour2+":"+minute+")").append("\n");
                        }




                    }

                }



                textViewBookedRooms.setText(bookedRoomsStringBuilder.toString().trim());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


        return v;
    }


}