package com.aklni.islammahoud.aklnii.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.R;
import com.aklni.islammahoud.aklnii.Utility.TypeWriter;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Locale;

public class Splaj extends  AppCompatActivity {

    private final static int SPLASH_DISPLAY_LENGTH = 5000;
    private ProgressBar progressBar;
    private Context context;
    private TypeWriter ask_food,from_phon;
    private ImageView logo,pizza,burgger,grille,plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_splaj);
        context = getApplicationContext();
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/bb.ttf");
        logo=findViewById(R.id.logo);
        plate=findViewById(R.id.plate);
        pizza=findViewById(R.id.pizza);
        burgger=findViewById(R.id.burger);
        ask_food=findViewById(R.id.ask_food);
        grille=findViewById(R.id.grilled);
        ask_food.setTypeface(custom_font);

        ask_food.setText("");
        ask_food.setCharacterDelay(150);
        //ask_food.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        ask_food.animateText(getResources().getString(R.string.splaj)+"\n"+getResources().getString(R.string.splaj2));
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.splash_icon);
        Animation animation2= AnimationUtils.loadAnimation(this,R.anim.splash_items);
        Animation animation3= AnimationUtils.loadAnimation(this,R.anim.splash_items);
        logo.setAnimation(animation);
        pizza.setAnimation(animation2);
        burgger.setAnimation(animation2);
        grille.setAnimation(animation2);
        plate.setAnimation(animation3);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splaj.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
