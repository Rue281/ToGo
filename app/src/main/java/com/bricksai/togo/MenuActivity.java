package com.bricksai.togo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bricksai.togo.adapters.MenuAdapter;
import com.bricksai.togo.fragments.MapsFragment;
import com.bricksai.togo.models.MenuModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Remonda on 5/16/2017.
 */

public class MenuActivity extends AppCompatActivity
{
    final ArrayList<MenuModel> menuModels=new ArrayList<>();
    ProgressDialog progressDialog;
    FloatingActionButton fab;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView txtTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycler);
        txtTotal=(TextView) findViewById(R.id.txtTotal);


        preferences = getApplicationContext().getSharedPreferences("order", MODE_PRIVATE);
        editor = preferences.edit();

        txtTotal.setText(preferences.getString("total", null));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(MenuActivity.this,"total is "+preferences.getString("total", null), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MenuActivity.this, OrderSummaryActivity.class);
                startActivity(intent);
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MenuActivity.this));

        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(MenuActivity.this, R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {
                Bundle bundle = getIntent().getExtras();
                String id=bundle.getString("id");
                String url= ServerUrl.url1+"menu.php?id="+id;
                String result="";
                try {
                    URL url1=new URL(url);
                    HttpURLConnection connection=(HttpURLConnection)url1.openConnection();
                    InputStreamReader input=new InputStreamReader(connection.getInputStream());
                    BufferedReader buffer= new BufferedReader(input);
                    result=buffer.readLine();
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
                    jsonObject=new JSONObject(s);
                    Log.e("debugJSON", s);
                    JSONArray jsonArray=jsonObject.optJSONArray("menu");
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        final String id = jsonObject.optString("id");
                        final String name = jsonObject.optString("name");
                        final String price = jsonObject.optString("price");
                        final String img = jsonObject.optString("img");
                        menuModels.add(new MenuModel(Integer.parseInt(id),name,Double.parseDouble(price),img, ""));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MenuAdapter menuAdapter = new MenuAdapter(menuModels);
                recyclerView.setAdapter(menuAdapter);
                progressDialog.dismiss();
            }
        }.execute();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MenuActivity.this,recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(MenuActivity.this, ItemDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("ItemId",String.valueOf(menuModels.get(position).getId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }
}
