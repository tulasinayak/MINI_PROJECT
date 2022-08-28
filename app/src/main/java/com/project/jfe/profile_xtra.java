package com.project.jfe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Objects;

public class profile_xtra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_xtra);
        TextView protxt=findViewById(R.id.textView4);

        String mainname = "Category Name not set";

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            mainname=extras.getString("mainname");
        }
        protxt.setText(mainname);
    }
    }
