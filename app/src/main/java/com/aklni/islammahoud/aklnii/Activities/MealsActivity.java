package com.aklni.islammahoud.aklnii.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Data.Cart;
import com.aklni.islammahoud.aklnii.Data.Client;
import com.aklni.islammahoud.aklnii.Database.DbHelper;
import com.aklni.islammahoud.aklnii.Fragments.Catemealfrag;
import com.aklni.islammahoud.aklnii.Fragments.InfoFragment;
import com.aklni.islammahoud.aklnii.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

public class MealsActivity extends AppCompatActivity implements MaterialTabListener {

    public MaterialTabHost mTabHost;
    public ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private int position,id;
    private String name,desc;
    private List<Client> clients;
    private ArrayList<Cart>carts;
    public TextView num_oforder, appbarname;
    private int num;
    private RelativeLayout relativeLayoutcart;
    private  SharedPreferences numorders;
    private CircleImageView logo;
    private URL url;
    private DbHelper db;
    private int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        setupTabs();
        db=new DbHelper(this);
        fab();
        position=getIntent().getIntExtra("position",0);
        id=getIntent().getIntExtra("id",0);
        name=getIntent().getStringExtra("name");
        desc=getIntent().getStringExtra("desc");
        logo=findViewById(R.id.logo);
        logoimage(getIntent().getStringExtra("logo"));
        appbarname=findViewById(R.id.restname);
        appbarname.setText(name +" "+"("+desc+")");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
    }
    public void setupTabs() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mPager.setFocusable(false);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        //when the page changes in the ViewPager, update the Tabs accordingly
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);

            }
        });
        //Add all the Tabs to the TabHost
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mAdapter.getIcon(i))
                            .setTabListener(this));
        }
    }
    @Override
    public void onTabSelected(MaterialTab tab) {
        mPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

         int icons[]={R.mipmap.waiter,R.mipmap.info};
        String[] titles = {"الرعاة", "التصنيفات"};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public Fragment getItem(int x) {
            Fragment fragment = null;
            switch (x) {
                case 0:
                    fragment = Catemealfrag.newInstance(id,num);
                    break;
                case 1:
                    fragment = InfoFragment.newInstance(id);
                    break;
            }
            return fragment;

        }
        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }
    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()==0&& num>0)
        {creatDialog();}
        else{super.onBackPressed();}

    }
    public void Mvisability()
    {
        setupTabs();
mPager.setVisibility(View.GONE);
        mTabHost.setVisibility(View.GONE);
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        fab();

    }
    public void fab()
    {
        carts=db.getallcart();
        num=carts.size();
        relativeLayoutcart=findViewById(R.id.relativeLayoutcart);
        num_oforder=findViewById(R.id.ordernum);
        if(num>0)
        {
            relativeLayoutcart.setVisibility(View.VISIBLE);
        }
        else
        {
            relativeLayoutcart.setVisibility(View.GONE);
        }
        num_oforder.setText(String.valueOf(num));
    }
    public void creatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                db.deleteContact();
                carts.clear();
                num=0;
                onBackPressed();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setMessage(R.string.D_message);
        builder.create();
        builder.show();
    }
    public void logoimage(String imname)
    {
        String imagurl="http://e-mool.com/public/main/images/clients/"
                + imname;
        url= null;
        try {
           url = new URL(imagurl.replaceAll(" ","%20"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(url.toString())
                .into(logo);
    }
    @Override
    public void onDestroy()
    {
        DbHelper dp=new DbHelper(this);
        dp.deleteContact();
        SharedPreferences.Editor restid=getSharedPreferences("rest_data",MODE_PRIVATE).edit();
        restid.clear();
        restid.commit();
        super.onDestroy();
    }
}
