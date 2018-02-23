package com.bricksai.togo.fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bricksai.togo.MapWrapperLayout;
import com.bricksai.togo.MenuActivity;
import com.bricksai.togo.R;
import com.bricksai.togo.ServerUrl;
import com.bricksai.togo.models.RestaurantModel;
import com.bricksai.togo.utiles.ImageDownloader;
import com.bricksai.togo.utiles.OnInfoWindowElemTouchListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

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

import static android.content.Context.MODE_PRIVATE;
import static com.bricksai.togo.R.id.map;

/**
 * Created by Mahmoud Emad on 4/16/2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    ViewGroup infoWindow;
    private Button infoButton;
    private TextView infoTitle;
    private TextView txtName,txtOpeningTime,txtClosingTime;
    ImageView imgRestaurant;

    GoogleMap googleMap;
    View remonaKarsa;
    private OnInfoWindowElemTouchListener infoButtonListener;
    LocationManager locationManager;
    SharedPreferences preferences;
    ProgressDialog progressDialog;
    final ArrayList<RestaurantModel> restaurantModels =new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        preferences = getActivity().getSharedPreferences("remonda", MODE_PRIVATE);
        remonaKarsa = view;
        MapFragment mapFragment = (MapFragment) this.getChildFragmentManager().findFragmentById(R.id.fragmentMap);
        mapFragment.getMapAsync(this);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//        try {
//            Context context = getActivity().getApplicationContext();
//            MapsInitializer.initialize(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//                googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
//                MapsFragment.this.googleMap = googleMap;
//                final double[] x = new double[1];
//                final double[] y = new double[1];
//                x[0] = 30.0470591;
//                y[0] = 31.2345761;
//                LatLng location = new LatLng(x[0], y[0]);
//                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_resturant);
//                googleMap.addMarker(new MarkerOptions().position(location).title("Cook Door").icon(icon).snippet("HElloooooo Mahmoud"));
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 17));
//
//
//                final double[] xx = new double[1];
//                final double[] yy = new double[1];
//                xx[0] = 30.0481691;
//                yy[0] = 31.2345761;
//                LatLng location1 = new LatLng(xx[0], yy[0]);
//                BitmapDescriptor icon1 = BitmapDescriptorFactory.fromResource(R.mipmap.ic_caffe);
//                googleMap.addMarker(new MarkerOptions().position(location1).title("Costa Cafe").icon(icon1));
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1, 17));
//
//                final double[] xxx = new double[1];
//                final double[] yyy = new double[1];
//                xxx[0] = 30.0481691;
//                yyy[0] = 31.2365761;
//                LatLng location2 = new LatLng(xxx[0], yyy[0]);
//                googleMap.addMarker(new MarkerOptions().position(location2).title("BoB Cafe Shop").icon(icon1));
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location2, 17));
//            }
//        });
        return view;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap = map;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return null;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, MapsFragment.this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);


        //final MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)remonaKarsa.findViewById(R.id.map_relative_layout);
        //final GoogleMap map = mapFragment.getMap();

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(map, getPixelsFromDp(getActivity(),39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.infoWindow = (ViewGroup)inflater.inflate(R.layout.info_window_layout, null);
        this.infoTitle = (TextView)infoWindow.findViewById(R.id.txtname);
        this.txtOpeningTime=(TextView)infoWindow.findViewById(R.id.txtOpeningTime);
        this.txtClosingTime=(TextView)infoWindow.findViewById(R.id.txtClosingTime);
        this.imgRestaurant=(ImageView)infoWindow.findViewById(R.id.imgRestaurant);
        this.infoButton = (Button)infoWindow.findViewById(R.id.clinicType);
        //this.infoSnippet = (TextView)infoWindow.findViewById(R.id.addressTv);
        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                getResources().getDrawable(R.drawable.round_but_green_sel),
                getResources().getDrawable(R.drawable.round_but_red_sel))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                //Toast.makeText(getActivity(), marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();
                String restId = marker.getSnippet();
//                final FragmentManager fm= getFragmentManager();
//                final android.app.Fragment fragment=fm.findFragmentById(R.id.frame);
                //MenuFragment menu = new MenuFragment();
                Intent intent = new Intent(getActivity(),MenuActivity.class);
                intent.putExtra("id", restId);
                startActivity(intent);
                //menu.setArguments(bundle);
//                if(fragment != null && fragment.isVisible()){
//                    getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
//                    getFragmentManager().beginTransaction().add(R.id.frame, menu).commit();
//                }
//                else{
//                    getFragmentManager().beginTransaction().add(R.id.frame, menu).commit();
//                }

                Toast.makeText(getActivity(), restId, Toast.LENGTH_LONG).show();
            }
        };
        this.infoButton.setOnTouchListener(infoButtonListener);


        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                txtOpeningTime.setText(restaurantModels.get(0).getOpen_time());
                txtClosingTime.setText(restaurantModels.get(0).getClose_time());
                // infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout

                final ImageDownloader imageDownloader=new ImageDownloader();
                new AsyncTask<String, String, Bitmap>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
//                        progressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
//                        progressDialog.setIndeterminate(true);
//                        progressDialog.setMessage("Downloading Restaurants...");
//                        progressDialog.setCancelable(false);
//                        progressDialog.show();
                    }

                    @Override
                    protected Bitmap doInBackground(String... params) {
                        String url=restaurantModels.get(1).getImg();
                        Bitmap image=imageDownloader.downloadImage(url);

                        return image;
                    }

                    @Override
                    protected void onPostExecute(Bitmap s) {
                        super.onPostExecute(s);
                        imgRestaurant.setImageBitmap(s);
                        progressDialog.dismiss();
                    }
                }.execute();
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);

                return infoWindow;
            }
        });

        // Let's add a couple of markers

//        for (int i=0;i<restaurantModels.size();i++){
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
                String url= ServerUrl.url1+"resturant_json.php";
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
                    JSONArray jsonArray=jsonObject.optJSONArray("resturant");
                    for (int i=0;i<jsonArray.length();i++){
                        jsonObject = jsonArray.getJSONObject(i);
                        final String id = jsonObject.optString("id");
                        final String name = jsonObject.optString("name");
                        final String phone = jsonObject.optString("phone");
                        final String email = jsonObject.optString("email");
                        final String city = jsonObject.optString("city");
                        final String address = jsonObject.optString("address");
                        final String img = jsonObject.optString("img");
                        final String type = jsonObject.optString("class");
                        final String x = jsonObject.optString("main_location_latitude");
                        final String y = jsonObject.optString("main_location_longitude");
                        final String open_time = jsonObject.optString("open_time");
                        final String close_time = jsonObject.optString("close_time");




                        restaurantModels.add(new RestaurantModel(Integer.parseInt(id),name,phone,email,city,address,img,type,Double.parseDouble(x),Double.parseDouble(y),open_time,close_time));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                map.addMarker(new MarkerOptions()
                        .snippet(String.valueOf(restaurantModels.get(0).getId()))
                        .title(restaurantModels.get(0).getName())
                        .position(new LatLng(restaurantModels.get(0).getX(), restaurantModels.get(0).getY())));
                progressDialog.dismiss();
            }
        }.execute();

        Toast.makeText(getActivity(), "hello Iam here" , Toast.LENGTH_LONG).show();
    }
    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    @Override
    public void onLocationChanged(final Location location) {
//        String msg = "New Latitude: "+location.getLatitude()+"New Longitude: "+location.getLongitude();
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        LatLng userLocation= new LatLng(location.getLatitude(),location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation,14));
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                String url= ServerUrl.url1+"updateUser.php?phone="+preferences.getString("phone",null)+"&x="+location.getLatitude()+"&y="+location.getLongitude();
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
        }.execute();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);

    }
}
