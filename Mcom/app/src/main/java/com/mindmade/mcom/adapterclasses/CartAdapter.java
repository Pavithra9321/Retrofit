package com.mindmade.mcom.adapterclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindmade.mcom.R;

import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    // List<String> data;

    public CartAdapter(Context context) {
        mContext = context;
        //   data = passData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new CartViewHolder(inflater.inflate(R.layout.cart_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cartProductImageView;
        TextView cartProductName, cartProductPrice, cartProductQuantity;
        ImageButton cartAddBtn, cartLessBtn, cartDeleteBtn;

        public CartViewHolder(View itemView) {
            super(itemView);
            cartProductImageView = (ImageView) itemView.findViewById(R.id.cart_product_imageview);
            cartProductName = (TextView) itemView.findViewById(R.id.cart_product_name_TV);
            cartProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price_TV);
            cartProductQuantity = (TextView) itemView.findViewById(R.id.cart_quantity_TV);
            cartAddBtn = (ImageButton) itemView.findViewById(R.id.cart_add_IV);
            cartLessBtn = (ImageButton) itemView.findViewById(R.id.cart_less_IV);
            cartDeleteBtn = (ImageButton) itemView.findViewById(R.id.cart_delete_IV);


            cartDeleteBtn.setOnClickListener(this);
            cartLessBtn.setOnClickListener(this);
            cartAddBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == cartDeleteBtn) {
                Toast.makeText(mContext, "Clicked Delete " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            } else if (v == cartAddBtn) {
                Toast.makeText(mContext, "Clicked Add " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            } else if (v == cartLessBtn) {
                Toast.makeText(mContext, "Clicked Less " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
