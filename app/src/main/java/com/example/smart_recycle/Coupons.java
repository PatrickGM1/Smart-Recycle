package com.example.smart_recycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Coupons extends AppCompatActivity {
    Button succes, returnbtn, coupon;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private DatabaseReference mDatabase;
    private final int ticketPrice = 1000, couponprice=10000;
    private int currentGarbagePoints = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        succes=(Button)findViewById(R.id.bilet);
        returnbtn=(Button)findViewById(R.id.returnbutton2);
        coupon=(Button)findViewById(R.id.Cupon);
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
                        if(currentGarbagePoints>1000) {
                            mDatabase.child("Users").child(userID).child("garbagePoints").setValue(currentGarbagePoints - ticketPrice);
                            Toast.makeText(Coupons.this, "Ticket successfully bought", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Coupons.this, "Insufficient points", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Users").child(userID).child("garbagePoints").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentGarbagePoints = snapshot.getValue(int.class);
                        if(currentGarbagePoints>10000) {
                            mDatabase.child("Users").child(userID).child("garbagePoints").setValue(currentGarbagePoints - couponprice);
                            Toast.makeText(Coupons.this, "Coupon successfully bought", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(Coupons.this, "Insufficient points", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Coupons.this, MapsActivity.class));
            }
        });
    }
}