package com.example.exercise;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.exercise.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    TextView textView;
    FirebaseAuth auth;
    FirebaseUser user;

    FirebaseDatabase db;
    DatabaseReference ref;

    String name, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menuItemId = item.getItemId();

                if (menuItemId == R.id.home){
                    replaceFragment(new HomeFragment());
                    return true;
                }
                else if (menuItemId == R.id.message){
                    replaceFragment(new MassageFragment());
                    return true;
                }
                else if (menuItemId == R.id.person){
                    replaceFragment(new ProfileFragment());
                    return true;
                }
                else if (menuItemId == R.id.calendar){
                    replaceFragment(new CalendarFragment());
                    return true;
                }
                return true;
            }
        });

        db = FirebaseDatabase.getInstance();

        ref = db.getReference("Rooms");





    }

    public void onClickLogout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void onClickBook(View view) {
        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");

        GlobalData globalData = GlobalData.getInstance();
        ArrayList<String> array = globalData.getGlobalArray();
        ArrayList<ArrayList<Integer>> array2 = globalData.getNestedGlobalArray();
        String lastElement = array.get(array.size() - 1);
        ArrayList<Integer> lastElement2 = array2.get(array.size() - 1);



        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userEmail = user != null ? user.getEmail() : null;


        if (userEmail != null) {
            String roomName = lastElement;
            DatabaseReference specificRoomRef = roomsRef.child(roomName);


            specificRoomRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("booked-by")) {
                        String bookedBy = dataSnapshot.child("booked-by").getValue(String.class);

                        if (!userEmail.equals(bookedBy)) {

                            Toast.makeText(MainActivity.this, "Room is already booked by another user.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "You already book it.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        specificRoomRef.child("time").setValue(lastElement2);
                        specificRoomRef.child("status").setValue("booked");
                        specificRoomRef.child("booked-by").setValue(userEmail);

                        Toast.makeText(MainActivity.this, "Successfully booked!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }


    public void onClickunBook(View view) {
        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");
        GlobalData globalData = GlobalData.getInstance();

        ArrayList<String> array = globalData.getGlobalArray();
        String lastElement = array.get(array.size() - 1);

        String roomName = lastElement;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String currentUserEmail = currentUser.getEmail();

            roomsRef.child(roomName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String bookedBy = dataSnapshot.child("booked-by").getValue(String.class);

                        if (currentUserEmail.equals(bookedBy)) {

                            roomsRef.child(roomName).child("status").setValue("available");
                            roomsRef.child(roomName).child("booked-by").setValue(null);
                            roomsRef.child(roomName).child("time").setValue(null);
                            Toast.makeText(MainActivity.this, "Room unbooked successfully.", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(MainActivity.this, "You can only unbook your own booked rooms.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}