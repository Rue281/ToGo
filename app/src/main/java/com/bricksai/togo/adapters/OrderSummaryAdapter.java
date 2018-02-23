package com.bricksai.togo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bricksai.togo.R;
import com.bricksai.togo.models.OrderSummaryModel;

import java.util.ArrayList;

/**
 * Created by Remonda on 5/18/2017.
 */

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.MenuViewHolder>{
    ArrayList<OrderSummaryModel> orderSummaryModels;

    public OrderSummaryAdapter(ArrayList<OrderSummaryModel> orderSummaryModels){
        this.orderSummaryModels=orderSummaryModels;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_summary_items,parent,false);
        OrderSummaryAdapter.MenuViewHolder menuViewHolder= new OrderSummaryAdapter.MenuViewHolder(view);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        holder.txtItem.setText(orderSummaryModels.get(position).getItem());
        holder.txtPrice.setText(String.valueOf(orderSummaryModels.get(position).getPrice()));
        holder.txtQuantity.setText(String.valueOf(orderSummaryModels.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return orderSummaryModels.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView txtItem, txtPrice, txtQuantity;
        LinearLayout linearLayout;

        public MenuViewHolder(View itemView) {
            super(itemView);
            txtItem= (TextView) itemView.findViewById(R.id.txtItem);
            txtPrice=(TextView) itemView.findViewById(R.id.txtItemPrice);
            txtQuantity=(TextView) itemView.findViewById(R.id.txtQuantity);
            linearLayout= (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
