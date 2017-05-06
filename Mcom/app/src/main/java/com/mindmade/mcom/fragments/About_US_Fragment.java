package com.mindmade.mcom.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindmade.mcom.R;
import com.mindmade.mcom.activity.ProductListActivity;
import com.mindmade.mcom.utilclasses.CustomDialogFont;

public class About_US_Fragment extends Fragment {

    ImageView facebook,twitter,google,linkedin;
    TextView description;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View aboutus = inflater.inflate(R.layout.about__us__fragment, container, false);

        facebook= (ImageView) aboutus.findViewById(R.id.aboutusfb);
        twitter= (ImageView) aboutus.findViewById(R.id.aboutustwitter);
        google= (ImageView) aboutus.findViewById(R.id.aboutusgoogle);
        linkedin= (ImageView) aboutus.findViewById(R.id.aboutuslinked);
        description= (TextView) aboutus.findViewById(R.id.desc);


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/TT0140M.TTF");
        CustomDialogFont tfSpan = new CustomDialogFont(tf);
        description.setTypeface(tf);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String fb="https://www.facebook.com/mindmadetech/";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(fb));
                    startActivity(intent);
                    Log.d("success","fb"+intent);

                }catch (Exception e){
                    Toast.makeText(getActivity(),"Sorry now could not connect",Toast.LENGTH_LONG).show();
                }

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String tw="https://twitter.com/mindmadetech";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tw));
                    startActivity(intent);
                    Log.d("success","tw"+intent);

                }catch (Exception e){
                    Toast.makeText(getActivity(),"Sorry now could not connect",Toast.LENGTH_LONG).show();
                }
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String go="https://plus.google.com/+MindMade";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(go));
                    startActivity(intent);
                    Log.d("success","google"+intent);

                }catch (Exception e){
                    Toast.makeText(getActivity(),"Sorry now could not connect",Toast.LENGTH_LONG).show();
                }
            }
        });
        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prod=new Intent(getActivity(),ProductListActivity.class);
                startActivity(prod);

//                try {
//                    String link="https://www.linkedin.com/company-beta/3146241/?pathWildcard=3146241";
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
//                    startActivity(intent);
//                    Log.d("success","link"+intent);
//
//                }catch (Exception e){
//                    Toast.makeText(getActivity(),"Sorry now could not connect",Toast.LENGTH_LONG).show();
//                }
            }
        });

//        Button btn= (Button) aboutus.findViewById(R.id.btn);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent product=new Intent(getActivity(), ProductListActivity.class);
//                startActivity(product);
//            }
//        });

        return aboutus;
    }
}
