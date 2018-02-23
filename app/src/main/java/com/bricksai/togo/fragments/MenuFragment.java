package com.bricksai.togo.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bricksai.togo.ItemDetailsActivity;
import com.bricksai.togo.R;
import com.bricksai.togo.RecyclerItemClickListener;
import com.bricksai.togo.ServerUrl;
import com.bricksai.togo.adapters.MenuAdapter;
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
 * Created by Remonda on 5/3/2017.
 */

public class MenuFragment extends Fragment{
    ProgressDialog progressDialog;
    final ArrayList<MenuModel> menuModels=new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_recycler,container,false);


        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new AsyncTask<String, String, String>() {
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
                String id=getArguments().getString("id");
                String url= ServerUrl.url+"menu.php?id="+id;
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), ItemDetailsActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("ItemId",String.valueOf(menuModels.get(position).getId()));
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        return view;
    }
}
