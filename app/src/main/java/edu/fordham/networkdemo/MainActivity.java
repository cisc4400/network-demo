package edu.fordham.networkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RequestQueue queue;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        queue = Volley.newRequestQueue(this);
    }

    public void startImageActivity(View view) {
        Intent intent = new Intent(this, ImageActivity.class);
        startActivity(intent);
    }

    public void sendRequest(View view) {
        // From: https://developer.android.com/training/volley/simple
        String url = "https://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void requestWeatherJSON(View view) {
        String url = "https://api.weather.gov/alerts/active?area=NY";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("JSON response", response.toString());
                        String text = "";
                        try {
                            text += response.get("title") + "\n\n";
                            JSONArray arr = response.getJSONArray("features");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                JSONObject properties = obj.getJSONObject("properties");
                                text += properties.getString("headline") + "\n\n";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        textView.setText(text);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent", "Demo weather alert app");
                return headers;
            }
        };

        queue.add(jsonObjectRequest);
    }
}