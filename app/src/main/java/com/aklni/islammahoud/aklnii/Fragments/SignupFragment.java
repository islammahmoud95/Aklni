package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Activities.VerifiActivity;
import com.aklni.islammahoud.aklnii.Data.Users;
import com.aklni.islammahoud.aklnii.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class SignupFragment extends Fragment implements View.OnClickListener {
    private EditText phonenum,verifynum,name,email,address;
    private Button signup;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider phoneAuthProvider;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private static final String TAG = "PhoneAuthActivity";
    private LinearLayout login,verify,register;
    private ProgressBar prog;
    private FirebaseUser user;
    private String ver_id,ver_code;
    private PhoneAuthProvider.ForceResendingToken mtoken;
    private RequestQueue mRequestQueue;
    private ArrayList<Users> userses;
    private TextInputLayout nameinput,emailinput,addinput,phoneinput;
    private Context context;
    private CountryCodePicker ccp;
    private int authentication=0;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        context=getContext();
        nameinput=view.findViewById(R.id.input_name);
        addinput=view.findViewById(R.id.input_address);
        emailinput=view.findViewById(R.id.input_email);
        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        address=view.findViewById(R.id.address);
        phonenum=view.findViewById(R.id.phonenum);
        ccp = view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phonenum);
        phoneinput=view.findViewById(R.id.input_phone);
        prog=view.findViewById(R.id.prog);
        prog.setVisibility(View.GONE);
        signup=view.findViewById(R.id.signup);
        signup.setOnClickListener(this);
        return view;
    }
    private boolean validatenewregister() {
        String phoneNumber = phonenum.getText().toString();
        String personname = name.getText().toString();
        String personemail = email.getText().toString();
        String personadd = address.getText().toString();
        if (TextUtils.isEmpty(personname)) {
            nameinput.setError("يرجى كتابه الاسم");
            return false;
        }
        if (TextUtils.isEmpty(personemail)) {
            emailinput.setError("يرجى كتابه الايميل");
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber) || (phoneNumber.length()<11 || phoneNumber.length()>15)) {
            phoneinput.setError("تأكد من رقم الموبايل");
            return false;
        }

        if (TextUtils.isEmpty(personadd)) {
            addinput.setError("يرجى كتابه عنوانك الخاص");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signup)
        {
            if(validatenewregister()) {

             authi(getContext());  //phoneAuthProvider.verifyPhoneNumber(phonenum.getText().toString(), 60, TimeUnit.SECONDS, (Activity) context, mCallbacks);

            }

        }

    }
    public void authi(final Context context)
    {
        prog.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, "http://e-mool.com/api/index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                    //    Toast.makeText(context, phonenum.getText().toString(), Toast.LENGTH_LONG).show();
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
                                  //  Toast.makeText(context,"trur",Toast.LENGTH_LONG).show();
                                    authentication=1;
                                    prog.setVisibility(View.GONE);
                                }

                            }
                            if(authentication==0)
                            {
                                prog.setVisibility(View.GONE);
                                Intent intent = new Intent(context, VerifiActivity.class);
                                intent.putExtra("name",name.getText().toString());
                                intent.putExtra("email",email.getText().toString());
                                intent.putExtra("address",address.getText().toString());
                                intent.putExtra("phone_num", phonenum.getText().toString());
                                startActivityForResult(intent, 1);
                            }
                            else { prog.setVisibility(View.GONE);
                                phoneinput.setError("رقم الموبايل مسجل لمستخدم اخر");}



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        prog.setVisibility(View.GONE);
                        Toast.makeText(context,R.string.internet_connection,Toast.LENGTH_LONG).show();

                    }
                });

        requestQueue.add(request);
    }
}
