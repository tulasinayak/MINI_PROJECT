package com.project.jfe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class my_job extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText jname, jdescription, jprice,usern,userphone;
    Spinner s;
    Button submit;
    ImageView I1, I2, main,I;

    DatabaseReference databaseReference;
    Uri iuri;
    job j;
    String text;
    FirebaseAuth fAuth;
    String IURL;
    reg r;


    private  FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_job);
        storage=FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        jname = findViewById(R.id.jname);
        jdescription = findViewById(R.id.jdesc);
        jprice = findViewById(R.id.jprice);
        submit = findViewById(R.id.button5);
        I1 = findViewById(R.id.imageView4);
        main = findViewById(R.id.imageView6);
        usern=findViewById(R.id.JUNAME);
        userphone=findViewById(R.id.JPHONE);
        fAuth = FirebaseAuth.getInstance();
        s = findViewById(R.id.planets_spinner);
        s.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Beauty");
        categories.add("Construction");
        categories.add("Food");
        categories.add("Fashion");
        categories.add("Home");
        categories.add("Small Business");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);
        DataSnapshot dataSnapshot;
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        I=findViewById(R.id.imageView15);
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(my_job.this,MainActivity.class);
                startActivity(intent);
            }
        });

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 11);



            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                j = new job();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("my_job");
                String jname2 = jname.getText().toString();
                String jdec2 = jdescription.getText().toString();
                String jpric2 = jprice.getText().toString();
                String jid = firebaseUser.getUid();
                String juname= usern.getText().toString();
                String jphone=userphone.getText().toString();
                String jurl = IURL;

                j.setJname(jname2);
                j.setJdec(jdec2);
                j.setJprice(jpric2);
                j.setJcat(text);
                j.setJphone(jid);
                j.setJurl(IURL);
                j.setName(juname);
                j.setPhoneno(jphone);
                databaseReference.child(text).child(jid).setValue(j);
                Toast.makeText(my_job.this, "Submitted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(my_job.this, profile.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        text = s.getSelectedItem().toString();
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==11 && resultCode == RESULT_OK && data != null)
        {

                iuri = data.getData();
                I1.setImageURI(iuri);
                I1.setVisibility(View.VISIBLE);
                uploadImage();


        }
    }

    private void uploadImage() {
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        String ID = firebaseUser.getUid();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/"+randomKey).child(ID);
        riversRef.putFile(iuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Snackbar.make(findViewById(R.id.imageView4),"Image uploaded",Snackbar.LENGTH_LONG).show();
                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        IURL = task.getResult().toString();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"Failed to upload",Toast.LENGTH_LONG).show();
            }
        });
    }

}
