package com.aklni.islammahoud.aklnii.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.aklni.islammahoud.aklnii.Fragments.SigninFragment;
import com.aklni.islammahoud.aklnii.R;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
       if( hasWindowFocus()) {
           getCurrentFocus().clearFocus();
       }
        findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(getCurrentFocus()!=null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    getCurrentFocus().clearFocus();
                }
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SigninFragment()).commit();


    }
    @Override
public void onBackPressed()
    {
        isFinishing();
        new MainActivity().finish();
        super.onBackPressed();
        super.onBackPressed();
    }
}
