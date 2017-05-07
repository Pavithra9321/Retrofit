package com.mindmade.mcom.utilclasses.api;

import android.database.Observable;

import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.model.ProductDescription;
import com.mindmade.mcom.utilclasses.model.ProductModel;
import com.mindmade.mcom.utilclasses.model.SearchModel;
import com.mindmade.mcom.utilclasses.model.Products;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public interface AllApi {
    @Headers("Content-Type: application/json")
    @GET(Const.CATEGORY_URL)
    Call<CategoryModel> getCategoriesData();

//    @GET(Const.PRODUCT_DESCRIPTION_URL)
//    Call<ProductDescription> getProductDescriptiondata();

    @Headers("Content-Type: application/json")
    @GET("products/{user_id}.json")
    Call<ProductDescription> getProductDescriptiondata(@Path("user_id") String userId );


    @GET(Const.PRODUCT_URL)
    Call<ProductModel> getProductsListData(@Query(Const.ID_INTENT_KEY) String customer_id,@Query(Const.LIMIT_KEY) String limit,@Query(Const.ORDER_KEY) String order);

    @GET(Const.PRODUCT_URL)
    Call<SearchModel> getSearchProducts(@Query(Const.TITLE_KEY) String title, @Query(Const.LIMIT_KEY) String limit, @Query(Const.FIELDS_KEY) String filelds);

}
