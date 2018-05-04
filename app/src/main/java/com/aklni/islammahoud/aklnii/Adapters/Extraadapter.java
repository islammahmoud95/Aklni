package com.aklni.islammahoud.aklnii.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Data.Extra;
import com.aklni.islammahoud.aklnii.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by islam mahoud on 2/9/2018.
 */

public class Extraadapter extends RecyclerView.Adapter<Extraadapter.ViewHolder> {
    private ArrayList<Extra>extras;
    private List<CheckBox>checkBoxList;
    private Context context;
    private int size;
    public int itemcount=0;
    private int items=0;
    private float price;
    public Extraadapter(Context context, ArrayList<Extra> extras,int size)
    {
        this.context=context;
        this.size=size;
        this.extras=extras;
        checkBoxList=new ArrayList<>();
    }
    public Extraadapter()
    {
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.exstraitem,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.prices.setText(" ("+extras.get(position).getPrice()+" ج.م"+")");
        holder.checkBox.setText(extras.get(position).getName());
        holder.checkBox.setContentDescription( holder.textView.getText());
        holder.checkBox.setChecked(false);
        holder.textView.setText(String.valueOf(0));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //Toast.makeText(context,holder.checkBox.getText(),Toast.LENGTH_LONG).show();
                    if (isChecked) {
                        holder.minus.setActivated(true);
                        holder.plus.setActivated(true);
                        holder.checkBox.setContentDescription(holder.textView.getText());
                        holder.minus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Integer.parseInt(holder.textView.getText().toString()) > 0) {
                                    int x = Integer.parseInt(holder.textView.getText().toString());
                                    holder.textView.setText(String.valueOf(x - 1));
                                    price = price - Float.parseFloat(extras.get(position).getPrice());
                                    itemcount--;
                                    holder.checkBox.setContentDescription(holder.textView.getText());

                                }
                            }
                        });
                        holder.plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemcount < size) {
                                    int x = Integer.parseInt(holder.textView.getText().toString());
                                    holder.textView.setText(String.valueOf(x + 1));
                                    price = price + Float.parseFloat(extras.get(position).getPrice());
                                    itemcount++;
                                    holder.checkBox.setContentDescription(holder.textView.getText());

                                }
                            }
                        });
                    }
                    else {
                        holder.checkBox.setChecked(false);
                        holder.minus.setClickable(false);
                        holder.plus.setClickable(false);
                        int x = Integer.parseInt(holder.textView.getText().toString());
                        price = price - Float.parseFloat(extras.get(position).getPrice())*x;
                        itemcount= itemcount-x;
                        holder.textView.setText("0");

                    }
                }
            });
        checkBoxList.add(holder.checkBox);
      // Toast.makeText(context, checkBoxList.get(0).getText(),Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return extras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        Button plus,minus;
        TextView textView,prices;
        public ViewHolder(View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.check);
            plus=itemView.findViewById(R.id.plus);
            minus=itemView.findViewById(R.id.minus);
            textView=itemView.findViewById(R.id.ordernum);
            prices=itemView.findViewById(R.id.price);
        }
    }
public ArrayList<CheckBox> getcheckbox()
{
    return (ArrayList<CheckBox>) checkBoxList;
}
public Float getexprice()
        {
                return price;
        }
}
