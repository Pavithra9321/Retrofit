package com.mindmade.mcom.activity;

import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.mindmade.mcom.R;
import com.mindmade.mcom.adapterclasses.ProductsAdapter;
import com.mindmade.mcom.utilclasses.CartSQLiteHelper;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.NetworkConnectionManager;
import com.mindmade.mcom.utilclasses.PrefManager;
import com.mindmade.mcom.utilclasses.api.AllApi;
import com.mindmade.mcom.utilclasses.model.CartProduct;
import com.mindmade.mcom.utilclasses.model.ProductDescription;
import com.mindmade.mcom.utilclasses.model.ProductModel;
import com.mindmade.mcom.utilclasses.network.ServiceGenerator;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mindmade.mcom.R.id.product_detail_name_TV;
import static com.mindmade.mcom.R.id.progressBar;

public class ProductDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView productImageView;
    ProgressBar imageloadingProgressbar, productdetailProgressBar;
    TextView producttitleTV, productPriceTV, productOfferPriceTV, productDescriptionTV, productNodataTV;
    ScrollView productDetailScroolview;
    Toolbar toolbar;
    TextView toolbarTitleTV, no_data_Text;
    ImageButton desclikeImage;
    SwipeRefreshLayout product_detailRefreshLayout;
    Button cart_button;

    NetworkConnectionManager connectionManager;
    PrefManager sessionManager;
    AllApi apiInitialize;
    String productName, Likes;
    String UserID;
    ProductDescription descriptionDataList;
    CartSQLiteHelper sqLiteHelper;
    ProductModel productModel;
    ArrayList<CartProduct> cartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description);
        /*Toolbar Start*/


        //  int productID = getIntent().getIntExtra(Const.PRODUCT_ID_KEY,-1);
        UserID = getIntent().getStringExtra(Const.PRODUCT_ID_KEY);
        productName = getIntent().getStringExtra(Const.PRODUCT_NAME);
        Likes = getIntent().getStringExtra(Const.PRODUCT_LIKES);
        Log.d("success", "MMM3" + productName);
        // Log.d("success","MMM2"+product);
        connectionManager = new NetworkConnectionManager(this);
        sessionManager = new PrefManager(this);
        sqLiteHelper = new CartSQLiteHelper(this);
        apiInitialize = ServiceGenerator.createService(AllApi.class, Const.API_VALUE, Const.PASSWORD_VALUE);
        toolbar = (Toolbar) findViewById(R.id.product_detail_toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitleTV = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitleTV.setAllCaps(true);
        toolbarTitleTV.setText(StringUtils.abbreviate(productName, 10));
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*Toolbar End*/


        productDetailScroolview = (ScrollView) findViewById(R.id.product_detail_scrollview);
        productImageView = (ImageView) findViewById(R.id.product_detail_imageview);
        productdetailProgressBar = (ProgressBar) findViewById(R.id.product_detail_common_progressbar);
        imageloadingProgressbar = (ProgressBar) findViewById(R.id.product_detail_image_progressbar);
        producttitleTV = (TextView) findViewById(product_detail_name_TV);
        productPriceTV = (TextView) findViewById(R.id.product_detail_actual_price_TV);
        productOfferPriceTV = (TextView) findViewById(R.id.product_detail_offer_price_TV);
        productDescriptionTV = (TextView) findViewById(R.id.product_detail_content_TV);
        productNodataTV = (TextView) findViewById(R.id.product_detail_nodata_TV);
        desclikeImage = (ImageButton) findViewById(R.id.desclikeImage);
        cart_button = (Button) findViewById(R.id.cart_button);
        product_detailRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.product_detail_refresh_layout);
        product_detailRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.colorPrimary);
        loadDataFromApi(UserID);


        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Drawable drawableProgress = DrawableCompat.wrap(ra.getIndeterminateDrawable());
            DrawableCompat.setTint(drawableProgress, ContextCompat.getColor(getContext(), android.R.color.holo_green_light));
            progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(drawableProgress));

        } else {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), android.R.color.holo_green_light), PorterDuff.Mode.SRC_IN);
        }*/
        desclikeImage.setOnClickListener(this);
        cart_button.setOnClickListener(this);
        product_detailRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productDetailScroolview.setVisibility(View.GONE);
                productdetailProgressBar.setVisibility(View.GONE);
//                if (descriptionDataList.size() > 0) {
//                    descriptionDataList.clear();
//                }
                loadDataFromApi(UserID);
            }
        });
    }

    private void loadDataFromApi(String userID) {
        if (connectionManager.isConnectingToInternet()) {
            Call<ProductDescription> Productscall = apiInitialize.getProductDescriptiondata(userID);
            Log.w("Success", "URL::: " + Productscall.request().url().toString());

            Productscall.enqueue(new Callback<ProductDescription>() {
                @Override
                public void onResponse(Call<ProductDescription> call, Response<ProductDescription> response) {
                    Log.w("Success", "Response::: " + new Gson().toJson(response.body()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.code()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.message()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.errorBody()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.headers()));

                    //catProductRefreshLayout.setRefreshing(false);
                    try {
                        if (response.isSuccessful()) {

                            descriptionDataList = response.body();
                            Log.d("Success", "AAA" + descriptionDataList.getProductDesc().getId());

                            Long id = descriptionDataList.getProductDesc().getId();
                            String title = descriptionDataList.getProductDesc().getName();
                            String offerPrice = descriptionDataList.getProductDesc().getVaraiants().get(0).getPrice();
                            String image = descriptionDataList.getProductDesc().getImage().getSrc();

                            Log.d("Success", "AAA" + id);
                            Log.d("Success", "AAACart" + descriptionDataList.getProductDesc().isCartCheck());
                            //   Log.d("Success","AAA"+actualPrice);
                            Log.d("Success", "AAA" + image);

                            productdetailProgressBar.setVisibility(View.GONE);
                            productDetailScroolview.setVisibility(View.VISIBLE);
                            productNodataTV.setVisibility(View.GONE);
                            product_detailRefreshLayout.setRefreshing(false);
                            producttitleTV.setText(descriptionDataList.getProductDesc().getName());
                            productOfferPriceTV.setText(descriptionDataList.getProductDesc().getVaraiants().get(0).getPrice());
                            productPriceTV.setText(descriptionDataList.getProductDesc().getVaraiants().get(0).getActualprice());
                            productPriceTV.setPaintFlags(productPriceTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            productDescriptionTV.setText(descriptionDataList.getProductDesc().getDescription());
                            Glide
                                    .with(ProductDescriptionActivity.this)
                                    .load(descriptionDataList.getProductDesc().getImage().getSrc())
                                    .placeholder(R.drawable.placeholder)
                                    .crossFade()
                                    .dontAnimate()
                                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                    .placeholder(R.drawable.placeholder)
                                    .into(productImageView);

                            cartData = new ArrayList<CartProduct>();
                            cartData.addAll(sqLiteHelper.getAllCartItems());
                            Log.d("Success", "JJJJ" + cartData.size());
                            Log.d("Success", "KKK" + cartData.size());
                            for (int j = 0; j < cartData.size(); j++) {
                                if (cartData.get(j).getId().equals(String.valueOf(descriptionDataList.getProductDesc().getId()))) {
                                    Log.d("Success", "Cart Name:::123 " + cartData.get(j).getName());
                                    //Log.d("Success", "Name::: " + data.get(i).getName());
                                    descriptionDataList.getProductDesc().setCartCheck(true);
                                }
                                // }
                            }

                            if (descriptionDataList.getProductDesc().isCartCheck()) {
                                desclikeImage.setImageResource(R.drawable.like_red);
                                Log.d("Success", "AAACartRED");
                                cart_button.setText(getString(R.string.removecart));
                            } else {
                                desclikeImage.setImageResource(R.drawable.like_gray);
                                Log.d("Success", "AAACartGrey");
                                cart_button.setText(getString(R.string.addtocart));
                            }


                        } else {
                            productdetailProgressBar.setVisibility(View.GONE);
                            productDetailScroolview.setVisibility(View.GONE);
                            productNodataTV.setVisibility(View.VISIBLE);
                            Log.e("Error", "Failure Response");
                        }


                    } catch (Exception e) {
                        Log.e("Exception", "" + e);
                    }
                }

                @Override
                public void onFailure(Call<ProductDescription> call, Throwable t) {
                    Log.e("Failure", " Response Error " + t.getMessage());
                }
            });
        } else {
            // Toast.makeText(CategoryProductsActivity.this, "" + getString(R.string.netUnavailable), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == desclikeImage) {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setId(String.valueOf(descriptionDataList.getProductDesc().getId()));
            cartProduct.setName(descriptionDataList.getProductDesc().getName());
            cartProduct.setImg_url(descriptionDataList.getProductDesc().getImage().getSrc());
            cartProduct.setPrice(descriptionDataList.getProductDesc().getVaraiants().get(0).getPrice());
            cartProduct.setQty(1);
            cartProduct.setTotal(String.valueOf(cartProduct.getQty() * Float.parseFloat(cartProduct.getPrice())));
            // Log.d("Success", "Clicked Like image:::: " + data.get(getAdapterPosition()).isCartCheck());

            if (descriptionDataList.getProductDesc().isCartCheck()) {
                Log.d("Success", "Clicked Like image");
                sqLiteHelper.deleteCart(cartProduct);
                descriptionDataList.getProductDesc().setCartCheck(false);
                cart_button.setText(getString(R.string.addtocart));
            } else {
                sqLiteHelper.addToCart(cartProduct);
                descriptionDataList.getProductDesc().setCartCheck(true);
                cart_button.setText(getString(R.string.removecart));
            }
            //descriptionDataList.notifyAll();
        } else if (v == cart_button) {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setId(String.valueOf(descriptionDataList.getProductDesc().getId()));
            cartProduct.setName(descriptionDataList.getProductDesc().getName());
            cartProduct.setImg_url(descriptionDataList.getProductDesc().getImage().getSrc());
            cartProduct.setPrice(descriptionDataList.getProductDesc().getVaraiants().get(0).getPrice());
            cartProduct.setQty(1);
            cartProduct.setTotal(String.valueOf(cartProduct.getQty() * Float.parseFloat(cartProduct.getPrice())));
            // Log.d("Success", "Clicked Like image:::: " + data.get(getAdapterPosition()).isCartCheck());


            Log.d("Success", "LLL" + descriptionDataList.getProductDesc().isCartCheck());
            if (descriptionDataList.getProductDesc().isCartCheck()) {
                Log.d("Success", "Clicked Like image delete");
                sqLiteHelper.deleteCart(cartProduct);
                descriptionDataList.getProductDesc().setCartCheck(false);
                cart_button.setText(getString(R.string.addtocart));
                Toast.makeText(getApplicationContext(), "Product removed from cart successfully", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("Success", "Clicked Like image add");
                sqLiteHelper.addToCart(cartProduct);
                descriptionDataList.getProductDesc().setCartCheck(true);
                cart_button.setText(getString(R.string.removecart));
                Toast.makeText(getApplicationContext(), "Product added to cart successfully", Toast.LENGTH_SHORT).show();
            }

        }
        loadDataFromApi(UserID);
    }
}
