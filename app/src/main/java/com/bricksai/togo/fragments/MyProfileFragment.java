package com.bricksai.togo.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bricksai.togo.R;
import com.bricksai.togo.utiles.ImageDownloader;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Remonda on 5/2/2017.
 */

public class MyProfileFragment extends Fragment{
    CircleImageView profileImage;
    ProgressDialog progressDialog;
    RatingBar ratingBar;
    EditText edAdress, edEmail,edPassword,edPhone;
    ImageView imgchangeUserInfo;
    boolean edit;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);

        edit = false;

        profileImage=(CircleImageView)view.findViewById(R.id.circleImageView);
        edAdress=(EditText)view.findViewById(R.id.edAdress);
        edEmail=(EditText)view.findViewById(R.id.edEmail);
        edPassword=(EditText)view.findViewById(R.id.edPassword);
        edPhone=(EditText)view.findViewById(R.id.edPhone);
        imgchangeUserInfo=(ImageView)view.findViewById(R.id.imgchangeUserInfo);

        imgchangeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit)
                {
                    edAdress.setEnabled(true);
                    edEmail.setEnabled(true);
                    edPassword.setEnabled(true);
                    edPhone.setEnabled(true);
                    edit = true;
                }else if (edit)
                {
                    edAdress.setEnabled(false);
                    edEmail.setEnabled(false);
                    edPassword.setEnabled(false);
                    edPhone.setEnabled(false);
                    edit =false;
                    new AsyncTask<String, String, String>() {
                        @Override
                        protected String doInBackground(String... params) {

                            return null;
                        }
                    }.execute();
                }
            }
        });

        ratingBar=(RatingBar)view.findViewById(R.id.ratingBar);
        //ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        //preferencesRating= PreferenceManager.getDefaultSharedPreferences(this);

//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//
//            }
//        });

        final ImageDownloader imageDownloader=new ImageDownloader();
        new AsyncTask<String, String, Bitmap>() {
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
            protected Bitmap doInBackground(String... params) {
                String url="https://i.ytimg.com/vi/tntOCGkgt98/maxresdefault.jpg";
                Bitmap image;
                image=imageDownloader.downloadImage(url);
//                try {
//                    URL test=new URL(url);
//                    HttpURLConnection connection=(HttpURLConnection)test.openConnection();
//                    InputStreamReader input=new InputStreamReader(connection.getInputStream());
//                    BufferedReader buffer=new BufferedReader(input);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            return image;
            }

            @Override
            protected void onPostExecute(Bitmap s) {
                super.onPostExecute(s);
                profileImage.setImageBitmap(s);
                progressDialog.dismiss();
            }
        }.execute();

        //btnEdit=(ImageView)view.findViewById(R.id.edit);

        preferences = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = preferences.edit();
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("User info");
    }
}
