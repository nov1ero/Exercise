package com.example.exercise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {
    TextView textView,textView2;
    FirebaseAuth auth;
    FirebaseUser user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendarView = rootView.findViewById(R.id.calendarView);
        textView = rootView.findViewById(R.id.infoCal);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String currentUserName = user != null ? user.getEmail() : null;
        DatabaseReference roomsRef = FirebaseDatabase.getInstance().getReference("Rooms");
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;


            roomsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    StringBuilder bookedRoomsStringBuilder = new StringBuilder();
                    String status = "Free!";
                    for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {


                        String roomName = roomSnapshot.child("name").getValue(String.class);
                        if (roomSnapshot.hasChild("booked-by") && currentUserName != null && currentUserName.equals(roomSnapshot.child("booked-by").getValue(String.class))) {


                            DataSnapshot timeSnapshot = roomSnapshot.child("time");
                            int yearValue = timeSnapshot.child("0").getValue(Integer.class);
                            int monthValue = timeSnapshot.child("1").getValue(Integer.class);
                            int dayValue = timeSnapshot.child("2").getValue(Integer.class);

                            if (year == yearValue && month == monthValue && dayOfMonth == dayValue){


                                textView.setText("Booked!");
                                break;
                            }
                            else{
                                textView.setText(status);
                            }

                        }

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });

        });

        return rootView;
    }


}