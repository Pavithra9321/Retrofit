package com.mindmade.mcom.activity;

import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindmade.mcom.R;
import com.mindmade.mcom.adapter.TabPagerAdapter;
import com.mindmade.mcom.fragments.About_US_Fragment;
import com.mindmade.mcom.fragments.Cart_Fragment;
import com.mindmade.mcom.fragments.CategoriesFragment;
import com.mindmade.mcom.fragments.Search_Fragment;
import com.mindmade.mcom.utilclasses.Const;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity {

    TabLayout tabLayout;
    int initialPage = 0;
    ViewPager tabViewPager;
    Toolbar toolbar;
    TextView toolbarTitleTV;
    Cart_Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        toolbar = (Toolbar) findViewById(R.id.tab_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleTV = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitleTV.setText(getString(R.string.app_name));
        /*Toolbar End*/
        tabLayout = (TabLayout) findViewById(R.id.tab_tab_bar);
        tabViewPager = (ViewPager) findViewById(R.id.tab_pager);
        fragment = new Cart_Fragment();
        //set tablayout with viewpager so that the tabs can be swipeable
        setupViewPager(tabViewPager);
        tabLayout.setupWithViewPager(tabViewPager);
        tabViewPager.setCurrentItem(initialPage);

        setupTabIcons();

        tabViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setToolbarTitle(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupTabIcons() {
        Log.d("Success", "Comesss");
        TextView tabOne = (TextView) LayoutInflater.from(TabActivity.this).inflate(R.layout.custom_tab_bar_layout, null);
        tabOne.setText(Const.CATEGORIES);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_categories, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);
        TextView tabTwo = (TextView) LayoutInflater.from(TabActivity.this).inflate(R.layout.custom_tab_bar_layout, null);
        tabTwo.setText(Const.SEARCH);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(TabActivity.this).inflate(R.layout.custom_tab_bar_layout, null);
        tabThree.setText(Const.CART);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cart, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(TabActivity.this).inflate(R.layout.custom_tab_bar_layout, null);
        tabFour.setText(Const.ABOUT_US);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.about_us_tab, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        tabLayout.getTabAt(initialPage).getCustomView().setSelected(true);
        // toolbarTitleTV.setText(Const.LATEST_FRAG_TITLE);
        setToolbarTitle(0);

    }
    private void setupViewPager(ViewPager viewPager) {

        ArrayList<Fragment> frag = new ArrayList<>();
        frag.add(0, new CategoriesFragment());
        frag.add(1, new Search_Fragment());
        frag.add(2, new Cart_Fragment());
        frag.add(3, new About_US_Fragment());

        ArrayList<String> title = new ArrayList<>();
        title.add(0, Const.CATEGORIES);
        title.add(1, Const.SEARCH);
        title.add(2, Const.CART);
        title.add(3, Const.ABOUT_US);
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), frag, title);
        viewPager.setAdapter(adapter);
    }
    private void setToolbarTitle(int pos) {
        String title = Const.APP_NAME;
        switch (pos) {
            case 0:
                title = Const.CATEGORIES;
                break;
            case 1:
                title = Const.SEARCH;
                break;
            case 2:
                title = Const.CART;
                break;
            case 3:
                title = Const.ABOUT_US;
                break;
        }
        toolbarTitleTV.setAllCaps(true);
        toolbarTitleTV.setText(title);
    }
    public void updatePriceAndCount(String count, String price) {
        Log.d("Success", "Count::: " + count);
        Log.d("Success", "Price:::" + price);

        /*FragmentManager fm = getSupportFragmentManager();
        Cart_Fragment fragment = (Cart_Fragment)fm.findFragmentByTag("yourTag");
        fragment.updateUI(count,price);*/
        fragment.updateUI(count,price);
    }
}
