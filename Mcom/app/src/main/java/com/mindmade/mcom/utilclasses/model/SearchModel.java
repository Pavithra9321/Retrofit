package com.mindmade.mcom.utilclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mindmade.mcom.utilclasses.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public class SearchModel {
    @SerializedName(Const.PRODUCT_ARRAY_KEY)
    @Expose
    private List<SearchModel.Search> searchList;

    public List<Search> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Search> searchList) {
        this.searchList = searchList;
    }

    public class Search{
        @SerializedName(Const.TITLE_KEY)
        String name;
        @SerializedName(Const.ID_KEY)
        float id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getId() {
            return id;
        }

        public void setId(float id) {
            this.id = id;
        }
    }
}
