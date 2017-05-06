package com.mindmade.mcom.utilclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindmade.mcom.utilclasses.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class ProductModel {


    @SerializedName(Const.PRODUCT_ARRAY_KEY)
    @Expose
    private ArrayList<Products> product = new ArrayList<>();

    /**
     * @return The contacts
     */
    public ArrayList<Products> getProduct() {
        return product;
    }


    public void setProduct(ArrayList<Products> product) {
        this.product = product;
    }

//
//    @SerializedName(Const.PRODUCT_ARRAY_KEY)
//    List<Products> productList;
//
//    public List<Products> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Products> productList) {
//        this.productList = productList;
//    }
//
    public class Products {
        @SerializedName(Const.TITLE_KEY)
        String name;
        @SerializedName(Const.ID_KEY)
        float id;

        @SerializedName(Const.IMAGE_OBJECT_KEY)
        private Map<Object, Image> result;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getId() {
            return id;
        }

        public void setId(Float id) {
            this.id = id;
        }
//        public Map<String, String> getResult() {
//            return result;
//        }
//
//        public void setResult(Map<String, String> result) {
//            this.result = result;
//        }


        public Map<Object, Image> getResult() {
            return result;
        }

        public void setResult(Map<Object, Image> result) {
            this.result = result;
        }

        public class Image {

            @SerializedName(Const.PRODUCT_ID_KEY)
            float product_id;

            @SerializedName(Const.PRODUCT_IMAGE_ID)
            float img_id;

            @SerializedName(Const.PRODUCT_POSITON_ID)
            float position_id;

            @SerializedName(Const.CREATED_AT)
            String create;

            @SerializedName(Const.UPDATED_AT)
            String update;

            @SerializedName(Const.SRC_KEY)
            String src;


            public float getProduct_id() {
                return product_id;
            }

            public void setProduct_id(float product_id) {
                this.product_id = product_id;
            }

            public float getImg_id() {
                return img_id;
            }

            public void setImg_id(float img_id) {
                this.img_id = img_id;
            }

            public float getPosition_id() {
                return position_id;
            }

            public void setPosition_id(float position_id) {
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



