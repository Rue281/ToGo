package com.bricksai.togo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bricksai.togo.R;
import com.bricksai.togo.models.OrderHistoryModel;

import java.util.ArrayList;

/**
 * Created by Remonda on 5/4/2017.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MenuViewHolder>{
    ArrayList<OrderHistoryModel>orderHistoryModels;
    boolean color;

    public OrderHistoryAdapter(ArrayList<OrderHistoryModel>orderHistoryModels){
        this.orderHistoryModels=orderHistoryModels;
        color = false;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_items,parent,false);
        OrderHistoryAdapter.MenuViewHolder menuViewHolder=new OrderHistoryAdapter.MenuViewHolder(view);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder,final int position) {
        holder.txtTime.setText(orderHistoryModels.get(position).getTime());
        holder.txtNumber.setText("#"+orderHistoryModels.get(position).getNumber());
        holder.txtPrice.setText(orderHistoryModels.get(position).getPrice()+" LE");
        if (color)
        {
            holder.layout.setBackgroundResource(R.color.light_gray);
            color = false;
        }else
        {
            color = true;
        }
    }

    @Override
    public int getItemCount() {
        return orderHistoryModels.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime,txtNumber,txtPrice;
        RelativeLayout layout;

        public MenuViewHolder(View itemView) {
            super(itemView);
            txtTime=(TextView)itemView.findViewById(R.id.txtTime);
            txtNumber=(TextView)itemView.findViewById(R.id.txtOrderNumber);
            txtPrice=(TextView)itemView.findViewById(R.id.txtPrice);
            layout = (RelativeLayout) itemView.findViewById(R.id.backLayout);
        }
    }
}
