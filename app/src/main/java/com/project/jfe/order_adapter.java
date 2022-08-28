package com.project.jfe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class order_adapter extends FirebaseRecyclerAdapter<job,order_adapter.myViewHolder>
{
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public order_adapter(@NonNull FirebaseRecyclerOptions<job> options) {
        super(options);
    }

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