package com.aklni.islammahoud.aklnii.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Data.Categ;
import com.aklni.islammahoud.aklnii.Fragments.MealsitemFrag;
import com.aklni.islammahoud.aklnii.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by islam mahoud on 1/26/2018.
 */

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Categ>categs;
    private int size,num;
    public  CatAdapter(Context context, ArrayList<Categ>categs,int size,int num)
    {
        this.categs=categs;
        this.context=context;
        this.size=size;
        this.num=num;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.catmealitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mealname.setText(categs.get(position).getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)  context;
                Fragment myFragment = new MealsitemFrag().newInstance(categs.get(position).getClient_id(),categs.get(position).getFoodCat_id(),num);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealname;
        RelativeLayout relativeLayout;
        CircleImageView circleImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mealname=itemView.findViewById(R.id.mealname);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);
        }
    }
}
