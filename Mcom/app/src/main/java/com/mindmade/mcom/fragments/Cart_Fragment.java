package com.mindmade.mcom.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mindmade.mcom.R;
import com.mindmade.mcom.utilclasses.CartSQLiteHelper;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.NetworkConnectionManager;
import com.mindmade.mcom.utilclasses.model.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class Cart_Fragment extends Fragment {

    RecyclerView cartRecyclerView;
    TextView cartNodataTV, carttotalItems, carttotalPrice, cartToChechout;
    ProgressBar cartProgressbar;
    SwipeRefreshLayout cartSwipeRefreshLayout;
    NetworkConnectionManager connectionManager;
    List<CartProduct> data;
    String count, price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cart = inflater.inflate(R.layout.cart__fragment, container, false);
        Cart_Fragment fragment = new Cart_Fragment();
        CartSQLiteHelper cartHelper = new CartSQLiteHelper(getActivity());
        connectionManager = new NetworkConnectionManager(getActivity());
        cartRecyclerView = (RecyclerView) cart.findViewById(R.id.common_recyclerview);
        cartNodataTV = (TextView) cart.findViewById(R.id.common_nodata_TV);
        cartProgressbar = (ProgressBar) cart.findViewById(R.id.common_progressbar);
        cartSwipeRefreshLayout = (SwipeRefreshLayout) cart.findViewById(R.id.common_refresh_layout);
        carttotalItems = (TextView) cart.findViewById(R.id.cart_item_count_TV);
        carttotalPrice = (TextView) cart.findViewById(R.id.cart_total_amount_TV);
        cartToChechout = (TextView) cart.findViewById(R.id.cart_checkout_TV);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setHasFixedSize(false);
        data = new ArrayList<>();
        data.addAll(cartHelper.getAllCartItems());

        if (connectionManager.isConnectingToInternet()) {
            if (data.size() > 0) {
                CartAdapter adapter = new CartAdapter(getActivity(), data);
                cartRecyclerView.setAdapter(adapter);
                cartToChechout.setVisibility(View.VISIBLE);
                carttotalItems.setVisibility(View.VISIBLE);
                carttotalPrice.setVisibility(View.VISIBLE);
                cartRecyclerView.setVisibility(View.VISIBLE);
                cartProgressbar.setVisibility(View.GONE);
                carttotalItems.setText(String.valueOf(Const.CART_ITEMS_KEY + String.valueOf(data.size())));
                carttotalPrice.setText(String.valueOf(Const.CART_TOTAL_KEY + getResources().getString(R.string.rs_symbol) + cartHelper.getTotalPrice()));
                // updatePriceAndCount(String.valueOf(data.size()), cartHelper.getTotalPrice());
            } else {
                cartRecyclerView.setVisibility(View.GONE);
                cartProgressbar.setVisibility(View.GONE);
                cartNodataTV.setVisibility(View.VISIBLE);
                cartNodataTV.setText("No data found");
                cartToChechout.setVisibility(View.GONE);
                carttotalItems.setVisibility(View.GONE);
                carttotalPrice.setVisibility(View.GONE);
            }
        } else {
            cartRecyclerView.setVisibility(View.GONE);
            cartProgressbar.setVisibility(View.GONE);
            cartNodataTV.setVisibility(View.VISIBLE);
            cartNodataTV.setText("Internet connection not found");
            cartToChechout.setVisibility(View.GONE);
            carttotalItems.setVisibility(View.GONE);
            carttotalPrice.setVisibility(View.GONE);
        }
        return cart;
    }


    public void updateUI(String count, String price) {
        if (count.equals("0") || count.equals("")) {
            cartRecyclerView.setVisibility(View.GONE);
            cartProgressbar.setVisibility(View.GONE);
            cartNodataTV.setVisibility(View.VISIBLE);
            cartNodataTV.setText("No data found");
            cartToChechout.setVisibility(View.GONE);
            carttotalItems.setVisibility(View.GONE);
            carttotalPrice.setVisibility(View.GONE);
        } else {
            carttotalItems.setText(String.valueOf(Const.CART_ITEMS_KEY + count));
            carttotalPrice.setText(String.valueOf(Const.CART_TOTAL_KEY + price));
        }
    }


    class CartAdapter extends RecyclerView.Adapter {
        Context mContext;
        List<CartProduct> data;
        CartSQLiteHelper cartSQLiteHelper;

        public CartAdapter(Context context, List<CartProduct> passData) {
            mContext = context;
            data = passData;
            cartSQLiteHelper = new CartSQLiteHelper(mContext);
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
                ((CartViewHolder) holder).cartProductPrice.setText(String.valueOf(mContext.getResources().getString(R.string.rs_symbol) + data.get(position).getTotal()));
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

        class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
                    updateUI(String.valueOf(data.size()), cartSQLiteHelper.getTotalPrice());
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
}
