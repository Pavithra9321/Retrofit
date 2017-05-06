package com.mindmade.mcom.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindmade.mcom.R;
import com.mindmade.mcom.adapterclasses.CartAdapter;

public class Cart_Fragment extends Fragment {

    RecyclerView cartRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cart = inflater.inflate(R.layout.cart__fragment, container, false);
        cartRecyclerView = (RecyclerView) cart.findViewById(R.id.cart_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setHasFixedSize(false);
        CartAdapter adapter = new CartAdapter(getActivity());
        cartRecyclerView.setAdapter(adapter);

        return cart;
    }
}
