package com.bricksai.togo;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bricksai.togo.fragments.MapsFragment;
import com.bricksai.togo.fragments.MenuFragment;
import com.bricksai.togo.fragments.MyProfileFragment;
import com.bricksai.togo.fragments.OrderHistoryFragment;

public class AppActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        preferences = getApplicationContext().getSharedPreferences("remonda", MODE_PRIVATE);
        editor = preferences.edit();

        Toast.makeText(this, preferences.getString("phone", null), Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setBackgroundResource(R.color.colorPrimary);
        navigationView.setNavigationItemSelectedListener(this);
        final FragmentManager fm= getFragmentManager();
        final android.app.Fragment fragment=fm.findFragmentById(R.id.frame);

        if(fragment != null && fragment.isVisible()){
            MapsFragment mapsFragment = new MapsFragment();
            getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
            getFragmentManager().beginTransaction().add(R.id.frame, mapsFragment, "about").commit();
        }
        else{
            MapsFragment mapsFragment = new MapsFragment();
            getFragmentManager().beginTransaction().add(R.id.frame, mapsFragment, "about").commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            final FragmentManager fm= getFragmentManager();
            final android.app.Fragment fragment=fm.findFragmentById(R.id.frame);

            if(fragment != null && fragment.isVisible()){
                MapsFragment mapsFragment = new MapsFragment();
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
                getFragmentManager().beginTransaction().add(R.id.frame, mapsFragment, "about").commit();
            }
            else{
                MapsFragment mapsFragment = new MapsFragment();
                getFragmentManager().beginTransaction().add(R.id.frame, mapsFragment, "about").commit();
            }
        } else if (id == R.id.nav_order) {
            final FragmentManager fm= getFragmentManager();
            final android.app.Fragment fragment=fm.findFragmentById(R.id.frame);

            if(fragment != null && fragment.isVisible()){
                OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
                getFragmentManager().beginTransaction().add(R.id.frame, orderHistoryFragment).commit();
            }
            else{
                OrderHistoryFragment orderHistoryFragment = new OrderHistoryFragment();
                getFragmentManager().beginTransaction().add(R.id.frame, orderHistoryFragment).commit();
            }

        } else if (id == R.id.nav_profile) {
            final FragmentManager fm= getFragmentManager();
            final android.app.Fragment fragment=fm.findFragmentById(R.id.frame);

            if(fragment != null && fragment.isVisible()){
                MyProfileFragment myProfile = new MyProfileFragment();
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
                getFragmentManager().beginTransaction().add(R.id.frame, myProfile).commit();
                //ServerUrl serverUrl = new ServerUrl();
                //String x = ServerUrl.url;
            }
            else{
                MyProfileFragment myProfile = new MyProfileFragment();
                getFragmentManager().beginTransaction().add(R.id.frame, myProfile).commit();
            }

        } else if (id == R.id.nav_friends) {
            ///for testing only !!!!!!!!!!!!
            final FragmentManager fm= getFragmentManager();
            final android.app.Fragment fragment=fm.findFragmentById(R.id.frame);

            if(fragment != null && fragment.isVisible()){
                MenuFragment Menu = new MenuFragment();
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.frame)).commit();
                getFragmentManager().beginTransaction().add(R.id.frame, Menu).commit();
            }
            else{
                MenuFragment Menu = new MenuFragment();
                getFragmentManager().beginTransaction().add(R.id.frame, Menu).commit();
            }

        } else if (id == R.id.nav_about) {
            Intent intent= new Intent(this,AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            editor.putString("phone", null);
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //Intent intent=new Intent(this, AndroidImageSlider.class);
            //startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
