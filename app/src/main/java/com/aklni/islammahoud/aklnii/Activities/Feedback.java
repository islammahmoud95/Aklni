package com.aklni.islammahoud.aklnii.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.R;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity implements View.OnClickListener {
    private EditText feedbacktext;
    private Button submitl;
    private String name,email;
    private String client_id;
    private SharedPreferences user_data,rest_data;
    private TextInputLayout textInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        feedbacktext=findViewById(R.id.list);
        textInputLayout=findViewById(R.id.feed_input);
        submitl=findViewById(R.id.apply);
        user_data =getSharedPreferences("user_data", MODE_PRIVATE);
        rest_data = getSharedPreferences("rest_data", MODE_PRIVATE);
        client_id =String.valueOf(rest_data.getInt("rest_id",0));
        name =user_data.getString("name"," ");
        email=user_data.getString("email"," ");
        submitl.setOnClickListener(this);
    }
    public void getcatdata(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, "http://e-mool.com/api/complaints",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Toast.makeText(context, "شكرا لأهتمامك", Toast.LENGTH_LONG).show();
                       Feedback.super.onBackPressed();

                        //db.deleteContact();
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
        {  @Override
        protected Map<String,String> getParams()throws AuthFailureError {
            Map<String,String> params = new HashMap<String, String>();
            params.put("name",name);
            params.put("email", email);
            params.put("msg",feedbacktext.getText().toString());
            params.put("client_id", client_id);
            return params;
        }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        queue.add(request);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.apply)
        {
            getcatdata(this);

        }
    }
    public boolean textvalidation()
    {
        String text=feedbacktext.getText().toString();
        if(TextUtils.isEmpty(text)&& text.length()>50)
        {
            textInputLayout.setError("يجب الا يقل المدخل ع 50 حرف");
        }
        return true;
    }
}
