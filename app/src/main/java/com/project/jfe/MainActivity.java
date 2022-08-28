package com.project.jfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ImageView I;
    private ArrayList<user> userslist;
    private RecyclerView recyclerView;
    private recycler_adapter.RecyclerViewClickListener listener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        I=findViewById(R.id.imageView3);

        userslist = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        setUserInfo();
        setAdapter();

        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),profile.class));

            }
        });



        }

    private void setAdapter() {
        setOnClickListner();
        recycler_adapter adapter = new recycler_adapter(userslist,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListner() {
        listener = new recycler_adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int pos) {
              final  Intent i;
              switch(pos){
                  case 0:
                      i= new Intent(getApplicationContext(),beauty.class);
                      break;
                  case 1:
                      i= new Intent(getApplicationContext(),construction.class);
                      break;
                  case 2:
                      i= new Intent(getApplicationContext(),food.class);
                      break;
                  case 3:
                      i= new Intent(getApplicationContext(),fashion.class);
                      break;
                  case 4:
                      i= new Intent(getApplicationContext(),home.class);
                      break;


                  default:
                      i=new Intent(getApplicationContext(),category_list.class);
              }

                     //   Intent intent2 = new Intent(getApplicationContext(),category_list.class);
                        startActivity(i);


            }
        };

    }

    private void setUserInfo() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.recycler_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(new recycler_adapter(userslist,listener));
        userslist.add(new user("Beauty"));
        userslist.add(new user("Construction"));
        userslist.add(new user("Food"));
        userslist.add(new user("Fashion"));
        userslist.add(new user("Home"));
        userslist.add(new user("Small Business"));

    }
}
