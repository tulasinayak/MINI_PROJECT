package com.project.jfe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class category_list extends AppCompatActivity {

    RecyclerView recyclerView;
    RV_adapter rv_adapter;
    ImageView I;


    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        recyclerView = findViewById(R.id.RV);


            recyclerView_main2();
        I=findViewById(R.id.imageView17);
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(category_list.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }


    private void recyclerView_main2() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        FirebaseRecyclerOptions<job> option =
                new FirebaseRecyclerOptions.Builder<job>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("my_job").child("Small Business"), job.class)
                        .build();
        rv_adapter = new RV_adapter(option,category_list.this);
        recyclerView.setAdapter(rv_adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        rv_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        rv_adapter.stopListening();
    }
}
