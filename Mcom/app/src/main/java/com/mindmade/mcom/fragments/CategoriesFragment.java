package com.mindmade.mcom.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mindmade.mcom.R;
import com.mindmade.mcom.adapterclasses.CategoryAdapter;
import com.mindmade.mcom.utilclasses.AppController;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.NetworkConnectionManager;
import com.mindmade.mcom.utilclasses.PrefManager;
import com.mindmade.mcom.utilclasses.api.AllApi;
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.network.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {

    RecyclerView categoryRecyclerView;
    TextView categoryNodataTV;
    ProgressBar categoryProgressbar;
    SwipeRefreshLayout categorySwipeRefreshLayout;
    NetworkConnectionManager connectionManager;
    AllApi apiInitialize;
    PrefManager sessionManger;
    List<CategoryModel> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View categories = inflater.inflate(R.layout.categories_fragment, container, false);

        connectionManager = new NetworkConnectionManager(getActivity());
        sessionManger = new PrefManager(getActivity());
        apiInitialize = ServiceGenerator.createService(AllApi.class, Const.API_VALUE,Const.PASSWORD_VALUE);

        categoryRecyclerView = (RecyclerView) categories.findViewById(R.id.common_recyclerview);
        categoryNodataTV = (TextView) categories.findViewById(R.id.common_nodata_TV);
        categoryProgressbar = (ProgressBar) categories.findViewById(R.id.common_progressbar);
        categorySwipeRefreshLayout = (SwipeRefreshLayout) categories.findViewById(R.id.common_refresh_layout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setHasFixedSize(false);

        categorySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                categorySwipeRefreshLayout.setRefreshing(false);
            }
        });
        loadDataFromApi();

        return categories;
    }

    private void loadDataFromApi() {
        if (connectionManager.isConnectingToInternet()) {
           /* categoryPager.setVisibility(View.GONE);
            categoryBannerProgressbar.setVisibility(View.GONE);
            categoryBannerNodataTV.setVisibility(View.GONE);*/
            categoryRecyclerView.setVisibility(View.GONE);
            categoryProgressbar.setVisibility(View.VISIBLE);
            categoryNodataTV.setVisibility(View.GONE);

            Call<CategoryModel> categoriesCall = apiInitialize.getCategoriesData();
            Log.w("Success", "URL::: " + categoriesCall.request().url().toString());

            categoriesCall.enqueue(new Callback<CategoryModel>() {
                @Override
                public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                    Log.w("Success", "Response::: " + new Gson().toJson(response.body()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.code()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.message()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.errorBody()));
                    Log.w("Success", "Response::: " + new Gson().toJson(response.headers()));
                    try {
                        if (response.isSuccessful()) {

                            categoryRecyclerView.setVisibility(View.VISIBLE);
                            categoryProgressbar.setVisibility(View.GONE);
                            categoryNodataTV.setVisibility(View.GONE);

                            CategoryModel categoryModel = response.body();
                           /* if (categoryModel > 0) {
                                data = new ArrayList<CategoryModel>();
                                data.add(categoryModel);
                                CategoryAdapter adapter = new CategoryAdapter(getActivity(), data);
                                categoryRecyclerView.setAdapter(adapter);
                            } else {

                            }*/
                            data = new ArrayList<CategoryModel>();
                            data.add(categoryModel);
                            CategoryAdapter adapter = new CategoryAdapter(getActivity(), data);
                            categoryRecyclerView.setAdapter(adapter);
                        } else {
                            categoryRecyclerView.setVisibility(View.GONE);
                            categoryProgressbar.setVisibility(View.GONE);
                            categoryNodataTV.setVisibility(View.VISIBLE);
                            categoryNodataTV.setText("Un successfull response");
                        }
                    } catch (Exception e) {
                        categoryNodataTV.setVisibility(View.VISIBLE);
                        categoryNodataTV.setText("Exception");
                        Log.e("Exception", "" + e);
                    }
                }

                @Override
                public void onFailure(Call<CategoryModel> call, Throwable t) {
                    Log.e("Failure", " Response Error " + t.getMessage());
                }
            });
        } else {
            /*categoryPager.setVisibility(View.GONE);
            categoryBannerProgressbar.setVisibility(View.GONE);
            categoryBannerNodataTV.setVisibility(View.GONE);*/
            categoryRecyclerView.setVisibility(View.GONE);
            categoryProgressbar.setVisibility(View.GONE);
            categoryNodataTV.setVisibility(View.VISIBLE);
            //categoryNodataTV.setText(getString(R.string.netUnavailable));
        }
    }
}
