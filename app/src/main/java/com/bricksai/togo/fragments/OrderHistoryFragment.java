package com.bricksai.togo.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bricksai.togo.R;
import com.bricksai.togo.ServerUrl;
import com.bricksai.togo.adapters.MenuAdapter;
import com.bricksai.togo.adapters.OrderHistoryAdapter;
import com.bricksai.togo.models.OrderHistoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Remonda on 5/4/2017.
 */

public class OrderHistoryFragment extends Fragment{
    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recycler,container,false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new AsyncTask<String,String,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {
                String url = ServerUrl.url+"orderhistory.php?userid=58";
                String result="a7eh ba2a ana zeh2t yabn el teeeeeeeeeet";
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
                Log.d("debugJSON", s);
                ArrayList<OrderHistoryModel>orderHistoryModels=new ArrayList<OrderHistoryModel>();
                JSONObject jsonObject;
                try {
                    jsonObject=new JSONObject(s);
                    JSONArray jsonArray=jsonObject.optJSONArray("order_history");
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        final String id = jsonObject.optString("order_id");
                        final String price = jsonObject.optString("total_money");
                        final String time = jsonObject.optString("time");
                        orderHistoryModels.add(new OrderHistoryModel(id,price,time));
                    }
            } catch (JSONException e) {
                    e.printStackTrace();
                }
                OrderHistoryAdapter orderHistoryAdapter= new OrderHistoryAdapter(orderHistoryModels);
                recyclerView.setAdapter(orderHistoryAdapter);
                progressDialog.dismiss();
            }
            }.execute();
        return view;
    }
}
