package com.project.jfe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recycler_adapter extends RecyclerView.Adapter<recycler_adapter.MyViewHolder> {
   private ArrayList<user> userslist;
   private RecyclerViewClickListener listener;
   public recycler_adapter(ArrayList<user> userslist, RecyclerViewClickListener listener){
       this.userslist=userslist;
       this.listener=listener;
   }
    public class MyViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
       private TextView maintext;
       public MyViewHolder(final View view){
           super(view);
           maintext = view.findViewById(R.id.textView5);
           view.setOnClickListener(this);
       }
       @Override
        public void onClick(View view) {
           listener.onClick(view,getAdapterPosition());
       }
    }
    @NonNull
    @Override
    public recycler_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_itemsmain,parent,false);
        return new MyViewHolder(itemview);
    }
    @Override
    public void onBindViewHolder(@NonNull recycler_adapter.MyViewHolder holder, int position) {
        String name = userslist.get(position).getMainname();
        holder.maintext.setText(name);
    }
    @Override
    public int getItemCount() {
        return userslist.size();
    }
    public interface RecyclerViewClickListener{
       void onClick(View v,int pos);
    }
}
