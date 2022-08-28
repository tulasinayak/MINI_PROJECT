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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class register extends AppCompatActivity {
    Button b3;
    EditText e1,e2,e3,e4,p;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    reg r;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        b3 = findViewById(R.id.button3);
        e1 = findViewById(R.id.editTextTextPersonName);
        e2 = findViewById(R.id.editTextTextEmailAddress);
        e3 = findViewById(R.id.editTextTextPassword2);
        e4 = findViewById(R.id.editTextTextPassword3);
        p=findViewById(R.id.editTextPhone);
        fAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new  Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        b3.setOnClickListener(view -> {

            String name = e1.getText().toString().trim();
            String phone = p.getText().toString().trim();
            String email =  e2.getText().toString().trim();
            String password= e3.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                e2.setError("Email is required.");
                return;
            }
            else{
                validateEmailAddress(e2);
            }

            if(TextUtils.isEmpty(password))
            {
                e3.setError("Password is required");
                return;
            }
            if(TextUtils.isEmpty(name))
            {
                e1.setError("Name is required");
                return;
            }
            if(TextUtils.isEmpty(phone))
            {
                p.setError("Phone is required");
                return;
            }
            if(phone.length()<10&&phone.length()>10)
            {
                p.setError("Phone no. must be =10 characters");
                return;
            }

            if(password.length()<6)
            {
                e3.setError("Password must be >=6 characters");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                 //process of registering user is called task
                    if(task.isSuccessful()){

                        updata();
                        Toast.makeText(register.this,"User Created",Toast.LENGTH_SHORT).show();
                        startActivity(new  Intent(getApplicationContext(),MainActivity.class));

                    }
                    else{
                        Toast.makeText(register.this,"Error!! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }


            });



        });

    }

    private void updata() {

        r=new reg();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user");
        String uname = e1.getText().toString();
        String uemail = e2.getText().toString();
        String upas=e3.getText().toString();
        String Phone = p.getText().toString();
        r.setPhone(Phone);
        r.setUname(uname);
        r.setEpas(upas);
        r.setUemail(uemail);

        databaseReference.child(Phone).setValue(r);
    }


    private boolean validateEmailAddress(EditText email){
        String emailInput = e2.getText().toString();
        if(!emailInput.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            Toast.makeText(register.this,"Email Validates Successfully",Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(register.this,"Invalid Email address",Toast.LENGTH_SHORT).show();
            return false;
        }
    }



}