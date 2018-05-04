package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Adapters.CartAdapter;
import com.aklni.islammahoud.aklnii.Data.Cart;
import com.aklni.islammahoud.aklnii.Database.DbHelper;
import com.aklni.islammahoud.aklnii.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment  {
    private ArrayList<Cart>carts;
    private RecyclerView recyclerView;
    private DbHelper db;
    private CartAdapter cartAdapter;
    private Button btn;
    private Context context;
    private TextView aklniprice;
    private float allprice=0;
    private LinearLayout res;

    public static CartFragment newInstance(int i) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);
        aklniprice=view.findViewById(R.id.aklniprice);
        aklniprice.setText(String.valueOf(.5)+" ج.م ");
        res=view.findViewById(R.id.res);
        context=getContext();
        carts=new ArrayList<>();
        db=new DbHelper(context);
        carts=db.getallcart();
        cartAdapter=new CartAdapter(context,carts);
        recyclerView=view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(cartAdapter);
        btn=view.findViewById(R.id.cart);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)  context;
                Fragment registerFrag = new RegisterFrag();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, registerFrag).addToBackStack(" ").commit();

            }
        });
        return view;
    }
    // TODO: Rename method, update argument and hook method into UI event

}