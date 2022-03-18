package com.example.smart_recycle;

import static com.google.android.gms.tasks.Tasks.await;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Top_garbage_collectors extends AppCompatActivity {
    Button returnbtn;
    private final int MAX_TOP_USERS = 10;
    private DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference();
    private Query query = dbUsers.child("Users").orderByChild("garbagePoints").limitToFirst(MAX_TOP_USERS);
    public TextView lista;
    String listastring="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_garbage_collectors);
        lista=(TextView)findViewById(R.id.listatop);
        lista.setText(listastring);
        returnbtn=(Button)findViewById(R.id.returnbutton2);
        DisplayTopUsers();
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Top_garbage_collectors.this, MapsActivity.class));
            }
        });
    }

    private void DisplayTopUsers() {

        List<User> topUsers = new ArrayList<User>();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long postsCount = dataSnapshot.getChildrenCount();
                if(postsCount > MAX_TOP_USERS) {
                    postsCount = MAX_TOP_USERS;
                }
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println(ds.getValue(User.class).name);
                    User user = ds.getValue(User.class);
                    listastring = postsCount + ". " + user.name + ": " + user.garbagePoints + "\n" + listastring;
                    postsCount--;
                    lista.setText(listastring);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        query.addListenerForSingleValueEvent(eventListener);
    }
}