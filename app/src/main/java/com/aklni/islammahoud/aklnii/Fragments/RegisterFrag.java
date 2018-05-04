package com.aklni.islammahoud.aklnii.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aklni.islammahoud.aklnii.Activities.CartActivity;
import com.aklni.islammahoud.aklnii.Activities.MealsActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aklni.islammahoud.aklnii.Activities.MainActivity;
import com.aklni.islammahoud.aklnii.Data.Cart;
import com.aklni.islammahoud.aklnii.Database.DbHelper;
import com.aklni.islammahoud.aklnii.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFrag extends Fragment {
    private Button loc,sub;
    private EditText add;
    private TextView addtext,phone,price,aklniprice,person_name;
    private Context context;
    private DbHelper db;
    private ArrayList<Cart>carts;
    private String order="";
    private String orderText,name,exstras,address,num,foodcategory;
    private float cost =0;
    private int client, customer;
    private  SharedPreferences user_data,rest_data;
    private TextInputLayout add_input;
    private CartActivity cartActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
         user_data = getContext().getSharedPreferences("user_data", MODE_PRIVATE);
        rest_data = getContext().getSharedPreferences("rest_data", MODE_PRIVATE);
        add=view.findViewById(R.id.add);
        person_name=view.findViewById(R.id.name);
        add_input=view.findViewById(R.id.add_input);
        client =rest_data.getInt("rest_id",0);
        customer =user_data.getInt("id",0);
        phone=view.findViewById(R.id.phone);
        phone.setText(user_data.getString("phone"," "));
        person_name.setText(user_data.getString("name"," "));
        price=view.findViewById(R.id.price);
        aklniprice=view.findViewById(R.id.aklniprice);
        context=getContext();
        db=new DbHelper(context);
        carts=db.getallcart();
        for(int i=0;i<carts.size();i++)
        {
            if(carts.get(i).getExtra()!=null) {
                exstras = carts.get(i).getExtra();
            }
            else exstras=" ";
            foodcategory=carts.get(i).getFoodcategory();
            name=carts.get(i).getName()+" ";
            num=carts.get(i).getNum()+" ";
            order=order+" \n "+foodcategory+num + name +exstras;
            cost = cost +Float.parseFloat(carts.get(i).getPrice());
        }
        price.setText(String.valueOf(cost));
        aklniprice.setText(String.valueOf(.5));
     //Toast.makeText(context,order,Toast.LENGTH_LONG).show();
        sub=view.findViewById(R.id.submit);
        phone=view.findViewById(R.id.phone);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatePhoneNumber()) {
                    address=add.getText().toString();
                    getcatdata(context);
                }
            }
        });
        return view;
    }
    public void getcatdata(final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, "http://e-mool.com/api/getorder",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                       Toast.makeText(context, "تم اضافه اوردرك", Toast.LENGTH_LONG).show();
                        db.deleteContact();
                        Intent intent=new Intent(context,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        new CartActivity().finish();
                        RegisterFrag.super.getActivity().finish();
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
            params.put("orderText",order);
            params.put("customer_id", String.valueOf(customer));
            params.put("client_id", String.valueOf(client));
            params.put("address",address);
            params.put("cost", String.valueOf(cost));

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
    private boolean validatePhoneNumber() {
        String phoneNumber = add.getText().toString();
        // String personname = name.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            add_input.setError("يرجى ادخال العنوان");
            return false;
        }

        return true;
    }
}