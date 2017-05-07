package com.mindmade.mcom.utilclasses.model;


public class BottomSheetItem {


    private String mTitle;
    private int mId;
    private String mKey;



    public BottomSheetItem(String title, int id,String key) {
        mTitle = title;
        mId=id;
        mKey=key;
    }

    public int getmId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getmKey() {
        return mKey;
    }
}
