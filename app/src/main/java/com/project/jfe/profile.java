package com.project.jfe;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class profile extends AppCompatActivity {
    Button b;

    TextView T;


    private ArrayList<user> prolist;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    Uri uri;
    FirebaseAuth fAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    ImageView I;



    private RecyclerView RV2;
    private recycler_adapter.RecyclerViewClickListener listener2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        b=findViewById(R.id.button4);


        prolist = new ArrayList<>();
        RV2 = findViewById(R.id.recyclerview2);

        setUserInfo2();
        setAdapter2();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });
        I=findViewById(R.id.imageView16);
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }




    private void setAdapter2() {
        setOnClickListner();
        recycler_adapter adapter = new recycler_adapter(prolist,listener2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RV2.setLayoutManager(layoutManager);
        RV2.setItemAnimator(new DefaultItemAnimator());
        RV2.setAdapter(adapter);
    }

    private void setOnClickListner() {
        listener2 = new recycler_adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
                final Intent intent;
                switch (pos){
                    case 0:
                       intent = new Intent(getApplicationContext(),my_job.class);
                       break;
                    case 1:
                        intent = new Intent(getApplicationContext(),orders.class);
                        break;
                    default:
                        intent = new Intent(getApplicationContext(),about.class);
                        break;

                }

               // intent.putExtra("mainname",prolist.get(pos).getMainname());
              startActivity(intent);
            }
        };
    }

    private void setUserInfo2() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        RV2.addItemDecoration(dividerItemDecoration);
        RV2.setAdapter(new recycler_adapter(prolist,listener2));
        prolist.add(new user("MY JOB"));
        prolist.add(new user("ORDERS"));
        prolist.add(new user("ABOUT US"));

    }



}
