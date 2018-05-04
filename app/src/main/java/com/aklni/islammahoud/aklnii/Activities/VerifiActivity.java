package com.aklni.islammahoud.aklnii.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.aklni.islammahoud.aklnii.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifiActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText verifynum;
    private TextInputLayout verify_input;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider phoneAuthProvider;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private static final String TAG = "PhoneAuthActivity";
    private LinearLayout login,verify,register;
    private FirebaseUser user;
    private String ver_id,ver_code;
    private PhoneAuthProvider.ForceResendingToken mtoken;
    private String phonenum,name,email,address;
    private Button verifybtn,resentbtn;
    private LoginActivity loginActivity;
    private ProgressBar prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifi);
        prog=findViewById(R.id.prog);
        prog.setVisibility(View.GONE);
        loginActivity=new LoginActivity();
        mAuth = FirebaseAuth.getInstance();
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        phonenum=getIntent().getStringExtra("phone_num");
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        address=getIntent().getStringExtra("address");
        verifynum=findViewById(R.id.verifynum);
        verifybtn=findViewById(R.id.verifybtn);
        resentbtn=findViewById(R.id.button_resend);
        verify_input=findViewById(R.id.verify_input);
        verifybtn.setOnClickListener(this);
        resentbtn.setOnClickListener(this);
        phoneAuthProvider=PhoneAuthProvider.getInstance();
        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifynum.setText(phoneAuthCredential.getSmsCode());
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
               // Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                   Toast.makeText(getApplicationContext(),"هذا الرقم غير صحيح",Toast.LENGTH_LONG).show();
                    VerifiActivity.super.finish();
                    // [END_EXCLUDE]
                }

            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token)
            {
                ver_id=verificationId;
                mtoken=token;
                Toast.makeText(getApplicationContext()," تم ارسال رسالة التأكيد",Toast.LENGTH_LONG).show();
            }
        };
        phoneAuthProvider.verifyPhoneNumber(phonenum, 60, TimeUnit.SECONDS, this, mCallbacks);
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            SharedPreferences.Editor phone=getSharedPreferences("phone_num",MODE_PRIVATE).edit();
                            phone.putString("phone",phonenum);
                            phone.commit();
                            prog.setVisibility(View.VISIBLE);
                            getcatdata(getApplicationContext());


                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                verify_input.setError("تاكد من الكود الصحيح");

                            }
                        }
                    }
                });
    }

    public void getcatdata(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://e-mool.com/api/customer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                       // Toast.makeText(context, s, Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        VerifiActivity.super.finish();
                        loginActivity.finish();
                        prog.setVisibility(View.GONE);


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
                        prog.setVisibility(View.GONE);
                    }
                })
        {
            @Override
            protected Map<String,String> getParams()throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",name);
                params.put("email",email);
                params.put("phone",phonenum);
                params.put("address",address);
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
        if(v.getId()==R.id.verifybtn) {
            if (validateverifynum()) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(ver_id, verifynum.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        }
        else if(v.getId()==R.id.button_resend)
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phonenum,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks,         // OnVerificationStateChangedCallbacks
                    mtoken);

        }
        else if(v.getId()==R.id.verifybtn)
        {
            verifyPhoneNumberWithCode(ver_id,phonenum);
        }

    }
    public boolean validateverifynum()
    {
        String x=verifynum.getText().toString();
        if(TextUtils.isEmpty(x))
        {
            verify_input.setError("ادخل كود التأكيد");
            return false;
        }
        return true;
    }
}
