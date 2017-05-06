package com.mindmade.mcom.utilclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindmade.mcom.utilclasses.Const;

import java.util.List;
import java.util.Map;

/**
 * Created by Mindmade technologies.
 */

public class CategoryModel {

    @SerializedName(Const.CATEGORY_ARRAY_KEY)
    @Expose
    List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public class Category {
        @SerializedName(Const.TITLE_KEY)
        @Expose
        String name;
        @SerializedName(Const.ID_KEY)
        @Expose
        int id;
        @SerializedName(Const.IMAGE_OBJECT_KEY)
        @Expose
        private Map<String, Image> result;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Map<String, Image> getResult() {
            return result;
        }

        public void setResult(Map<String, Image> result) {
            this.result = result;
        }
    }

    public class Image {
        @SerializedName(Const.SRC_KEY)
        @Expose
        String src;


        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
