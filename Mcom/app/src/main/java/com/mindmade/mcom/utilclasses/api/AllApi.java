package com.mindmade.mcom.utilclasses.api;

import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.model.ProductModel;
import com.mindmade.mcom.utilclasses.model.SearchModel;
import com.mindmade.mcom.utilclasses.model.Products

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public interface AllApi {
    @Headers("Content-Type: application/json")
    @GET(Const.CATEGORY_URL)
    Call<CategoryModel> getCategoriesData();

    @Headers("Content-Type: application/json")
    @GET(Const.PRODUCT_URL)
    Call<ProductModel> getProductsListData(@Query(Const.LIMIT_KEY) String limit);

    @GET(Const.PRODUCT_URL)
    Call<SearchModel> getSearchProducts(@Query(Const.TITLE_KEY) String title, @Query(Const.LIMIT_KEY) String limit, @Query(Const.FIELDS_KEY) String filelds);
}
