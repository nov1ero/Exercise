package com.example.exercise;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends  Fragment{
    TextView textView, textView2, textView3;
    FirebaseAuth auth;
    FirebaseUser user;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

//        auth = FirebaseAuth.getInstance();
//
//        textView = textView.findViewById(R.id.textView3);
//        user = auth.getCurrentUser();
//        textView.setText(user.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");

        View v =  inflater.inflate(R.layout.fragment_profile, container, false);
        textView = v.findViewById(R.id.textViewName);
        textView2 = v.findViewById(R.id.textViewPhone);
        textView3 = v.findViewById(R.id.textViewUsername);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and whenever data at this location is updated

                // Iterate through the users
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    String userName = userSnapshot.child("fullName").getValue(String.class);
                    String userEmail = userSnapshot.child("userName").getValue(String.class);
                    String userPhone = userSnapshot.child("phone").getValue(String.class);
                    textView.setText(userName);
                    textView2.setText("Phone: "+userPhone);
                    textView3.setText("Username: "+userEmail);

                    }

                }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                // ...
            }
        });

        return v;

    }

//    public void onClickLogout(View view) {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(), Login.class));
//        finish();

}