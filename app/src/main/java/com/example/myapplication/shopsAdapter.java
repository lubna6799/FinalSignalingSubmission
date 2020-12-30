package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Models.Favs;
import com.example.myapplication.Models.product;
import com.example.myapplication.Models.shopItem;
import com.example.myapplication.app.AppConfig;
import com.example.myapplication.app.AppController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class shopsAdapter extends RecyclerView.Adapter<shopsAdapter.shopsAdapterHolder>{
  ArrayList<shopItem> arr ;
  String productName ;
  String productId;
  OnShopItemClicked onShopItemClicked ;
  public interface OnShopItemClicked{
   void OnShopItemClick( int position) ;
  }
  public shopsAdapter(ArrayList<shopItem> arr, OnShopItemClicked clickListener,  String productName, String productId){
    this.arr = arr ;
    this.onShopItemClicked = clickListener ;
    this.productName = productName;
    this.productId = productId;
  }
  public static class shopsAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView TextView1 ;
    TextView TextView2 ;
    TextView TextView3 ;
    Button fav;
    OnShopItemClicked onShopItemClicked ;

    public shopsAdapterHolder(View itemView , OnShopItemClicked onShopItemClicked) {
    super(itemView) ;
      TextView1 = itemView.findViewById(R.id.T1) ;
      TextView2 = itemView.findViewById(R.id.T2) ;
      TextView3 = itemView.findViewById(R.id.T3) ;
      fav = itemView.findViewById(R.id.saveFavs);
      this.onShopItemClicked = onShopItemClicked ;
      itemView.setOnClickListener(this) ;
    }


    @Override
    public void onClick(View v) {
      onShopItemClicked.OnShopItemClick(getAdapterPosition());

    }
  }

  @NonNull
  @Override
  public shopsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopitem , parent,false);
    shopsAdapterHolder evh = new shopsAdapterHolder(v , onShopItemClicked) ;
    return evh ;
  }
  @Override
  public void  onBindViewHolder(@NonNull shopsAdapterHolder holder, int position) {
    shopItem currentItem = arr.get(position) ;
    holder.TextView1.setText(currentItem.getName());
    holder.TextView2.setText(String.valueOf(currentItem.getPrice()) + " L.E");
    holder.TextView3.setText(String.valueOf(currentItem.getDistance())+ " KM");

    holder.fav.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        System.out.print("LAYLAYLAY");

        Context context = holder.TextView1.getContext();

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String uid = pref.getString("uid", 1+"");
        System.out.print("UIDDD "+uid);
        saveFavs(uid, productId, currentItem.getUid(), productName ,currentItem.getName(), currentItem.getPrice()+"");
        }
      }
    );

  }


  private void saveFavs(String userId, String prodId, String shopId, String prodName, String shopName, String price) {
    // Tag used to cancel the request
    String tag_string_req = "req_register";
    System.out.println("tststststs");

    StringRequest strReq = new StringRequest(Request.Method.POST,
            AppConfig.URL_PostFavs, new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {
        Log.d("FAVS", "favs Register Response: " + response.toString());




      }
    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("SAVE", "favs Error: " + error.toString());
      }
    }) {

      @Override
      protected Map<String, String> getParams() {
        // Posting params to register url
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userId+"");
        params.put("products", prodId+"");
        params.put("shops", shopId+"");
        params.put("product_name", prodName+"");
        params.put("shop_name", shopName+"");
        params.put("price", price+"");


        return params;
      }

    };

    // Adding request to request queue
    Log.d("TAG", (AppController.getInstance()==null)?"Favs Act It is null":"Favs Act not null") ;


    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  }


  @Override
  public int getItemCount() {
    return arr.size();
  }
}
