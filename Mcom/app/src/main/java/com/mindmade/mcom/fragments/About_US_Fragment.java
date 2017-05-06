package com.mindmade.mcom.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mindmade.mcom.R;
import com.mindmade.mcom.activity.ProductListActivity;

public class About_US_Fragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aboutus = inflater.inflate(R.layout.about__us__fragment, container, false);


        Button btn= (Button) aboutus.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent product=new Intent(getActivity(), ProductListActivity.class);
                startActivity(product);
            }
        });

        return aboutus;
    }
}
