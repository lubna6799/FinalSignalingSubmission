package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Models.Favs;
import com.example.myapplication.app.AppConfig;
import com.example.myapplication.app.AppController;
import com.example.myapplication.helper.SQLiteHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavsActivity extends AppCompatActivity implements favsAdapter.OnFavItemClicked {
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    ArrayList<Favs> arrayList ;

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private SQLiteHandler db;

    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new SQLiteHandler(getApplicationContext());
        getFavs();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    private void getFavs() {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        System.out.println("tststststs");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String uid = pref.getString("uid", 1+""); // getting String
        System.out.println("IDDDD "+uid);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GetFavs, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "favs Register Response: " + response.toString());


                try {

                    Log.d(TAG, "The favs retrieved sting" + response);
                    JSONArray jObj = new JSONArray(response);
                    arrayList = new ArrayList<Favs>();
                    for (int i = 0; i < jObj.length(); i++) {


                        JSONObject favsItem = (JSONObject) jObj.get(i);

                        arrayList.add(new Favs(Integer.parseInt(favsItem.getString("id")),Integer.parseInt(favsItem.getString("user_id")), Integer.parseInt(favsItem.getString("products")), Integer.parseInt(favsItem.getString("shops")), favsItem.getString("product_name"), favsItem.getString("shop_name"), favsItem.getString("price")));





                    }
                    mRecyclerView = findViewById(R.id.favsRecycler);
                    mRecyclerView.setHasFixedSize(true);
                    //mLayoutManager =new GridLayoutManager(MainActivity.this, 2) ;
                    mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    mAdapter = new favsAdapter(arrayList , FavsActivity.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "spinner stopped bec" + e.getMessage()) ;
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "favs Error: " + error.toString());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", uid);


                return params;
            }

        };

        // Adding request to request queue
        Log.d(TAG, (AppController.getInstance()==null)?"Favs Act It is null":"Favs Act not null") ;

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    @Override
    public void OnFavItemClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}