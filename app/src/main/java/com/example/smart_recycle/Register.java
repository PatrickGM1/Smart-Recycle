package com.example.smart_recycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.abt.FirebaseABTesting;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private TextView banner, registerUser;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        banner=(TextView) findViewById(R.id.Banner);

        banner.setOnClickListener(this);
        registerUser = (Button) findViewById(R.id.button3);
        registerUser.setOnClickListener(this);
        editTextFullName = (EditText) findViewById(R.id.Name);
        editTextAge = (EditText) findViewById(R.id.Age);
        editTextEmail = (EditText) findViewById(R.id.Email);
        editTextPassword = (EditText) findViewById(R.id.Password);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button3:
                registerUser1();
                break;

        }
    }

    private void registerUser1(){
        String email = editTextEmail.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextFullName.getText().toString().trim();

        if(name.isEmpty()){
            editTextFullName.setError("Full name is required");
            editTextFullName.requestFocus();
            return;
        }
        if(age.isEmpty()){
            editTextAge.setError("Age is required");
            editTextAge.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Provide an email");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
        }
        if(password.isEmpty()){
            editTextPassword.setError("Provide a password");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Password is too short");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(name, age, email,  0);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                            setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Register.this, "User has been registered", Toast.LENGTH_LONG).show();
                                editTextEmail.getText().clear();
                                editTextAge.getText().clear();
                                editTextPassword.getText().clear();
                                editTextFullName.getText().clear();
                                startActivity(new Intent(Register.this, Login.class));
                            }else{
                                Toast.makeText(Register.this, "Failed to register", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(Register.this, "Failed to register", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}