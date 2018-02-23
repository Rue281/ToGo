package com.bricksai.togo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bricksai.togo.fragments.MapsFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegistrationActivity extends AppCompatActivity {
    EditText edName,edPhone,edPassword,edPasswordAgain,edEmail;
//    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edName=(EditText)findViewById(R.id.edName);
        edPhone=(EditText)findViewById(R.id.edPhone);
        edPassword=(EditText)findViewById(R.id.edPassword1);
        edPasswordAgain=(EditText)findViewById(R.id.edPassword2);
        edEmail=(EditText)findViewById(R.id.edEmail);
    }
    public void saveData (View view){

        final String[] name= new String[1];
        name[0] = edName.getText().toString();

        final String[] phone= new String[1];
        phone[0] = edPhone.getText().toString();

        final String[] password= new String[1];
        password[0]=edPassword.getText().toString();

        final String[] passwordAgain= new String[1];
        passwordAgain[0]=edPasswordAgain.getText().toString();

        final String[] email= new String[1];
        email[0]=edEmail.getText().toString();

        if(password[0].equals(passwordAgain[0])){

            new AsyncTask<String, String, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    //String url= null;
                    String url1=null;
                    try {
                        //url = ServerUrl.url+"signup.php?email="+ URLEncoder.encode(email[0],"utf-8")+"&name="+ URLEncoder.encode(name[0],"utf-8")+"&phone="+URLEncoder.encode(phone[0],"utf-8")+"&password="+URLEncoder.encode(password[0],"utf-8");
                        //url1=ServerUrl.url1+"creatuser.php?name="+ URLEncoder.encode(name[0],"utf-8")+"&email"+ URLEncoder.encode(email[0],"utf-8")+"&phone="+URLEncoder.encode(phone[0],"utf-8")+"&password="+URLEncoder.encode(password[0],"utf-8");
                        url1=ServerUrl.url1+"creatuser.php?email="+ URLEncoder.encode(email[0],"utf-8")+"&name="+ URLEncoder.encode(name[0],"utf-8")+"&phone="+URLEncoder.encode(phone[0],"utf-8")+"&password="+URLEncoder.encode(password[0],"utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String result="";
                    try {
                        URL urll=new URL(url1);
                        HttpURLConnection connection=(HttpURLConnection)urll.openConnection();
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
                }
            }.execute();
            Toast.makeText(RegistrationActivity.this,"Successfully signed up!!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AppActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(RegistrationActivity.this,"Password didn't matched",Toast.LENGTH_SHORT).show();
        }

    }

    public void signIn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
