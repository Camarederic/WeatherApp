package com.camarederic.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText textInputEditTextEnter;
    private Button buttonSearchWeather;
    private TextView textViewCity;
    private TextView textViewDate;
    private TextView textViewTemperature;
    private TextView textViewDescription;
    private TextView textViewFeelsLike;
    private TextView textViewMinimum;
    private TextView textViewMaximum;
    private ImageView imageViewIcon;


    private TextView textViewSunrise;
    private TextView textViewSunset;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private TextView textViewHumidity;
    private TextView textViewPressure;
    private TextView textViewWindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        textInputEditTextEnter = findViewById( R.id.textInputEditTextEnter );
        textViewCity = findViewById( R.id.textViewCity );
        textViewDate = findViewById( R.id.textViewDate );
        textViewTemperature = findViewById( R.id.textViewTemperature );
        textViewDescription = findViewById( R.id.textViewDescription );
        textViewFeelsLike = findViewById( R.id.textViewFeelsLike );
        textViewMinimum = findViewById( R.id.textViewMinimum );
        textViewMaximum = findViewById( R.id.textViewMaximum );
        buttonSearchWeather = findViewById( R.id.buttonSearchWeather );
        textViewSunrise = findViewById( R.id.textViewSunrise );
        textViewSunset = findViewById( R.id.textViewSunset );
        textViewLongitude = findViewById( R.id.textViewLongitude );
        textViewLatitude = findViewById( R.id.textViewLatitude );
        textViewHumidity = findViewById( R.id.textViewHumidity );
        textViewPressure = findViewById( R.id.textViewPressure );
        textViewWindSpeed = findViewById( R.id.textViewWindSpeed );
        imageViewIcon = findViewById( R.id.imageViewIcon );




        buttonSearchWeather.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findWeather();

            }
        } );
    }



    public void findWeather() {

        String city = textInputEditTextEnter.getText().toString();

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=4867d656104b277d0dfd2f3555c70051&units=metric";
        StringRequest request = new StringRequest( Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String findCity = jsonObject.getString("name");
                    textViewCity.setText( findCity );

                    JSONObject object = jsonObject.getJSONObject( "main" );
                    int findTemperature = object.getInt( "temp" );
                    textViewTemperature.setText( findTemperature + " ℃" );

                    JSONArray jsonArray1 = jsonObject.getJSONArray( "weather" );
                    JSONObject object2 = jsonArray1.getJSONObject( 0 );
                    String findDescription = object2.getString( "description" );
                    textViewDescription.setText( findDescription );

                    JSONObject object3 = jsonObject.getJSONObject( "main" );
                    int findFeelsLike = object3.getInt( "feels_like" );
                    textViewFeelsLike.setText( findFeelsLike + " ℃" );

                    JSONObject object4 = jsonObject.getJSONObject( "main" );
                    int findTempMin = object4.getInt( "temp_min" );
                    textViewMinimum.setText( findTempMin + " ℃" );

                    JSONObject object5 = jsonObject.getJSONObject( "main" );
                    int findTempMax = object5.getInt( "temp_max" );
                    textViewMaximum.setText( findTempMax + " ℃" );

                    JSONObject object6 = jsonObject.getJSONObject( "sys" );
                    String findSunrise = object6.getString( "sunrise" );
                    textViewSunrise.setText( findSunrise );

                    JSONObject object7 = jsonObject.getJSONObject( "sys" );
                    String findSunset = object7.getString( "sunset" );
                    textViewSunset.setText( findSunset );

                    JSONObject object8 = jsonObject.getJSONObject( "coord" );
                    String findLongitude = object8.getString( "lon" );
                    textViewLongitude.setText( findLongitude + "° E" );

                    JSONObject object9 = jsonObject.getJSONObject( "coord" );
                    String findLatitude = object9.getString( "lat" );
                    textViewLatitude.setText( findLatitude + "° N" );

                    JSONObject object10 = jsonObject.getJSONObject( "main" );
                    String findHumidity = object10.getString( "humidity" );
                    textViewHumidity.setText( findHumidity + " %" );

                    JSONObject object11 = jsonObject.getJSONObject( "main" );
                    String findPressure = object11.getString( "pressure" );
                    textViewPressure.setText( findPressure + " hRa" );

                    JSONObject object12 = jsonObject.getJSONObject( "wind" );
                    String findWindSpeed = object12.getString( "speed" );
                    textViewWindSpeed.setText( findWindSpeed + " km/h" );

                    imageViewIcon.setVisibility( View.VISIBLE );
                    JSONArray jsonArray2 = jsonObject.getJSONArray( "weather" );
                    JSONObject object13 = jsonArray2.getJSONObject( 0 );
                    String findIcon = object13.getString( "icon" );
                    Picasso.get().load( "https://openweathermap.org/img/wn/" + findIcon + "@4x.png" ).into( imageViewIcon );

                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  dd/MM/yyyy");
                    String date = simpleDateFormat.format( calendar.getTime() );
                    textViewDate.setText( date );

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "There is no such city", Toast.LENGTH_LONG ).show();
            }
        } );
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add( request );

    }
}