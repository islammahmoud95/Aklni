package com.aklni.islammahoud.aklnii.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Fragments.AboutusFra;
import com.aklni.islammahoud.aklnii.Fragments.AccountFragment;
import com.aklni.islammahoud.aklnii.Fragments.RestaurantFrag;
import com.aklni.islammahoud.aklnii.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout linearLayout;
    private int pressed=0;
    private int num;
    private TextView num_oforder;
    private FirebaseAuth mAuth;
    private  NavigationView navigationView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CallbackManager callbackManager;

    private AccessTokenTracker token;
    private Profile profile;
    private ProfileTracker profileTracker;
    private CircleImageView improfile;
    private TextView user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        mAuth= FirebaseAuth.getInstance();
        callbackManager=CallbackManager.Factory.create();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       getSupportFragmentManager().beginTransaction().replace(R.id.containr,new RestaurantFrag()).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
       /// startService(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.restaurant);
        navigationView.setNavigationItemSelectedListener(this);
        improfile= navigationView.getHeaderView(0).findViewById(R.id.profile);
        user_name=(TextView) navigationView.getHeaderView(0).findViewById(R.id.name);
        final MenuItem menuItem=navigationView.getMenu().findItem(R.id.restaurant);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        if(menuItem.isChecked())
                        {
                            getSupportFragmentManager().beginTransaction().replace(R.id.containr,new RestaurantFrag()).commit();
                            //getordercount(getApplicationContext(),"http://e-mool.com/api/orderCount");
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        swipeRefreshLayout.setRefreshing(false);


                    }
                }
        );
        profile=Profile.getCurrentProfile();
        makeprofile(profile);
        token=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };
        profileTracker=new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                makeprofile(currentProfile);

            }
        };
        token.startTracking();
        profileTracker.startTracking();

    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containr,new AccountFragment()).commit();
            navigationView.setCheckedItem(R.id.account);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.restaurant) {
            if(! item.isChecked())
            getSupportFragmentManager().beginTransaction().replace(R.id.containr,new RestaurantFrag()).addToBackStack(" ").commit();
            // Handle the camera action
        } else if (id == R.id.account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containr,new AccountFragment()).addToBackStack(" ").commit();

        } else if (id == R.id.info) {
            getSupportFragmentManager().beginTransaction().replace(R.id.containr,new AboutusFra()).addToBackStack(" ").commit();

        } else if (id == R.id.feedback) {
             SharedPreferences sharedPreferences;
            sharedPreferences=getSharedPreferences("rest_data",MODE_PRIVATE);
            SharedPreferences.Editor shred= sharedPreferences.edit();
            shred.clear();
            shred.apply();
            Intent intent =new Intent(this,Feedback.class);
            startActivity(intent);

        } else if (id == R.id.chat) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://details?id=" + getPackageName()));
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences numorders = getSharedPreferences("phone_num", MODE_PRIVATE);
        if (!numorders.contains("phone")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
    @Override
    public void onResume()
    {
        super.onResume();
    }
    private void makeprofile(Profile profile){
        if(profile != null){
            Picasso.with(getApplicationContext()).load(profile.getProfilePictureUri(150,150)).into(improfile);
            user_name.setText(profile.getFirstName()+" "+profile.getLastName() );

        }
    }
}
