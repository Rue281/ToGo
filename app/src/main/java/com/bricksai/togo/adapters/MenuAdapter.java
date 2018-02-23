package com.bricksai.togo.adapters;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bricksai.togo.R;
import com.bricksai.togo.models.MenuModel;
import com.bricksai.togo.utiles.ImageDownloader;

import java.util.ArrayList;

/**
 * Created by Remonda on 5/3/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{
    ArrayList<MenuModel> menuModels;
    public MenuAdapter(ArrayList<MenuModel> menuModels){this.menuModels=menuModels;}
    @Override public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_items,parent,false);
        MenuViewHolder menuViewHolder=new MenuViewHolder(view);
        return menuViewHolder;}
    @Override public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        holder.txtName.setText(menuModels.get(position).getName());
        holder.txtPrice.setText(String.valueOf(menuModels.get(position).getPrice()));
        new AsyncTask<String, String, Bitmap>() {
            @Override protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override protected Bitmap doInBackground(String... params) {
                ImageDownloader imageDownloader=new ImageDownloader();
                Bitmap bitmap=imageDownloader.downloadImage(menuModels.get(position).getImage());
                return bitmap;}
            @Override protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                holder.imgItem.setImageBitmap(bitmap);}}.execute();}
    @Override public int getItemCount() {
        return menuModels.size();}
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        TextView txtName,txtPrice;
        ImageView imgItem;
        public MenuViewHolder(View itemView) {
            super(itemView);
            txtName=(TextView)itemView.findViewById(R.id.txtName);
            txtPrice=(TextView)itemView.findViewById(R.id.txtPrice);
            imgItem=(ImageView)itemView.findViewById(R.id.imgItem);}}}