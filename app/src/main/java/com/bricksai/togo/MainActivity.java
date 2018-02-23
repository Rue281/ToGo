package com.bricksai.togo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bricksai.togo.utiles.IsValid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    //SharedPreferences preferencesRating;
    SharedPreferences.Editor editor;

    EditText edPhone, edPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edPhone = (EditText) findViewById(R.id.edPhone);
        edPassword = (EditText) findViewById(R.id.edPassword);

        try {
            preferences = getApplicationContext().getSharedPreferences("remonda", MODE_PRIVATE);
            editor = preferences.edit();

            if (preferences.getString("phone", null).toString().equals("") || preferences.getString("phone", null) == null) {

            } else {
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {

        }
    }

    public void signIn(View view) {
         final String[] phone = new String[1];
        phone[0] = edPhone.getText().toString();

         final String[] password = new String[1];
        password[0] = edPassword.getText().toString();


        IsValid isValid = new IsValid();
        String txtPhone = edPhone.getText().toString();
        Log.e("showphonenumber", txtPhone);
        if (isValid.isPhoneValid(txtPhone)) {
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    String loginUrl = null;
                    try {
                        //url = ServerUrl.url+"signup.php?email="+ URLEncoder.encode(email[0],"utf-8")+"&name="+ URLEncoder.encode(name[0],"utf-8")+"&phone="+URLEncoder.encode(phone[0],"utf-8")+"&password="+URLEncoder.encode(password[0],"utf-8");
                        //url1=ServerUrl.url1+"creatuser.php?name="+ URLEncoder.encode(name[0],"utf-8")+"&email"+ URLEncoder.encode(email[0],"utf-8")+"&phone="+URLEncoder.encode(phone[0],"utf-8")+"&password="+URLEncoder.encode(password[0],"utf-8");
                        loginUrl = ServerUrl.url1 + "login.php?phone=" + URLEncoder.encode(phone[0], "utf-8") + "&password=" + URLEncoder.encode(password[0], "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String result = "";
                    try {
                        URL urll = new URL(loginUrl);
                        HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
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
                    //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    if (s.equals("true")){
                        Intent intent = new Intent(MainActivity.this, AppActivity.class);
                        editor.putString("phone", edPhone.getText().toString());
                        editor.commit();
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"incorrect phone number ot password",Toast.LENGTH_SHORT).show();
                    }

                }
            }.execute();

        } else

        {
            Toast.makeText(this, "please Enter your mobile Number", Toast.LENGTH_LONG).show();
        }
    }




//            editor.putString("phone", edPhone.getText().toString());
//            editor.commit();
//            Intent intent = new Intent(this, AppActivity.class);
//            startActivity(intent);
//            finish();


    public void signUp(View view)
    {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
