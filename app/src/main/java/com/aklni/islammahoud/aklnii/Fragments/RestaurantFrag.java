package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Adapters.RestAdapter;
import com.aklni.islammahoud.aklnii.Data.Client;
import com.aklni.islammahoud.aklnii.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFrag extends Fragment {
    private RestAdapter clientsadapter;
    private RecyclerView recyclerView;
    private ArrayList<Client> clientses;
    private RequestQueue mRequestQueue;
    private int itemsize=0;
   private ProgressBar prog;
    private int customer_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_restaurant, container, false);
        authi(getContext());
        Context context=getContext();
        prog=view.findViewById(R.id.prog);
        recyclerView=view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        getclientdata(context);
        // clientsadapter = new Clientsadapter(context,clientses);
        // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerView.setAdapter(clientsadapter);
        return view;
    }
    public void getclientdata(final Context context) {
        prog.setVisibility(View.VISIBLE);
        RequestQueue requestQueue =getRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, "http://e-mool.com/api/index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        try {
                            Log.d("My",s);
                            JSONObject e = new JSONObject(s);
                            JSONArray a = e.getJSONArray("clients");
                            String z= String.valueOf(a.length());
                            clientses = new ArrayList<>(a.length());
                           // Toast.makeText(context,String.valueOf(clientses.size()),Toast.LENGTH_LONG).show();
                            int index = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject c = a.getJSONObject(i);
                                // Toast.makeText(getContext(),String.valueOf(c.getInt("id")),Toast.LENGTH_LONG).show();
                                //  Toast.makeText(getContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                                if(c.getInt("cat_id")==5 || c.getInt("cat_id")==6)
                                {

                                    clientses.add(new Client());
                                    //Toast.makeText(context,c.getString("name"),Toast.LENGTH_LONG).show();
                                    clientses.get(itemsize).setId(c.getInt("id"));
                                    clientses.get(itemsize).setName(c.getString("name"));
                                    clientses.get(itemsize).setLogo(c.getString("logo"));
                                    clientses.get(itemsize).setAddress(c.getString("address"));
                                    clientses.get(itemsize).setPhone(c.getString("phone"));
                                    clientses.get(itemsize).setOpen(c.getString("open"));
                                    clientses.get(itemsize).setDescription(c.getString("description"));
                                    clientses.get(itemsize).setAddress2(c.getString("address2"));
                                    clientses.get(itemsize).setStart(c.getInt("start"));
                                    clientses.get(itemsize).setEnditem(c.getInt("end"));
                                    clientses.get(itemsize).setActive(c.getInt("active"));
                                    itemsize=itemsize+1;
                                    //Toast.makeText(context,c.getString("name"),Toast.LENGTH_SHORT).show();
                                    // Log.d("my trace", "loop num " + i + " sesname : " + c.getString("ses_name"));
                                }
                                else {clientses.add(new Client());}
                            }

                            clientsadapter = new RestAdapter(context,clientses,itemsize);
                            // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            recyclerView.setAdapter(clientsadapter);
                           // progressBar.setVisibility(View.GONE);
                            itemsize=0;
                            prog.setVisibility(View.GONE);


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
                        prog.setVisibility(View.GONE);
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
    public void authi(final Context context) {
        SharedPreferences numorders = context.getSharedPreferences("phone_num", MODE_PRIVATE);
        final String userphone=numorders.getString("phone"," ");
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, "http://e-mool.com/api/index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(context, phonenum.getText().toString(), Toast.LENGTH_LONG).show();
                        try {
                            Log.d("My",s);
                            JSONObject e = new JSONObject(s);
                            JSONArray a = e.getJSONArray("customers");
                            String z= String.valueOf(a.length());
                            int index = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject c = a.getJSONObject(i);


                                if(c.getString("phone").equals(userphone))
                                {
                                    customer_id=c.getInt("id");
                                    SharedPreferences sharedPreference=context.getSharedPreferences("user_data",MODE_PRIVATE);
                                    SharedPreferences.Editor edit=sharedPreference.edit();
                                    edit.putInt("id",c.getInt("id"));
                                    edit.putString("name",c.getString("name"));
                                    edit.putString("phone",c.getString("phone"));
                                    edit.putString("email",c.getString("email"));
                                    edit.apply();
                                    getordercount(context,"http://e-mool.com/api/orderCount");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // progressBar.setVisibility(View.GONE);
                        // L.T(context,"there is a trouble with connectivity \n please check your network connection");
                        //  Toast.makeText(context,"error message sessions: "+volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(request);
    }


    public void getordercount(final Context context, String url)
    {
        // Post params to be sent to the server
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        // Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject a=new JSONObject(s);
                            SharedPreferences sharedPreference=context.getSharedPreferences("user_data",MODE_PRIVATE);
                            SharedPreferences.Editor edit=sharedPreference.edit();
                            edit.putString("count",String.valueOf(a.getString("count")));
                            edit.apply();
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
                        VolleyLog.e("Error: ", volleyError.getMessage());
                    }
                })
        {
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer_id",String.valueOf(customer_id));
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }
}