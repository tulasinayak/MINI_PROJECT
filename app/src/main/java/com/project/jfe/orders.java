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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class orders extends AppCompatActivity {


   RecyclerView recyclerView;
   DatabaseReference reference;
   order_adapter O_adapter;

   FirebaseAuth fAuth;
   job j;
   ImageView I;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);
        I=findViewById(R.id.main_image);
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(orders.this,MainActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recyclerviewo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        String jid = firebaseUser.getUid().toString();

        FirebaseRecyclerOptions<job> option =
                new FirebaseRecyclerOptions.Builder<job>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("orders").child(jid), job.class)
                        .build();
        O_adapter = new order_adapter(option);
        recyclerView.setAdapter(O_adapter);

}


    @Override
    protected void onStart() {
        super.onStart();
        O_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        O_adapter.stopListening();
    }
    }