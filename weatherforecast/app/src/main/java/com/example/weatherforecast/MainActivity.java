package com.example.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static com.example.weatherforecast.R.id.txtCity;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    List<String> forecastList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getWeather(View view) {
        // IMPORTANT: There are a few different ways to run the code on the background thread.
        // This method will show how to do it using Runnable objects directly.

        // Get the name of the city we are interested in
         EditText txtCity = findViewById(R.id.txtCity);
        String city = txtCity.getText().toString();

        Log.i("MainActivity","Getting weather for city: " + city);

        // Set up a new instance of our runnable object that will be run on the background thread
       // GetWeatherAsync getWeatherAsync = new GetWeatherAsync(this, city);
        GetWeatherAsync getWeatherAsync = new GetWeatherAsync(this, city);
        // Set up the thread that will use our runnable object
        Thread t = new Thread(getWeatherAsync);

        // starts the thread in the background. It will automatically call the run method of
        // the getWeatherAsync object we gave it earlier
        t.start();
    }

    /**
     * This function will get called (on the main UI thread) once we have successfully returned
     * from the API with results.
     * @param conditions The results of the API call. If an error occurred, this will be null.
     */
    void handleWeatherConditionsResult(WeatherConditions conditions) {
        Log.d("MainActivity", "Back from API on the UI thread with the weather results!");

        // Check for an error
        if (conditions == null) {
            Log.d("MainActivity", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the weather",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d("MainActivity", "Conditions: " + conditions.getMeasurements().toString());

            // Get the current temperature
            Float temp = conditions.getMeasurements().get("temp");

            // Show the temperature to the user
            Toast.makeText(this, "It is currently " + temp + " degrees.",
                    Toast.LENGTH_LONG).show();
        }
    }

    void handleWeatherForecastResult(WeatherForecast conditions) {
        Log.d("MainActivity", "Back from API on the UI thread with the weather results!");

        // Check for an error
        if (conditions == null) {
            Log.d("MainActivity", "API results were null");

            // Inform the user
            Toast.makeText(this, "An error occurred when retrieving the weather",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.d("MainActivity", "Conditions: " + conditions.getForecastItems().toString());

            // Get the current temperature
            String temp = String.valueOf(conditions.getForecastItems().get(2).getDescriptions());

            // Show the temperature to the user
            Toast.makeText(this, "It is the new data currently " + temp + " degrees.",
                    Toast.LENGTH_LONG).show();
        }
    }



}
