package com.project.jfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class home extends AppCompatActivity {
    RecyclerView recyclerView;
    RV_adapter rv_adapter;
    ImageView I;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.RVho);
        recyclerView_home();
    }

    private void recyclerView_home() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        FirebaseRecyclerOptions<job> option =
                new FirebaseRecyclerOptions.Builder<job>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("my_job").child("Home"), job.class)
                        .build();
        rv_adapter = new RV_adapter(option,home.this);
        recyclerView.setAdapter(rv_adapter);
        I=findViewById(R.id.imageView13);
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this,MainActivity.class);
                startActivity(intent);
            }
        });
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