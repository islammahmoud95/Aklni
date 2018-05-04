package com.aklni.islammahoud.aklnii.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Data.Cart;
import com.aklni.islammahoud.aklnii.Data.Extra;
import com.aklni.islammahoud.aklnii.Database.DbHelper;
import com.aklni.islammahoud.aklnii.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Activity implements View.OnClickListener {
    private RequestQueue mRequestQueue;
    private DbHelper db;

    private TextView name, price, ordernum, meal_price;
    private Button plus, minus, submit, ex;
    private ImageView logo;
    private LinearLayout linearLayout;
    private CheckBox checkBox;
    private ProgressBar progressBar;

    private String itemprice, itemname, foodcategory;
    private float allprice, num_ofordr;
    private int clientid, mealid, foodid, cheque = 1, extraprice, itemsposition = 0;

    private ArrayList<Extra> extras;
    private ArrayList<CheckBox> checkBoxArrayList;
    private ArrayList<Cart> carts;
    private List<Integer> extranum;
    private List<String> extraStr;
    private Spinner spinner;
    private String extradesc,pound=" ج.م ";
    private float extra_price;
    boolean extvalidation=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        db = new DbHelper(getApplicationContext());
        extras = new ArrayList<>();
        extranum = new ArrayList<>();
        extraStr = new ArrayList<>();
        checkBoxArrayList = new ArrayList<>();
        extranum.add(cheque);
        extraStr.add(String.valueOf(cheque));
        mealid = getIntent().getIntExtra("id", 0);
        foodid = getIntent().getIntExtra("foodid", 0);
        clientid = getIntent().getIntExtra("clientid", 0);
        itemprice = getIntent().getStringExtra("price");
        itemname = getIntent().getStringExtra("name");
        foodcategory = getIntent().getStringExtra("FoodCategory");
        progressBar = findViewById(R.id.prog);
        getradio(this);
        linearLayout=findViewById(R.id.orderlayout);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        meal_price = findViewById(R.id.allprice);
        submit = findViewById(R.id.submit);
        ex = findViewById(R.id.ex);
        ordernum = findViewById(R.id.ordernum);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        submit.setOnClickListener(this);
        ex.setOnClickListener(this);
        name.setText(itemname);
        price.setText(itemprice+pound);
        ordernum.setText(String.valueOf(cheque));
        allprice = Float.parseFloat(itemprice);
        meal_price.setText(String.valueOf(allprice)+pound);
        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout


    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.submit) {
            if(extvalidation) {
                SharedPreferences numorders = getSharedPreferences("numorder", MODE_PRIVATE);
                int num = numorders.getInt("num", 0);
                SharedPreferences.Editor numorder = getSharedPreferences("numorder", MODE_PRIVATE).edit();
                num = num + 1;
                numorder.putInt("num", num);
                numorder.commit();
                //allprice = Float.parseFloat(itemprice) * cheque;
                Cart cart = new Cart();
                cart.setFoodcategory(foodcategory);
                cart.setName(itemname);
                cart.setPrice(String.valueOf(allprice));
                cart.setNum(String.valueOf(cheque));
                cart.setCheck(1);
                cart.setExtra(extradesc);
                db.addorder(cart);
                onBackPressed();
            }
            else {ex.setText("من فضلك اختار مكونات");
            ex.setTextColor(Color.RED);}
        } else if (id == R.id.minus) {
            if (cheque > 1) {
                cheque--;
                ordernum.setText(String.valueOf(cheque));
                allprice = allprice - Float.parseFloat(itemprice);
                meal_price.setText(String.valueOf(allprice)+pound);
                extranum.remove(cheque);
            }

        } else if (id == R.id.plus) {
            cheque++;
            ordernum.setText(String.valueOf(cheque));
            allprice = allprice + Float.parseFloat(itemprice);
            meal_price.setText(String.valueOf(allprice)+pound);
            extranum.add(cheque);
        } else if (id == R.id.ex) {
            if (itemsposition > 0) {
                Intent intent = new Intent(this, ExtraActivity.class);
                intent.putExtra("exstra", extras);
                intent.putIntegerArrayListExtra("extranum", (ArrayList<Integer>) extranum);
                intent.putExtra("exvalid",extvalidation);
                startActivityForResult(intent, 1);
                allprice=allprice-extra_price;

            } else {
                Toast.makeText(this, "لا يوجد اضافات له", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void getradio(final Context context) {
        RequestQueue requestQueue = getRequestQueue(context);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, "http://e-mool.com/api/index",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
                        try {
                            Log.d("My", s);
                            JSONObject e = new JSONObject(s);
                            JSONArray a = e.getJSONArray("extra");
                            String z = String.valueOf(a.length());
                            extras = new ArrayList<>(a.length());
                            int index = 0;
                            for (int i = 0; i < a.length(); i++) {
                                JSONObject c = a.getJSONObject(i);
                                //  Toast.makeText(getContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                                if (c.getInt("client_id") == clientid && c.getInt("id") == mealid) {
                                    extras.add(new Extra());
                                    extras.get(itemsposition).setExtra_id(c.getInt("extra_id"));
                                    extras.get(itemsposition).setName(c.getString("name"));
                                    extras.get(itemsposition).setPrice(c.getString("extraPrice"));
                                    if(c.getString("extraPrice").equals("0"))
                                    {
                                        extvalidation=false;
                                    }
                                    itemsposition++;
                                }

                            }
                            linearLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            if(itemsposition>0)
                            {
                                ex.setVisibility(View.VISIBLE);
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
                        Toast.makeText(context, R.string.internet_connection, Toast.LENGTH_LONG).show();
                        // L.T(context,"there is a trouble with connectivity \n please check your network connection");
                       // Toast.makeText(context, "error message sessions: " + volleyError.getMessage(), Toast.LENGTH_LONG).show();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                extradesc = data.getStringExtra("checkbox");
                extra_price=data.getFloatExtra("price", 0);
               // Toast.makeText(this,String.valueOf(extra_price),Toast.LENGTH_LONG).show();
                extvalidation=data.getBooleanExtra("valid",true);
                allprice=allprice+extra_price;
                meal_price.setText(String.valueOf(allprice)+pound);
                ex.setText("تعديل الأضافات");
            }
        }

    }
}