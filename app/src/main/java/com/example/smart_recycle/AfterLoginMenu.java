package com.example.smart_recycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AfterLoginMenu extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    Button logout, leaderboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_menu);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference =FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        logout=(Button) findViewById(R.id.Logout);
        final TextView showname = (TextView) findViewById(R.id.welcome);
        final TextView showemail = (TextView) findViewById(R.id.showemail);
        final TextView showgarbage = (TextView) findViewById(R.id.garbagepointcounter);
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile!=null){
                    String fullname  = userprofile.name;
                    String email = userprofile.email;
                    int garbagecount = userprofile.garbagePoints;
                    showname.setText("Welcome, "+fullname+" !");
                    showemail.setText("Your email is: \n"+ email);
                    showgarbage.setText("You have "+garbagecount+" recycle points");

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AfterLoginMenu.this,MainActivity.class));
                finish();
            }
        });
    }
}