package com.posapp.nutech.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.posapp.nutech.MainActivity;
import com.posapp.nutech.R;
import com.posapp.nutech.model.DataModel;

import java.util.ArrayList;
import java.util.List;



public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    private List<DataModel> mList = new ArrayList<>();
    private Context ctx;


    public  AdapterData (Context ctx, List<DataModel> mList)
    {
        this.ctx = ctx;
        this.mList = mList;
    }


    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist,parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(HolderData holder, int position) {
        DataModel dm = mList.get(position);
        holder.name.setText(dm.getName());
        holder.price.setText(dm.getPrice());
        holder.description.setText(dm.getDescription());
        holder.dm = dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class HolderData extends  RecyclerView.ViewHolder{
        TextView name, price, description;
        DataModel dm;
        public HolderData (View v)
        {
            super(v);

            name  = (TextView) v.findViewById(R.id.tvNama);
            price = (TextView) v.findViewById(R.id.tvHarga);
            description = (TextView) v.findViewById(R.id.tvDeskripsi);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx,MainActivity.class);
                    goInput.putExtra("id", dm.getId());
                    goInput.putExtra("name", dm.getName());
                    goInput.putExtra("price", dm.getPrice());
                    goInput.putExtra("description", dm.getDescription());

                    ctx.startActivity(goInput);
                }
            });
        }
    }
}
