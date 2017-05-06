package com.mindmade.mcom.utilclasses.api;

import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.model.Products;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public interface AllApi {

    @GET(Const.CATEGORY_URL)
    Call<CategoryModel> getCategoriesData();

    @GET(Const.PRODUCT_URL)
    Call<Products> getProductsList();
}
