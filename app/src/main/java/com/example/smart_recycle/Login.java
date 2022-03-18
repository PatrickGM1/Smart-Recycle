package com.example.smart_recycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button signIn, forgotpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=(TextView)findViewById(R.id.Login);
        register.setOnClickListener(this);
        signIn = (Button) findViewById(R.id.login2);
        signIn.setOnClickListener(this);
        editTextEmail=(EditText) findViewById(R.id.email2);
        editTextPassword=(EditText) findViewById(R.id.password2);
        forgotpass =(Button)findViewById(R.id.button4);
        forgotpass.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getBaseContext(),MapsActivity.class));
            finish();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Login:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.login2:
                userLogin();
                break;
            case R.id.button4:
                startActivity(new Intent(Login.this, PassFG.class));
        }

    }
    private void userLogin(){
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
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
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    editTextPassword.getText().clear();
                    editTextEmail.getText().clear();
                    startActivity(new Intent(Login.this, MapsActivity.class));
                    Toast.makeText(Login.this, "Successfully logged in", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(Login.this, "Incorrect password or email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}