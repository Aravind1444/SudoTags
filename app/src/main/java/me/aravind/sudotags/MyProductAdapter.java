package me.aravind.sudotags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder> {

    Context context;

    ArrayList<MyProductData> list;

    public MyProductAdapter(Context context, ArrayList<MyProductData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.myproduct,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyProductData myproductdata = list.get(position);
        holder.theproductName.setText(myproductdata.getTheproductName());
        holder.theuniqueID.setText(myproductdata.getTheuniqueID());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView theproductName, theuniqueID;


        public MyViewHolder(@NonNull View myproductView) {
            super(myproductView);

            theproductName = myproductView.findViewById(R.id.theproductName);
            theuniqueID = myproductView.findViewById(R.id.theuniqueID);

        }

    }
}
