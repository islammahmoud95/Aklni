package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aklni.islammahoud.aklnii.Activities.LoginActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Activities.MainActivity;
import com.aklni.islammahoud.aklnii.R;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class SigninFragment extends Fragment implements View.OnClickListener {
    private EditText phonenum;
    private CountryCodePicker ccp;
    private Button btnlogin,new_register;
    private TextInputLayout phoneinput;
    private int authentication=0;
    private ProgressBar prog;
    private Context context;
    private LoginActivity loginActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signin, container, false);
        loginActivity=new LoginActivity();
        context=getContext();
        phonenum=view.findViewById(R.id.phonenum);
        ccp = view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phonenum);
        phoneinput=view.findViewById(R.id.input_phone);
        prog=view.findViewById(R.id.prog);
        prog.setVisibility(View.GONE);
        btnlogin=view.findViewById(R.id.submit);
        new_register=view.findViewById(R.id.new_register);
        btnlogin.setOnClickListener(this);
        new_register.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
            if(v.getId()==R.id.submit)
            {
                if(validatePhoneNumber()) {
                    // prog.setVisibility(View.VISIBLE);
                    // login.setVisibility(View.GONE);
                    // phoneAuthProvider.verifyPhoneNumber(phonenum.getText().toString(), 60, TimeUnit.SECONDS, this, mCallbacks);
                    authi(getContext());
                }

            }
            else if(v.getId()==R.id.new_register)
            {
                AppCompatActivity activity = (AppCompatActivity)  context;
                Fragment myFragment = new SignupFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, myFragment).commit();

            }

    }
    private boolean validatePhoneNumber() {
        String phoneNumber = phonenum.getText().toString();
        // String personname = name.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneinput.setError("تأكد من رقم الموبايل");
            return false;
        }

        return true;
    }
    public void authi(final Context context)
    {
        prog.setVisibility(View.VISIBLE);
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
                                // Toast.makeText(context,c.getString("phone"),Toast.LENGTH_LONG).show();

                                if(c.getString("phone").equals(phonenum.getText().toString()))
                                {
                                   // Toast.makeText(context,"trur",Toast.LENGTH_LONG).show();
                                    authentication=1;
                                    prog.setVisibility(View.GONE);
                                }

                            }
                            if(authentication==0)
                            {
                                prog.setVisibility(View.GONE);
                                phoneinput.setError("رقم الموبايل غير مسجل");
                            }
                            else { prog.setVisibility(View.GONE);

                                SharedPreferences sharedPreferences=getActivity().getSharedPreferences("phone_num",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("phone",phonenum.getText().toString());
                                editor.commit();
                                Intent intent=new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                SigninFragment.super.getActivity().finish();
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
                        prog.setVisibility(View.GONE);
                        Toast.makeText(context,R.string.internet_connection,Toast.LENGTH_LONG).show();

                        // L.T(context,"there is a trouble with connectivity \n please check your network connection");
                        //  Toast.makeText(context,"error message sessions: "+volleyError.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(request);
    }
}
