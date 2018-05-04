package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Adapters.Mealitemadapter;
import com.aklni.islammahoud.aklnii.Data.Sub_cat;
import com.aklni.islammahoud.aklnii.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealsitemFrag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String client_id = "cid";
    private static final String food_id = "fid";
    private static final String ordernum = "num";
    private int id,foodid,clientid,num;
    private  RecyclerView recyclerView;
    private Mealitemadapter mealitemadapter;
    private RequestQueue mRequestQueue;
    private ArrayList<Sub_cat>sub_cats;
    private int itemsize=0;
    private ProgressBar prog;

    public static MealsitemFrag newInstance(int c,int f,int n) {
        MealsitemFrag fragment = new MealsitemFrag();
        Bundle args = new Bundle();
        args.putInt(client_id,c);
        args.putInt(food_id,f);
        args.putInt(ordernum,n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clientid = getArguments().getInt(client_id);
            foodid = getArguments().getInt(food_id);
            num=getArguments().getInt(ordernum);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mealsitem, container, false);
       Context context=getContext();
        prog=view.findViewById(R.id.prog);
         recyclerView=view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        getMealdata(context);
        return view;
    }
    public void getMealdata(final Context context) {
        RequestQueue requestQueue =getRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, "http://e-mool.com/api/index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        try {
                            Log.d("My",s);
                            JSONObject e = new JSONObject(s);
                            JSONArray a = e.getJSONArray("foodItem");
                            String z= String.valueOf(a.length());
                            sub_cats = new ArrayList<>(a.length());
                            int index = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject c = a.getJSONObject(i);
                                // Toast.makeText(getContext(),String.valueOf(c.getInt("id")),Toast.LENGTH_LONG).show();
                                //  Toast.makeText(getContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                                if(c.getInt("client_id")==clientid && c.getInt("foodCat_id")==foodid)
                                {

                                    sub_cats.add(new Sub_cat());
                                    //Toast.makeText(context,c.getString("name"),Toast.LENGTH_LONG).show();
                                    sub_cats.get(itemsize).setId(c.getInt("id"));
                                    sub_cats.get(itemsize).setClient_id(c.getInt("client_id"));
                                    sub_cats.get(itemsize).setFoodCat_id(c.getInt("foodCat_id"));
                                    sub_cats.get(itemsize).setItemName(c.getString("itemName"));
                                    sub_cats.get(itemsize).setPrice(c.getString("price"));
                                    sub_cats.get(itemsize).setItemImage(c.getString("itemImage"));
                                    sub_cats.get(itemsize).setItemDescription(c.getString("itemDescription"));
                                    sub_cats.get(itemsize).setFoodCategory(c.getString("FoodCategory"));
                                    sub_cats.get(itemsize).setDiscount(c.getString("discount"));
                                    itemsize=itemsize+1;
                                    //Toast.makeText(context,c.getString("name"),Toast.LENGTH_SHORT).show();
                                    // Log.d("my trace", "loop num " + i + " sesname : " + c.getString("ses_name"));
                                }
                                else {sub_cats.add(new Sub_cat());}
                            }

                            mealitemadapter = new Mealitemadapter(context,sub_cats,itemsize,clientid,foodid);
                            // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(mealitemadapter);
                             prog.setVisibility(View.GONE);

                            itemsize=0;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // progressBar.setVisibility(View.GONE);
                        Toast.makeText(context,R.string.internet_connection,Toast.LENGTH_LONG).show();
                        // L.T(context,"there is a trouble with connectivity \n please check your network connection");
                        //Toast.makeText(context,"error message sessions: "+volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(request);


    }
    public RequestQueue getRequestQueue(Context con) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(con);
        }
        return mRequestQueue;
    }

}