package com.mindmade.mcom.adapterclasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mindmade.mcom.R;
import com.mindmade.mcom.activity.TabActivity;
import com.mindmade.mcom.fragments.Cart_Fragment;
import com.mindmade.mcom.utilclasses.CartSQLiteHelper;
import com.mindmade.mcom.utilclasses.model.CartProduct;

import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public class CartAdapter extends RecyclerView.Adapter {
    Context mContext;
    List<CartProduct> data;
    CartSQLiteHelper cartSQLiteHelper;
    Cart_Fragment mCart_fragment;

    public CartAdapter(Context context, List<CartProduct> passData, Cart_Fragment cart_fragment) {
        mContext = context;
        data = passData;
        cartSQLiteHelper = new CartSQLiteHelper(mContext);
        mCart_fragment = cart_fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new CartViewHolder(inflater.inflate(R.layout.cart_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CartViewHolder) {
            ((CartViewHolder) holder).cartProductName.setText(data.get(position).getName());
            ((CartViewHolder) holder).cartProductPrice.setText(String.valueOf(mContext.getResources().getString(R.string.rs_symbol)+data.get(position).getTotal()));
            Log.w("Success", "QTY::: " + data.get(position).getQty());
            ((CartViewHolder) holder).cartProductQuantity.setText(String.valueOf(data.get(position).getQty()));
            Glide.with(mContext).load(data.get(position).getImg_url()).fitCenter().listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(((CartViewHolder) holder).cartProductImageView);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cartProductImageView;
        public TextView cartProductName, cartProductPrice, cartProductQuantity;
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
                cartSQLiteHelper.deleteCart(data.get(getAdapterPosition()));
                data.remove(getAdapterPosition());
                //  ((TabActivity)mContext).updatePriceAndCount(String.valueOf(data.size()), cartSQLiteHelper.getTotalPrice());
                Log.d("Success", "Size:::" + String.valueOf(data.size()));
                Log.d("Success", "Size:::" + cartSQLiteHelper.getTotalPrice());
                notifyDataSetChanged();
            } else if (v == cartAddBtn) {
                data.get(getAdapterPosition()).setQty(Integer.parseInt(cartProductQuantity.getText().toString().trim()) + 1);
                data.get(getAdapterPosition()).setTotal(String.valueOf(data.get(getAdapterPosition()).getQty() * Float.parseFloat((data.get(getAdapterPosition()).getPrice()))));
                cartSQLiteHelper.updateCart(data.get(getAdapterPosition()));
                cartProductQuantity.setText(String.valueOf(data.get(getAdapterPosition()).getQty()));
                cartProductPrice.setText(data.get(getAdapterPosition()).getTotal());
            } else if (v == cartLessBtn) {
                if (data.get(getAdapterPosition()).getQty() > 1) {
                    data.get(getAdapterPosition()).setQty(Integer.parseInt(cartProductQuantity.getText().toString().trim()) - 1);
                    data.get(getAdapterPosition()).setTotal(String.valueOf(data.get(getAdapterPosition()).getQty() * Float.parseFloat((data.get(getAdapterPosition()).getPrice()))));
                    cartSQLiteHelper.updateCart(data.get(getAdapterPosition()));
                    cartProductQuantity.setText(String.valueOf(data.get(getAdapterPosition()).getQty()));
                    cartProductPrice.setText(data.get(getAdapterPosition()).getTotal());
                }
            }
        }
    }
}
