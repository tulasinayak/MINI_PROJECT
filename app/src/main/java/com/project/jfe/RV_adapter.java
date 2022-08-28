package com.project.jfe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

    public class RV_adapter extends FirebaseRecyclerAdapter<job,RV_adapter.myViewHolder> {
        Context context;
        public RV_adapter(@NonNull FirebaseRecyclerOptions<job> options,Context context) {
            super(options);
            this.context = context;
        }
        FirebaseAuth fAuth;
        DatabaseReference databaseReference;
        private StorageReference storageReference;
        job j;
        private static int aCount = 0;




    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull job model) {

          holder.name.setText(model.getJname());
          holder.cost.setText(model.getJprice());
          holder.dec.setText(model.getJdec());
          holder.uname.setText(model.getName());
          holder.uno.setText(model.getPhoneno());

         Glide.with(holder.img.getContext())
                .load(model.getJurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.google.firebase.database.collection.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Do You Want To book?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                aCount=aCount+1;
                                Toast.makeText(context, "Booked", Toast.LENGTH_SHORT).show();
                                fAuth = FirebaseAuth.getInstance();
                                FirebaseUser firebaseUser = fAuth.getCurrentUser();
                                j = new job();
                                String jid = firebaseUser.getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
                                String jname2 = model.getJname();
                                String jdec2 = model.getJdec();
                                String jpric2 = model.getJprice();
                                String juname= model.getName();
                                String jphone=model.getPhoneno();
                                String jurl = model.getJurl();
                                String count=String.valueOf(aCount);
                                j.setJname(jname2);
                                j.setJdec(jdec2);
                                j.setJprice(jpric2);
                                j.setJphone(jid);
                                j.setJurl(jurl);
                                j.setName(juname);
                                j.setPhoneno(jphone);
                                databaseReference.child(jid).child(count).setValue(j);
                                Intent intent = new Intent(context,orders.class);
                                context.startActivity(intent);

                            }


                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.setCancelable(true);
                    }
                }).show();
            }


        });


      }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main2,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,cost, dec, uname,uno;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img= itemView.findViewById(R.id.imageView8);
            name= itemView.findViewById(R.id.textname);
            cost= itemView.findViewById(R.id.textcost);
            dec=itemView.findViewById(R.id.textcost2);
            uname=itemView.findViewById(R.id.textcost3);
            uno = itemView.findViewById(R.id.textcost4);

        }


    }

}
