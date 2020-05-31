package com.example.weatherforecast;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class GetWeatherAsync implements Runnable {
    private MainActivity activity;
    private String city;


    /**
     * Sets up the runnable to be called. It needs the MainActivity so it can run code on the
     * UI thread, and also the city so that it can get its weather conditions.
     *
     * @param activity
     * @param city
     */

    public GetWeatherAsync(MainActivity activity, String city) {
        this.activity = activity;
        this.city = city;

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void run() {
        // This is the function that will be run on the background thread.
        WeatherDataLoader loader = new WeatherDataLoader();
        // Now, call the function that will get the results from the API and then when it is done,
        // it will call the "handleResult" function on this new WeatherConditionsResultHandler
        // object that we are giving it.
        loader.getWeatherAndPostResults(city, new WeatherConditionsResultHandler() {
            @Override
            public void handleResult(final com.example.weatherforecast.WeatherConditions conditions) {
                Log.d("GetWeatherAsync", "Back from API, but still on background thread.");
                // At this point we will be back from the API with the results stored in `conditions`
                // Next, we need to run the function that will update the UI elements, but this
                // must be run on the UI thread
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // This is code that will now run on the UI thread. Call the function in
                        // MainActivity that will update the UI correctly.
                        activity.handleWeatherConditionsResult(conditions);
                    }
                });
            }
        });
    }


    /**  @Override public void run() {
    // This is the function that will be run on the background thread.
    WeatherDataLoader loader = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
    loader = new WeatherDataLoader();
    }

    // Now, call the function that will get the results from the API and then when it is done,
    // it will call the "handleResult" function on this new WeatherConditionsResultHandler
    // object that we are giving it.

    final WeatherConditions conditions = loader.getWeatherAndPostResults(city);
    activity.runOnUiThread(new Runnable() {
    @Override public void run() {
    // This is code that will now run on the UI thread. Call the function in
    // MainActivity that will update the UI correctly.
    activity.handleWeatherConditionsResult(conditions);
    }
    });
    }
     @Override public void run() {
     // This is the function that will be run on the background thread.
     WeatherDataLoader loader = null;
     if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
     loader = new WeatherDataLoader();
     }

     // Now, call the function that will get the results from the API and then when it is done,
     // it will call the "handleResult" function on this new WeatherConditionsResultHandler
     // object that we are giving it.



     /*final WeatherForecast conditions = loader.getWeatherAndPostResults(city, new WeatherConditionsResultHandler()

     public void handleResult(final com.example.weatherforecast.WeatherConditions conditions);
     WeatherForecast forecast;
     loader.getForecastAndPostResults(city, new WeatherForecastResultHandler()activity.handleWeatherForecastResult(forecast);

     activity.runOnUiThread(new Runnable() {
     @Override public void run() {
     // This is code that will now run on the UI thread. Call the function in
     // MainActivity that will update the UI correctly.
     WeatherForecast conditions;
     activity.handleWeatherForecastResult(conditions);
     }
     });
     }


     I understand that the call defines the way the method public WeatherForecast getWeatherAndPostResults(String city, WeatherConditionsResultHandler handler)
     manage the API call and return the forecast, unfortunatelly cannot call it without having a error as the handler parameter must
     be instatiated before call.*/


}

