package com.mindmade.mcom.activity;

import android.content.DialogInterface;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mindmade.mcom.R;

import com.mindmade.mcom.adapterclasses.BottomSheetAdapter;
import com.mindmade.mcom.adapterclasses.ProductsAdapter;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.NetworkConnectionManager;
import com.mindmade.mcom.utilclasses.PrefManager;
import com.mindmade.mcom.utilclasses.api.AllApi;
import com.mindmade.mcom.utilclasses.model.BottomSheetItem;
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.model.ProductModel;
import com.mindmade.mcom.utilclasses.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitleTV;
    LinearLayout sortbyLayout, refineLayout;
    RecyclerView catProductRecyclerView;
    ProgressBar catProductProgressBar, leteastLoadmoreProgressbar;
    ImageView catProductNodataImageView;
    SwipeRefreshLayout catProductRefreshLayout;
    ArrayList<ProductModel.Products> data;
    NetworkConnectionManager connectionManager;
    AllApi apiInitialize;
    PrefManager sessionManger;
    ProductsAdapter adapter;


    public final int TYPE_LOAD = 1;
    public final int TYPE_PRODUCT = 0;
    private boolean _hasLoadedOnce = false;
    private int interval = 10 * 200;
    String categoryName;
    int categoryID;
    String sort, filters;


    BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        connectionManager = new NetworkConnectionManager(this);
        sessionManger = new PrefManager(this);
        apiInitialize = ServiceGenerator.createService(AllApi.class, Const.API_VALUE, Const.PASSWORD_VALUE);
        toolbar = (Toolbar) findViewById(R.id.category_product_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleTV = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitleTV.setAllCaps(true);
        //toolbarTitleTV.setText(categoryName + " " + Const.COLLECTION_STR);
        toolbarTitleTV.setText("ProductList");


        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        data = new ArrayList<>();
//        connectionManager = new NetworkConnectionManager(this);
//        sessionManger = new PrefManager(this);


        // sortbyLayout = (LinearLayout) findViewById(R.id.category_products_sortby_layout);
        // refineLayout = (LinearLayout) findViewById(R.id.category_products_refine_layout);
        catProductRecyclerView = (RecyclerView) findViewById(R.id.common_recyclerview);
        catProductProgressBar = (ProgressBar) findViewById(R.id.common_progressbar);
        catProductNodataImageView = (ImageView) findViewById(R.id.common_nodata_imageview);
        catProductRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.common_refresh_layout);
        catProductRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);
        catProductRecyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        catProductRecyclerView.setLayoutManager(layoutManager);


        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);


        loadDataFromApi(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sort_by_menu, menu);

        MenuItem item = menu.findItem(R.id.action_sort_by);
        // searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_by:
                showBottomSheetDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


        //loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_loading, latestRecyclerView, false);


//
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getItemViewType(position) == TYPE_PRODUCT) {
                    return 1;
                } else {
                    return 2;
                }
                //  return bottom_sheet_adapter.getItemViewType(position) == TYPE_LOAD ? 2 : 1;
            }
        });

        adapter.setLoadMoreListener(new ProductsAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                catProductRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                          Log.d("Success", "Size:::: " + data.size());
                        int index = data.size();
                     //  TYPE_LOAD=data.size();
                        loadDataFromApi(index);
                    }
                });
            }
        });

       catProductRecyclerView.setAdapter(adapter);

        catProductRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (data.size() > 0) {
                    data.clear();
                    adapter.notifyDataChanged();
                    adapter.setMoreDataAvailable(true);
                }
                loadDataFromApi(0);
            }
        });

        loadDataFromApi(0);




    }
    private void loadDataFromApi(int index) {
        if (connectionManager.isConnectingToInternet()) {
            Call<ProductModel> Productscall = apiInitialize.getProductsListData(Const.PRODUCT_LIMIT_VALUE);
            Log.w("Success", "URL::: " + Productscall.request().url().toString());

            Productscall.enqueue(new Callback<ProductModel>() {
                @Override
                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                    Log.w("Success", "Response::: " + new Gson().toJson(response.body()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.code()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.message()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.errorBody()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.headers()));

                    catProductRefreshLayout.setRefreshing(false);
                    try {
                        if (response.isSuccessful()) {

                            ProductModel productModel = response.body();
                            catProductProgressBar.setVisibility(View.GONE);
                            catProductRecyclerView.setVisibility(View.VISIBLE);
                            data.addAll(productModel.getProduct());
                            //  adapter.notifyDataChanged();

                            ProductsAdapter adapter = new ProductsAdapter(ProductListActivity.this, data);
                            Log.w("Suuccess", "asxfdf::: " + data.size());
                            catProductRecyclerView.setAdapter(adapter);
                        } else {
                            Log.e("Error", "Failure Response");
                        }
                    } catch (Exception e) {
                        Log.e("Exception", "" + e);
                    }
                }


                @Override
                public void onFailure(Call<ProductModel> call, Throwable t) {
                    Log.e("Failure", " Response Error " + t.getMessage());
                }
            });
        } else {
            // Toast.makeText(CategoryProductsActivity.this, "" + getString(R.string.netUnavailable), Toast.LENGTH_SHORT).show();
        }
    }

//    private void loadMoreDataFromApi(int index) {
//        if (connectionManager.isConnectingToInternet()) {
//            data.add(new ProductModel("load"));
//            adapter.notifyItemInserted(data.size() - 1);
//            Call<ProductSubCategory> latestProducts = apiInitialize.getCategoryProducts(categoryID, index, Const.LIMIT_VALUE, sort, filters);
//            Log.w("Success", "URL::: " + latestProducts.request().url().toString());
//            latestProducts.enqueue(new Callback<ProductSubCategory>() {
//                @Override
//                public void onResponse(Call<ProductSubCategory> call, Response<ProductSubCategory> response) {
//                    Log.w("Success", "" + new Gson().toJson(response.body()));
//                    try {
//                        if (response.isSuccessful()) {
//                            //remove loading view
//                            data.remove(data.size() - 1);
//                            ProductSubCategory productsModel = response.body();
//                            if (productsModel.getStatus() == 1) {
//                                if (productsModel.getProductsData().size() > 0) {
//                                    data.addAll(productsModel.getProductsData());
//                                } else {
//                                    adapter.setMoreDataAvailable(false);
//                                   /* data.add(new ProductModel("nodata"));
//                                    bottom_sheet_adapter.notifyItemInserted(data.size() - 1);
//                                    new Handler().postDelayed(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            bottom_sheet_adapter.setMoreDataAvailable(false);
//                                            data.remove(data.size() - 1);
//
//                                        }
//                                    }, interval);*/
//                                    //telling bottom_sheet_adapter to stop calling load more as no more server data available
//                                    Toast.makeText(CategoryProductsActivity.this, "No More Data Available", Toast.LENGTH_LONG).show();
//                                }
//
//                            } else {
//                                adapter.setMoreDataAvailable(false);
//                               /* data.add(new ProductModel("nodata"));
//                                bottom_sheet_adapter.notifyItemInserted(data.size() - 1);
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        bottom_sheet_adapter.setMoreDataAvailable(false);
//                                        data.remove(data.size() - 1);
//
//                                    }
//                                }, interval);*/
//                                //telling bottom_sheet_adapter to stop calling load more as no more server data available
//                                Toast.makeText(CategoryProductsActivity.this, "No More Data Available", Toast.LENGTH_LONG).show();
//                            }
//
//                            adapter.notifyDataChanged();
//                        } else {
//                            Log.e("Error", "Failure Response");
//                        }
//                    } catch (Exception e) {
//                        Log.e("Exception", "" + e);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ProductSubCategory> call, Throwable t) {
//                    Log.e("Failure", " Response Error " + t.getMessage());
//                }
//            });
//        } else {
//            Toast.makeText(CategoryProductsActivity.this, "" + getString(R.string.netUnavailable), Toast.LENGTH_SHORT).show();
//        }
//    }

    private void showBottomSheetDialog() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

