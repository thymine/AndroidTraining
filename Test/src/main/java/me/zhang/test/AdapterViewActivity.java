package me.zhang.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhang on 2016/5/6 下午 4:42 .
 */
public class AdapterViewActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    Spinner spinner0;

    @BindView(R.id.text)
    TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_view);

        ButterKnife.bind(this);

        final String[] country = new String[]{"Select Country", "Australia", "USA", "UK", "New Zealand", "EU", "Europe", "China", "Hong Kong",
                "India", "Malaysia", "Canada", "International", "Asia", "Africa"};

        ArrayAdapter<String> adapter0 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, country);
        adapter0.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner0.setAdapter(adapter0);

        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                text.setText(country[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
