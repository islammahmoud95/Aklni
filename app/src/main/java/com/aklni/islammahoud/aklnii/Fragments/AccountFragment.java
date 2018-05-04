package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment implements View.OnClickListener {
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private static final String EMAIL = "email";
    private SharedPreferences sharedPreferences;
    private TextView name ,phone ,orders,email;
    private Button editphone,fa;
    private String userphone;
    private CircleImageView circleImageView;
    private LinearLayout linearLayout;
    private String cover,em,profile;
    private ImageView coverimage;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);
       // Toast.makeText(getContext(),userphone,Toast.LENGTH_LONG).show();
        linearLayout=view.findViewById(R.id.linearlayout);
        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
        email=view.findViewById(R.id.email);
        coverimage=view.findViewById(R.id.cover);
        fa=view.findViewById(R.id.fa);
        orders=view.findViewById(R.id.numorder);
        circleImageView=view.findViewById(R.id.profile);
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        fa.setOnClickListener(this);
        setuserdata();
        Profile pof=Profile.getCurrentProfile();
if(pof!=null) {
    Picasso.with(getContext()).load(pof.getProfilePictureUri(50, 50)).into(circleImageView);
    view.findViewById(R.id.fa).setVisibility(View.GONE);
}
return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void setuserdata()
    {
        SharedPreferences user_data = getContext().getSharedPreferences("user_data", MODE_PRIVATE);
        linearLayout.setVisibility(View.VISIBLE);
        name.setText(user_data.getString("name","  "));
        phone.setText(user_data.getString("phone","  "));
        email.setText(user_data.getString("email", "  "));
        orders.setText(user_data.getString("count","0"));
    }
    public void loginwithfacebook() {
        loginButton.callOnClick();
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.setReadPermissions(Arrays.asList("public_profile"));
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        callbackManager=CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile pof=Profile.getCurrentProfile();
                Picasso.with(getContext()).load(pof.getProfilePictureUri(50,50)).into(circleImageView);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                // Application code
                                try {
                                     em = object.getString("email");
                                    profile=object.getString("");
                                    cover=object.getString("source");
                                    Picasso.with(getContext()).load(cover).into(coverimage);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fa);
        {
            loginwithfacebook();
        }
    }
}