package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnSearchWeather;
    EditText et_dataInput;
    TextView cityName;
    LinearLayout layout;
    private RecyclerView weatherRecView;
    private WeatherRecViewAdapter weatherRecViewAdapter;
    private RelativeLayout childRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        btnSearchWeather = findViewById(R.id.btnSearchWeather);

        cityName = findViewById(R.id.cityName);

        layout = findViewById(R.id.layout);

        et_dataInput = findViewById(R.id.et_dataInput);

        weatherRecView = findViewById(R.id.lv_weatherReports);

        childRelativeLayout = findViewById(R.id.childRelativeLayout);

        childRelativeLayout.setVisibility(View.INVISIBLE);

        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        cityName.setText("Delhi");

        ProgressBar pb = findViewById(R.id.indeterminateBar);
        pb.setVisibility(View.VISIBLE);

        weatherDataService.getCityForecastByName("Delhi", new WeatherDataService.GetCityForecastByNameCallback() {
            @Override
            public void onError(String message) {
                Toast.makeText(MainActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(List<WeatherReportModel> weatherReportModel) {

                pb.setVisibility(View.INVISIBLE);
                childRelativeLayout.setVisibility(View.VISIBLE);
                weatherRecViewAdapter = new WeatherRecViewAdapter(MainActivity.this,weatherReportModel);
                weatherRecView.setAdapter(weatherRecViewAdapter);
                weatherRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });

        // Click Listeners

        btnSearchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                childRelativeLayout.setVisibility(View.INVISIBLE);
                cityName.setText(et_dataInput.getText().toString());
                weatherDataService.getCityForecastByName(et_dataInput.getText().toString(), new WeatherDataService.GetCityForecastByNameCallback() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModel) {

                        pb.setVisibility(View.INVISIBLE);
                        childRelativeLayout.setVisibility(View.VISIBLE);
                        weatherRecViewAdapter = new WeatherRecViewAdapter(MainActivity.this,weatherReportModel);
                        weatherRecView.setAdapter(weatherRecViewAdapter);
                        weatherRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.search){
            if(layout.getVisibility() == View.VISIBLE)
                layout.setVisibility(View.GONE);
            else
                layout.setVisibility(View.VISIBLE);
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}