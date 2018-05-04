package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Activities.Feedback;
import com.aklni.islammahoud.aklnii.Data.Client;
import com.aklni.islammahoud.aklnii.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";

    // TODO: Rename and change types of parameters

    private int id;

    private TextView footer,desc,phone,add1,add2,worktime;
    private CircleImageView logo;
    private RequestQueue mRequestQueue;
    private ArrayList<Client>clients;
    private Client client;
    private ProgressBar prog;
    private Button feedback_message;

    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(int x) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1,x);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        Context context=getContext();
        client=new Client();
        footer=view.findViewById(R.id.footer);
        desc=view.findViewById(R.id.desc);
        prog=view.findViewById(R.id.prog);
        phone=view.findViewById(R.id.phone);
        add1=view.findViewById(R.id.add);
        add2=view.findViewById(R.id.add2);
        worktime=view.findViewById(R.id.time_work);
        logo=view.findViewById(R.id.logo);
        feedback_message=view.findViewById(R.id.apply);
        feedback_message.setOnClickListener(this);
        getclient(context);
        return view;
    }
    public void getclient(final Context context) {
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
                            clients = new ArrayList<>(a.length());
                            int index = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject c = a.getJSONObject(i);
                                //  Toast.makeText(getContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                                if(c.getInt("id")==id )
                                {
                                    footer.setText(c.getString("name"));
                                    desc.setText(c.getString("description"));
                                    phone.setText(c.getString("phone"));
                                    add1.setText(c.getString("address"));
                                    if(c.getString("address2")!="null") {
                                        add2.setText(c.getString("address2"));
                                    }
                                    else {add2.setText("لا يوجد لنا اي فروع اخرى");}
                                    worktime.setText(c.getString("open"));
                                    Picasso.with(getContext()).load("http://e-mool.com/public/main/images/clients/"+c.getString("logo")).error(R.mipmap.order).into(logo);
                                    //Toast.makeText(context,c.getString("name"),Toast.LENGTH_SHORT).show();
                                    // Log.d("my trace", "loop num " + i + " sesname : " + c.getString("ses_name"));
                                }

                            }
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

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.apply)
        {
            Intent intent =new Intent(getContext(),Feedback.class);
            startActivity(intent);
        }
    }
}


