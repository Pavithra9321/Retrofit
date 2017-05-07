package com.mindmade.mcom.utilclasses.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindmade.mcom.utilclasses.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies on 07-05-2017.
 */
public class ProductDescription {
    @SerializedName
   (Const.PRODUCT_DESC_ARRAY_KEY)
    @Expose
    private productDesc productDesc;

    /**
     * @return The contacts
     */
    public productDesc getProductDesc() {
        return productDesc;
    }


    public void setProductDesc(productDesc productDesc) {
        this.productDesc = productDesc;
    }


    //    //
//    @SerializedName(Const.PRODUCT_DESC_ARRAY_KEY)
//    List<Products> productList;
//
//    public List<Products> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Products> productList) {
//        this.productList = productList;
//    }
////
    public class productDesc{
        @SerializedName(Const.TITLE_KEY)
        String name;
        @SerializedName(Const.DESC_KEY)
        String description;
        @Expose
        @SerializedName(Const.ID_KEY)
        Long id;

        boolean cartCheck;

        public boolean isCartCheck() {
            return cartCheck;
        }
        public void setCartCheck(boolean cartCheck) {
            this.cartCheck = cartCheck;
        }


        @SerializedName(Const.IMAGE_OBJECT_KEY)
        public ProductModel.Products.Image image;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @SerializedName(Const.PRODUCT_VARAINTS)

        private ArrayList<ProductModel.Products.Variants> varaiants = new ArrayList<>();



        /**
         * @return The contacts
         */
        public ArrayList<ProductModel.Products.Variants> getVaraiants() {
            return varaiants;
        }


        public void setVaraiants(ArrayList<ProductModel.Products.Variants> varaiants) {
            this.varaiants = varaiants;
        }


        public ProductModel.Products.Image getImage(){
            return image;
        }

        public void setImage(ProductModel.Products.Image image){
            this.image=image;
        }



        public class Variants{
            @SerializedName(Const.PRODUCT_PRICE)
            String price;

            @SerializedName(Const.PRODUCTS_ACTUAL_PRICE)
            String actualprice;

            public String getActualprice() {
                return actualprice;
            }

            public void setActualprice(String actualprice) {
                this.actualprice = actualprice;
            }
            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

    }


        public class Image {

            @SerializedName(Const.PRODUCT_ID_KEY)
            Long product_id;

            @SerializedName(Const.PRODUCT_IMAGE_ID)
            Long img_id;

            @SerializedName(Const.PRODUCT_POSITON_ID)
            Long position_id;

            @SerializedName(Const.CREATED_AT)
            String create;

            @SerializedName(Const.UPDATED_AT)
            String update;

            @SerializedName(Const.SRC_KEY)
            String src;


            public Long getProduct_id() {
                return product_id;
            }

            public void setProduct_id(Long product_id) {
                this.product_id = product_id;
            }

            public Long getImg_id() {
                return img_id;
            }

            public void setImg_id(Long img_id) {
                this.img_id = img_id;
            }

            public float getPosition_id() {
                return position_id;
            }

            public void setPosition_id(Long position_id) {
                this.position_id = position_id;
            }


            public String getCreate() {
                return create;
            }

            public void setCreate(String create) {
                this.create = create;
            }

            public String getUpdate() {
                return update;
            }

            public void setUpdate(String update) {
                this.update = update;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }



}
