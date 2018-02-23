package com.bricksai.togo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bricksai.togo.utiles.ImageDownloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Remonda on 5/8/2017.
 */

public class ItemDetailsActivity extends AppCompatActivity{
    String id;
    ImageView imgItem,imgIncrease;
    TextView txtName,txtPrice,txtDescription,txtQuantity;
    int counter;
    ProgressDialog progressDialog;
    ToggleButton toggleButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String price;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        preferences = getApplicationContext().getSharedPreferences("order", MODE_PRIVATE);
        editor = preferences.edit();

        toggleButton = (ToggleButton) findViewById(R.id.cartToggleButton);

        this.counter=1;
        id = getIntent().getStringExtra("ItemId");

        imgItem =(ImageView) findViewById(R.id.poster);
        txtName=(TextView) findViewById(R.id.txtItemDetailsName);
        txtPrice=(TextView)findViewById(R.id.txtitemDetailsPrice);
        txtDescription=(TextView)findViewById(R.id.txtItemDetailsDescription);
        txtQuantity=(TextView)findViewById(R.id.txtQuantity);
        imgIncrease=(ImageView)findViewById(R.id.lk);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(), String.valueOf(counter), Toast.LENGTH_LONG).show();
                String quantity, itemId, itemName, total, itemCounter;
                quantity = String.valueOf(counter);
                itemId = String.valueOf(id);
                itemName = txtName.getText().toString();
                itemCounter = String.valueOf(Integer.parseInt(preferences.getString("itemCounter", "0"))+1);
                total = String.valueOf(Integer.parseInt(preferences.getString("total", "0"))+Integer.parseInt(quantity));
                editor.putString("total", total);
                editor.putString("itemCounter", itemCounter);
                editor.putString("item_"+itemCounter, itemId);
                editor.putString("itemP_"+itemCounter, price);
                editor.putString("itemN_"+itemCounter, itemName);
                editor.putString("itemQ_"+itemCounter, quantity);
                editor.commit();

                //Toast.makeText(ItemDetailsActivity.this,"total is "+total,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ItemDetailsActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        imgIncrease.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return false;
//            }
//        });
//        imgIncrease.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("touch","we Are Here");
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                    Log.e("touch","yes");
//                    Toast.makeText(ItemDetailsActivity.this, "have a click", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                else if(event.getAction() == MotionEvent.ACTION_UP){
//                    Log.e("touch","no");
//                    Toast.makeText(ItemDetailsActivity.this, "noClick", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                return false;
//
//            }
//        });



        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(ItemDetailsActivity.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {
                String url = ServerUrl.url1 + "get_item_details.php?item_id="+id;
                String result = "";
                try {
                    URL url1 = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                    InputStreamReader input = new InputStreamReader(connection.getInputStream());
                    BufferedReader buffer = new BufferedReader(input);
                    result = buffer.readLine();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(s);
                    Log.e("debugJSON", s);
                    JSONArray jsonArray = jsonObject.optJSONArray("item");

                        jsonObject = jsonArray.getJSONObject(0);
                        final String id = jsonObject.optString("id");
                        final String name = jsonObject.optString("name");
                        price = jsonObject.optString("price");
                        final String img = jsonObject.optString("img");
                        final String description=jsonObject.optString("descreption");
                    txtName.setText(name);
                    txtDescription.setText(description);
                    txtPrice.setText(price);
                    new AsyncTask<String, String, Bitmap>() {
                        @Override
                        protected Bitmap doInBackground(String... params) {
                            ImageDownloader imageDownloader=new ImageDownloader();

                            return imageDownloader.downloadImage(img);
                        }

                        @Override
                        protected void onPostExecute(Bitmap bitmap) {
                            super.onPostExecute(bitmap);
                            imgItem.setImageBitmap(bitmap);
                            progressDialog.dismiss();
                        }
                    }.execute();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();
    }
    public void removeOne(View v){

        if (counter ==1)
        {

        }else
        {
            counter = counter - 1;
            txtQuantity.setText(String.valueOf(counter));
        }

    }

    public void addOne (View view)
    {
        counter = counter + 1;
        txtQuantity.setText(String.valueOf(counter));
    }

}
