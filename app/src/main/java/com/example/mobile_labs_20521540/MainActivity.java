package com.example.mobile_labs_20521540;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    EditText editName,editGross;
    Button btnCal;
    ListView lvNhanvien;
    ArrayList<Employee> arrEmployee=new ArrayList<Employee>();
    ArrayAdapter<Employee>adapter=null;
    Employee tEmployee=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName = (EditText) findViewById(R.id.fullName);
        editGross = (EditText) findViewById(R.id.grossSalary);
        btnCal = (Button) findViewById(R.id.buttonCal);
        lvNhanvien = (ListView) findViewById(R.id.lvEmp);
        adapter=new ArrayAdapter<Employee>(this,
                android.R.layout.simple_list_item_1,
                arrEmployee);
        lvNhanvien.setAdapter(adapter);
        btnCal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                processClick();
            }
        });
    }
    //Xử lý sự kiện nhập
    public void processClick()
    {
        String Name=editName.getText()+"";
        String strGross=editGross.getText()+"";
        double Gross = Double.parseDouble(strGross);
        tEmployee = new Employee(Name,Gross);
        tEmployee.setGross(Gross);
        double Net = tEmployee.TinhNet();
        tEmployee.setNet(Net);
        //Đưa employee vào ArrayList
        arrEmployee.add(tEmployee);
        //Cập nhập giao diện
        adapter.notifyDataSetChanged();
    }
}