package com.aklni.islammahoud.aklnii.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Data.Cart;
import com.aklni.islammahoud.aklnii.Database.DbHelper;
import com.aklni.islammahoud.aklnii.Fragments.CartFragment;
import com.aklni.islammahoud.aklnii.R;

import java.util.ArrayList;

/**
 * Created by islam mahoud on 2/1/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<Cart> carts;
    private Context context;
    private DbHelper dbHelper;
    private CartFragment cartFragment;
    private float allprice;

    public CartAdapter(Context context,ArrayList<Cart> carts)
    {
        this.context=context;
        this.carts=carts;
        this.dbHelper=new DbHelper(context);
        this.cartFragment=new CartFragment();
        this.allprice=0;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemname.setText(carts.get(position).getFoodcategory()+"\n"+carts.get(position).getNum()+" " + carts.get(position).getName());
        holder.price.setText(carts.get(position).getPrice()+ " ج.م ");
        holder.comments.setText(carts.get(position).getExtra());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
                dbHelper.deleteContact(carts.get(position));
                carts=dbHelper.getallcart();
                dbHelper.close();
                refreshEvents(carts);

            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname,price,comments;
        Button cancel;

        public ViewHolder(View itemView) {
            super(itemView);
            itemname=itemView.findViewById(R.id.itemname);
            price=itemView.findViewById(R.id.itemprice);
            comments=itemView.findViewById(R.id.comments);
            cancel=itemView.findViewById(R.id.deleteorder);
        }
    }
    public void refreshEvents(ArrayList<Cart> events) {
        this.carts=events;
        new CartAdapter(context,events);
        notifyDataSetChanged();

    }
    public Float setpricetext()
    {
        // carts=new ArrayList<>();
        for (int i=0;i<carts.size();i++)
        {
            allprice=allprice+ Float.parseFloat(carts.get(i).getPrice());
        }
       return allprice;
    }
}
