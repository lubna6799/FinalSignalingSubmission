package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Models.Favs;
import com.example.myapplication.Models.shopItem;
import com.example.myapplication.app.AppConfig;
import com.example.myapplication.app.AppController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class favsAdapter extends RecyclerView.Adapter<favsAdapter.shopsAdapterHolder>{
  ArrayList<Favs> arr ;
  private favsAdapter adapter;

  OnFavItemClicked onFavItemClicked ;
  public interface OnFavItemClicked{
   void OnFavItemClick( int position) ;
  }
  public favsAdapter(ArrayList<Favs> arr , OnFavItemClicked clickListener  ){
    this.arr = arr ;
    this.onFavItemClicked = clickListener ;
    this.adapter = this; //This is an important line, you need this line to keep track the adapter variable

  }
  public static class shopsAdapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView TextView1 ;
    TextView TextView2 ;
    TextView productName ;
    TextView shopName ;
    TextView price ;
    Button deleteFav;
    OnFavItemClicked onFavItemClicked ;

    public shopsAdapterHolder(View itemView , OnFavItemClicked onFavItemClicked) {
    super(itemView) ;
      productName = itemView.findViewById(R.id.fav_prod_name);
      shopName = itemView.findViewById(R.id.fav_prod_price);
      price = itemView.findViewById(R.id.fav_prod_shop);
      TextView1 = itemView.findViewById(R.id.product_id) ;
      TextView2 = itemView.findViewById(R.id.shop_id) ;
      deleteFav = itemView.findViewById(R.id.deleteFav);
      this.onFavItemClicked = onFavItemClicked;
      itemView.setOnClickListener(this) ;
    }


    @Override
    public void onClick(View v) {

      onFavItemClicked.OnFavItemClick(getAdapterPosition());

    }
  }

  @NonNull
  @Override
  public shopsAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favs_item , parent,false);
    shopsAdapterHolder evh = new shopsAdapterHolder(v , onFavItemClicked) ;
    return evh ;
  }
  @Override
  public void  onBindViewHolder(@NonNull shopsAdapterHolder holder, int position) {
    Favs currentItem = arr.get(position) ;
    holder.TextView1.setText(String.valueOf("Product ID:"+currentItem.getProducts()) );
    holder.TextView2.setText(String.valueOf("Shop ID:"+currentItem.getShops()));
  holder.productName.setText(String.valueOf(currentItem.getProduct_name()));
  holder.shopName.setText(String.valueOf(currentItem.getShop_name()));
  holder.price.setText(String.valueOf(currentItem.getPrice()));
  holder.deleteFav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      deleteFav(currentItem.getId()+"");
      arr.remove(currentItem); //Actually change your list of items here
adapter.notifyDataSetChanged();

    }
  });

  }

  private void deleteFav(String itemId) {
    // Tag used to cancel the request
    String tag_string_req = "req_register";
    System.out.println("tststststs");

    StringRequest strReq = new StringRequest(Request.Method.POST,
            AppConfig.URL_DeleteFavs, new Response.Listener<String>() {

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
        params.put("item_id", itemId+"");

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
