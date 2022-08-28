package com.project.jfe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class login extends AppCompatActivity {
    Button b1,b2;
    EditText t2,t3;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1= findViewById(R.id.button);
        b2= findViewById(R.id.button2);
        t2= findViewById(R.id.editTextTextEmailAddress2);
        t3= findViewById(R.id.editTextTextPassword);
        fAuth= FirebaseAuth.getInstance();
        b1.setOnClickListener(view -> {
            String email =  t2.getText().toString().trim();
            String password= t3.getText().toString().trim();
            if(TextUtils.isEmpty(email)){
                t2.setError("Email is required.");
                return;
            }
             else{
                validateEmailAddress(t2);
            }
            if(TextUtils.isEmpty(password))
            {
                t3.setError("Password is required");
                return;
            }
            if(password.length()<6)
            {
                t3.setError("Password must be >=6 characters");
                return;
            }
            //authenticate the user
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //sent the verification email

                        Toast.makeText(login.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new  Intent(getApplicationContext(),MainActivity.class));
                    }else{
                        Toast.makeText(login.this,"Error!! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),login.class));
                    }
                }
            });
           // Intent i= new Intent(login.this,MainActivity.class);
          //  startActivity(i);
        });
        b2.setOnClickListener(view -> {
            Intent i= new Intent(login.this,register.class);
            startActivity(i);
        });

    }
    private boolean validateEmailAddress(EditText email){
        String emailInput = t2.getText().toString();
        if(!emailInput.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            //Toast.makeText(login.this,"Email Validates Successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(login.this,"Invalid Email address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }


}