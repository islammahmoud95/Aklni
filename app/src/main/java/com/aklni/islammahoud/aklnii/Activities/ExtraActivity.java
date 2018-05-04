package com.aklni.islammahoud.aklnii.Activities;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.aklni.islammahoud.aklnii.Adapters.Extraadapter;
import com.aklni.islammahoud.aklnii.Data.Extra;
import com.aklni.islammahoud.aklnii.R;
import java.util.ArrayList;
import java.util.List;

public class ExtraActivity extends AppCompatActivity implements View.OnClickListener
{

    private ArrayList<Extra>extras;
    private ArrayList<CheckBox> checkBoxArrayList;
    private ArrayList<CheckBox> checkBoxes;
    private ArrayList<Spinner> spinnerArrayList;
    private LinearLayout linearLayoutcheck;
    private  CheckBox checkBox;
    private Spinner spinner;
    private LinearLayout linearLayout;
    private List<Integer> exstranum;
    private List<Integer> snipperitem;
    private ArrayAdapter<Integer> adapter;
    private Button apply;
    private int snipperselectd;
    private RecyclerView recyclerView;
    private Extraadapter extraadapter;
    private float exprice;
    private TextView pricetext;
    private ArrayList<String> checkcontent;
    private boolean valid;
    private int exnum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int w=dm.widthPixels;
        int h=dm.heightPixels;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayoutcheck=findViewById(R.id.title);
        pricetext=findViewById(R.id.price);
        extras= (ArrayList<Extra>) getIntent().getSerializableExtra("exstra");
        exstranum=  getIntent().getIntegerArrayListExtra("extranum");
        valid=getIntent().getBooleanExtra("exvalid",true);
        extraadapter=new Extraadapter(this ,extras,exstranum.size());
        recyclerView=findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(extraadapter);
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //getWindow().setLayout((int)(w * .8),(int)(h * .6));
        checkBoxArrayList=new ArrayList<>();
        checkBoxes=new ArrayList<>();
        spinnerArrayList=new ArrayList<>();
        snipperselectd=exstranum.size();
        checkcontent=new ArrayList<>();
        adapter = new ArrayAdapter<Integer>(this,R.layout.spinneritem,exstranum);
       // addView();
        apply=findViewById(R.id.apply);
       apply.setOnClickListener(this);

    }

    /////////////////////////////////////////////////////////
public void addView()
{
    for(int i=0;i<extras.size();i++) {
        checkBox=new CheckBox(this);
        spinner=new Spinner(this);
        linearLayout=new LinearLayout(this);
        getLinearLayout(linearLayout);
        checkBox.setId(i);
        spinner.setId(i);
        checkBox.setText(extras.get(i).getName()+" " + extras.get(i).getPrice()+"ج.م");
        checkBox.setContentDescription(extras.get(i).getPrice());
        checkBox.setChecked(false);
        getCheckBox(checkBox);
        spinner.setDrawingCacheBackgroundColor(Color.BLACK);
        //getSpinner(spinner);
        linearLayout.addView(checkBox);
        linearLayout.addView(spinner);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    adapter = new ArrayAdapter<Integer>(getApplicationContext(),R.layout.spinneritem,exstranum);
                    spinnerArrayList.get(buttonView.getId()).setAdapter(adapter);
                    spinnerArrayList.get(buttonView.getId()).isShown();

                }
                else{
                    //adapter.clear();
                    spinnerArrayList.get(buttonView.getId()).setAdapter(null);
                }
            }
        });
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               int selected=Integer.parseInt(parent.getSelectedItem().toString());
              if(selected<snipperselectd)
              {
                  parent.setSelected(true);
                  selected=selected+selected;
              }

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        checkBoxArrayList.add(checkBox);
        spinnerArrayList.add(spinner);
        linearLayoutcheck.addView(linearLayout);
        //linearLayoutspin.addView(spinner);
    }
}
    public CheckBox getCheckBox(CheckBox checkBox)
    {
       // checkBox.setHeight(50);

        return checkBox;
    }
    public Spinner getSpinner(Spinner spinner)
    {

        return spinner;
    }
    public LinearLayout getLinearLayout(LinearLayout linearLayout)
    {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.apply) {
            String x = "الاضافات";
            checkBoxes = extraadapter.getcheckbox();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isChecked() && Integer.parseInt(checkBoxes.get(i).getContentDescription().toString()) > 0) {
                    x = x + " - " + checkBoxes.get(i).getContentDescription() + " " + checkBoxes.get(i).getText();
                    valid=true;

                }
            }
            if (valid) {

                exprice = Float.parseFloat(extraadapter.getexprice().toString());
                pricetext.setText(String.valueOf(exprice));
                Intent returnIntent = new Intent();
                returnIntent.putExtra("checkbox", x);
                returnIntent.putExtra("price", exprice);
                returnIntent.putExtra("exvalid",valid);
                setResult(this.RESULT_OK, returnIntent);
                finish();

            }
            else
            {apply.setText("يجب ان تختار نوع الاكله");
            apply.setTextColor(Color.RED);}
        }
    }
}
