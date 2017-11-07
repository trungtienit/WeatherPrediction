package com.example.trantien.myweather.weather.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.trantien.myweather.R;

import butterknife.BindView;

import static com.example.trantien.myweather.Common.toast;

/**
 * Created by trantien on 11/5/17.
 */

public class ChooseAddressActivity extends Activity implements View.OnClickListener {
    AutoCompleteTextView txtAddress;

    ListView lvAddress;

    String[] arrAddress;

    Intent intent;

    ArrayAdapter<String> arrAdapter;

    @BindView(R.id.btnCheckWeather)
    Button btnCheckWeather;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);

        init();
    }

    public void init() {
        txtAddress = findViewById(R.id.txtAddressCheck);
        lvAddress = (ListView) findViewById(R.id.lvAddress);
        arrAddress = getResources().getStringArray(R.array.arr_city_province);
        arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrAddress);
        lvAddress.setAdapter(arrAdapter);
        btnCheckWeather = (Button) findViewById(R.id.btnCheckWeather);
        txtAddress.setAdapter(arrAdapter);

        btnCheckWeather.setOnClickListener(this);
        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast(getBaseContext(), arrAdapter.getItem(position));
                intent = new Intent(ChooseAddressActivity.this, WeatherByAddressActivity.class);
                intent.putExtra("ADDRESS", arrAdapter.getItem(position));
                startActivity(intent);
            }
        });

        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                arrAdapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnCheckWeather) {
            intent = new Intent(ChooseAddressActivity.this, WeatherByAddressActivity.class);
            //truyền địa chỉ qua bên kia để xử lý
            intent.putExtra("ADDRESS", txtAddress.getText() + "");
            startActivity(intent);
        }
    }
}
