package com.mindmade.mcom.utilclasses.model;

import com.google.gson.annotations.SerializedName;
import com.mindmade.mcom.utilclasses.Const;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class Products {
    @SerializedName(Const.PRODUCT_ID_KEY)
   int productid;
    @SerializedName(Const.PRODUCT_CAT_ID_KEY)
    int productCatId;
    @SerializedName(Const.PRODUCT_NAME_KEY)
    String productName;
    @SerializedName(Const.PRODUCT_DESC_KEY)
    String productDesc;
    @SerializedName(Const.PRODUCT_IMAGE_KEY)
    String productImgUrl;
    @SerializedName(Const.PRODUCT_PRICE_KEY)
    String productPrice;
    @SerializedName(Const.TYPE_KEY)
    String type;
    @SerializedName(Const.PRODUCT_OFF_PRICE_KEY)
    String offerPrice;

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Products(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getProductCatId() {
        return productCatId;
    }

    public void setProductCatId(int productCatId) {
        this.productCatId = productCatId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductImgUrl() {
        return productImgUrl;
    }

    public void setProductImgUrl(String productImgUrl) {
        this.productImgUrl = productImgUrl;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
