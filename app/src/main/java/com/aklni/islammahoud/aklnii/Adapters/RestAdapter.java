package com.aklni.islammahoud.aklnii.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aklni.islammahoud.aklnii.Activities.MealsActivity;
import com.aklni.islammahoud.aklnii.Data.Client;
import com.aklni.islammahoud.aklnii.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by islam mahoud on 1/20/2018.
 */

public class RestAdapter extends RecyclerView.Adapter<RestAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Client>clients;
    private int size;
    private int open,close;
    private int time;
    private  URL url;
    FirebaseFirestore db;
    public RestAdapter(Context context,ArrayList<Client> clients,int size)
    {
        this.size=size;
        this.context=context;
        this.clients=clients;
        if(clients==null)
        {
            Toast.makeText(context,"Check your network conection",Toast.LENGTH_LONG).show();
        }
        db=FirebaseFirestore.getInstance();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurantitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       Calendar calendar=Calendar.getInstance();
        time=calendar.get(Calendar.HOUR_OF_DAY);
       // Toast.makeText(context,String.valueOf(time),Toast.LENGTH_LONG).show();
        close=clients.get(position).getEnditem();
        open=clients.get(position).getStart();
        holder.ratingBar.setText("("+"الطلبات من "+clients.get(position).getStart()+" ص : "+clients.get(position).getEnditem()+" ص)");
        holder.rname.setText(clients.get(position).getName());
        holder.rdesc.setText("("+clients.get(position).getDescription()+" )");
        String imagurl="http://e-mool.com/public/main/images/clients/"
                + clients.get(position).getLogo();
        url= null;
        try {
            url = new URL(imagurl.replaceAll(" ","%20"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(context).load(url.toString())
                .into(holder.imageView);
        if(((time<=open && time<=close) || (time>=open && time>=close)) && clients.get(position).getActive()==0) {
            Picasso.with(context).load(R.mipmap.open).into(holder.statues);
        }
        else { Picasso.with(context).load(R.mipmap.close).into(holder.statues);}
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((time<=open && time<=close) || (time>=open && time>=close)) && clients.get(position).getActive()==0) {
                    SharedPreferences sharedPreferences=context.getSharedPreferences("rest_data",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("rest_id",clients.get(position).getId());
                    editor.commit();
                    Intent intent = new Intent(context, MealsActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("name", clients.get(position).getName());
                    intent.putExtra("id", clients.get(position).getId());
                    intent.putExtra("desc", clients.get(position).getDescription());
                    intent.putExtra("logo",clients.get(position).getLogo());
                    context.startActivity(intent);

                }

                    else {Toast.makeText(context,"المطعم مغلق الرجاء قراءه مواعيد العمل",Toast.LENGTH_LONG).show();}

                }
            });




    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView rname,rdesc,ropen;
        TextView ratingBar;
        RelativeLayout relativeLayout;
        ImageView statues;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.spimage);
            rname=itemView.findViewById(R.id.name);
            rdesc=itemView.findViewById(R.id.desc);
            ratingBar=itemView.findViewById(R.id.rate);
            relativeLayout=itemView.findViewById(R.id.relativeLayout);
            statues=itemView.findViewById(R.id.status);
        }
    }
}
