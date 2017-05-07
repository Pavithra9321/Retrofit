package com.mindmade.mcom.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mindmade.mcom.R;
import com.mindmade.mcom.adapterclasses.CartAdapter;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cart = inflater.inflate(R.layout.cart__fragment, container, false);

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
                carttotalItems.setText(Const.CART_ITEMS_KEY + data.size());
                carttotalPrice.setText(Const.CART_TOTAL_KEY);
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


}
