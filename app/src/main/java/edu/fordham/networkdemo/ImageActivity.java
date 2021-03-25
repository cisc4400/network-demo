package edu.fordham.networkdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

public class ImageActivity extends AppCompatActivity {
    RequestQueue queue;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        queue = Volley.newRequestQueue(this);
        imageView = findViewById(R.id.imageView);
    }

    public void downloadImage(View view) {
        String url = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/db/Android_robot_2014.svg/227px-Android_robot_2014.svg.png";

        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.i("responded",  response.toString());
                        imageView.setImageBitmap(response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.FIT_CENTER,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Response status code: " + error.networkResponse.statusCode,
                                Toast.LENGTH_LONG).show();
                    }
                });

        queue.add(request);
    }
}