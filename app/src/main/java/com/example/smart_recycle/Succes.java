package com.example.smart_recycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Succes extends AppCompatActivity {
    Button succes;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private DatabaseReference mDatabase;
    private final int DEFAULT_POINTS = 100;
    private int currentGarbagePoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes);
        succes=(Button)findViewById(R.id.Succes);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        succes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Users").child(userID).child("garbagePoints").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentGarbagePoints = snapshot.getValue(int.class);
                        mDatabase.child("Users").child(userID).child("garbagePoints").setValue(currentGarbagePoints + DEFAULT_POINTS);
                        startActivity(new Intent(Succes.this, MapsActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}