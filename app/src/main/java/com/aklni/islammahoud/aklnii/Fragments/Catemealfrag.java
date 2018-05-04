package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.aklni.islammahoud.aklnii.Adapters.CatAdapter;
import com.aklni.islammahoud.aklnii.Data.Cart;
import com.aklni.islammahoud.aklnii.Data.Categ;
import com.aklni.islammahoud.aklnii.Database.DbHelper;
import com.aklni.islammahoud.aklnii.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Catemealfrag extends Fragment {
    private CatAdapter catAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<Categ> categs;
    private RequestQueue mRequestQueue;
    private int itemsize=0;
    private ProgressBar progressBar;
    private static String client_id="id";
    private static String numorder="num";
    private int id;
    private int num;
    private int fabnum;
    private ArrayList<Cart>carts;
    private DbHelper db;
    private ProgressBar prog;
    public static Catemealfrag newInstance(int i,int n) {
        Catemealfrag fragment = new Catemealfrag();
        Bundle args = new Bundle();
        args.putInt(Catemealfrag.client_id,i);
        args.putInt(Catemealfrag.numorder,n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            id=getArguments().getInt(client_id);
            num=getArguments().getInt(numorder);
    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_catemeal, container, false);
        Context context=getContext();
        prog=view.findViewById(R.id.prog);
        recyclerView=view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        getcatdata(context);
        return view;
    }
    public void getcatdata(final Context context) {
        RequestQueue requestQueue =getRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, "http://e-mool.com/api/index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        try {
                            Log.d("My",s);
                            JSONObject e = new JSONObject(s);
                            JSONArray a = e.getJSONArray("foodClient");
                            String z= String.valueOf(a.length());
                            categs = new ArrayList<>(a.length());
                            int index = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject c = a.getJSONObject(i);
                                //  Toast.makeText(getContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                                if(c.getInt("client_id")==id )
                                {
                                    categs.add(new Categ());
                                    categs.get(itemsize).setFoodCat_id(c.getInt("foodCat_id"));
                                    categs.get(itemsize).setName(c.getString("catName"));
                                    categs.get(itemsize).setClient_id(c.getInt("client_id"));
                                    itemsize=itemsize+1;
                                    //Toast.makeText(context,c.getString("name"),Toast.LENGTH_SHORT).show();
                                    // Log.d("my trace", "loop num " + i + " sesname : " + c.getString("ses_name"));
                                }
                                else {categs.add(new Categ());}
                            }

                            catAdapter = new CatAdapter(context,categs,itemsize,num);
                            // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(catAdapter);
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
                       // Toast.makeText(context,"error message sessions: "+volleyError.getMessage(),Toast.LENGTH_LONG).show();
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