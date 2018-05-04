package com.aklni.islammahoud.aklnii.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Activities.OrderActivity;
import com.aklni.islammahoud.aklnii.Data.Sub_cat;
import com.aklni.islammahoud.aklnii.R;

import java.util.ArrayList;

/**
 * Created by islam mahoud on 1/28/2018.
 */

public class Mealitemadapter extends RecyclerView.Adapter<Mealitemadapter.ViewHolder> {
    private Context context;
    private ArrayList<Sub_cat> sub_cats;
    private int size,clientid,foodid;
    public Mealitemadapter(Context context, ArrayList<Sub_cat> sub_cats,int size,int clientid,int foodid)
    {
        this.context=context;
        this.size=size;
        this.sub_cats=sub_cats;
        this.clientid=clientid;
        this.foodid=foodid;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mealsitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemdesc.setVisibility(View.GONE);
        holder.itemname.setText(sub_cats.get(position).getItemName());
        holder.price.setText(sub_cats.get(position).getPrice()+" ج.م ");
        if(sub_cats.get(position).getItemDescription()!="null")
        {
            holder.itemdesc.setVisibility(View.VISIBLE);
            holder.itemdesc.setText("  ( "+sub_cats.get(position).getItemDescription()+" )  ");
        }
        if(sub_cats.get(position).getDiscount()!="null")
        {
            holder.offer.setVisibility(View.VISIBLE);
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(sub_cats.get(position).getDiscount());
        }
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("poaition",position);
                intent.putExtra("id",sub_cats.get(position).getId());
                intent.putExtra("foodid",foodid);
                intent.putExtra("clientid",clientid);
                intent.putExtra("name",sub_cats.get(position).getItemName());
                intent.putExtra("price",sub_cats.get(position).getPrice());
                intent.putExtra("FoodCategory",sub_cats.get(position).getFoodCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname,price,itemdesc,discount;
        RelativeLayout relativeLayout;
        Button favBtn;
        ImageView offer;
        public ViewHolder(View itemView) {
            super(itemView);
            itemname=itemView.findViewById(R.id.mealname);
            price=itemView.findViewById(R.id.price);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);
            itemdesc=itemView.findViewById(R.id.desc);
            favBtn=itemView.findViewById(R.id.fav);
            discount=itemView.findViewById(R.id.discount);
            offer=itemView.findViewById(R.id.offer);
        }
    }

}
