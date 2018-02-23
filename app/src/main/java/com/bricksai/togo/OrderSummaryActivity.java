package com.bricksai.togo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bricksai.togo.adapters.OrderSummaryAdapter;
import com.bricksai.togo.models.OrderSummaryModel;

import java.util.ArrayList;

/**
 * Created by Remonda on 5/17/2017.
 */

public class OrderSummaryActivity extends AppCompatActivity{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<OrderSummaryModel>orderSummaryModels = new ArrayList<>();
    TextView txtPrice, txtQuantity, txtSubTotalBeforeDiscount,txtDiscount,txtSubtotal,txtFinalTotal;
    private String m_Text = "";
    Double finalTotal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);

        txtPrice=(TextView)findViewById(R.id.txtItemPrice);
        txtQuantity=(TextView)findViewById(R.id.txtQuantity);
        txtSubTotalBeforeDiscount=(TextView)findViewById(R.id.txtSubTotalBeforeDiscount);
        txtDiscount=(TextView)findViewById(R.id.txtDiscount);
        txtSubtotal=(TextView)findViewById(R.id.txtSubtotal);
        txtFinalTotal=(TextView)findViewById(R.id.txtFinalTotal);

        preferences = getApplicationContext().getSharedPreferences("order", MODE_PRIVATE);
        editor = preferences.edit();


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.summaryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderSummaryActivity.this));



        for(int i=0; i < Integer.parseInt(preferences.getString("itemCounter", "0"));i++){
//            try
//            {
                double totalBeforeDiscount=0;
                double discount=0;

                String itemId = preferences.getString("item_"+String.valueOf((i+1)),null);
                String itemPrice = preferences.getString("itemP_"+String.valueOf((i+1)),null);
                String itemName = preferences.getString("itemN_"+String.valueOf((i+1)),null);
                int quantity = Integer.parseInt(preferences.getString("itemQ_" +
                        ""+String.valueOf((i+1)),"0"));
                orderSummaryModels.add(new OrderSummaryModel(itemName,Double.parseDouble(itemPrice),quantity));
                //txtPrice.setText(itemPrice);
                //txtQuantity.setText(String.valueOf(quantity));

                totalBeforeDiscount=totalBeforeDiscount+(Double.valueOf(itemPrice)*quantity);
                txtSubTotalBeforeDiscount.setText(String.valueOf(totalBeforeDiscount));
                txtDiscount.setText(String.valueOf(discount));
                //String subtotal=String.valueOf(discount);
                txtSubtotal.setText(String.valueOf(totalBeforeDiscount - discount));
                txtFinalTotal.setText(String.valueOf(totalBeforeDiscount - discount));

                finalTotal =Double.parseDouble(String.valueOf(totalBeforeDiscount - discount));
//            }catch (Exception e)
//            {
//                e.printStackTrace();
//            }
        }


        OrderSummaryAdapter orderSummaryAdapter = new OrderSummaryAdapter(orderSummaryModels);
        recyclerView.setAdapter(orderSummaryAdapter);
    }

    public void clearSharedPreferences(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderSummaryActivity.this);
        builder.setTitle("Cancel Order")
                .setMessage("Are you sure you want to cancel your order ?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete
                editor.clear();
                editor.commit();
                Intent intent=new Intent(OrderSummaryActivity.this,AppActivity.class);
                startActivity(intent);
                finish();
            }
    })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog only
                    }
                })
                .setIcon(R.drawable.logo)
                .show();
    }
    public void proceedOrder(View view){

        //dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exchange");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_Text = input.getText().toString();
                double userInput = Double.parseDouble(m_Text);

                if (userInput >= finalTotal){
                    dialog.dismiss();
                    Intent intent=new Intent(OrderSummaryActivity.this,AppActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(OrderSummaryActivity.this,"Order proceeded successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(OrderSummaryActivity.this,"Blash estzraaaf",Toast.LENGTH_SHORT).show();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.

                }


            }
        });

    }

}
