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

    public class Products {
        @SerializedName(Const.TITLE_KEY)
        String name;
        @SerializedName(Const.ID_KEY)
        float id;

        @SerializedName(Const.IMAGE_OBJECT_KEY)
        public Image image;

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

        public void setId(float id) {
            this.id = id;
        }

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public class Image {

            @SerializedName(Const.PRODUCT_ID_KEY)
            long product_id;

            @SerializedName(Const.PRODUCT_IMAGE_ID)
            long img_id;

            @SerializedName(Const.PRODUCT_POSITON_ID)
            long position_id;

            @SerializedName(Const.CREATED_AT)
            String create;

            @SerializedName(Const.UPDATED_AT)
            String update;

            @SerializedName(Const.SRC_KEY)
            String src;


            public long getProduct_id() {
                return product_id;
            }

            public void setProduct_id(long product_id) {
                this.product_id = product_id;
            }

            public long getImg_id() {
                return img_id;
            }

            public void setImg_id(long img_id) {
                this.img_id = img_id;
            }

            public long getPosition_id() {
                return position_id;
            }

            public void setPosition_id(long position_id) {
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



