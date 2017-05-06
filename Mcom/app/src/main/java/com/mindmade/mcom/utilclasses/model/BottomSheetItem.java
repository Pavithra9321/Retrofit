package com.mindmade.mcom.utilclasses.model;


public class BottomSheetItem {


    private String mTitle;
    private int mId;

    public BottomSheetItem(String title, int id) {
        mTitle = title;
        mId=id;
    }

    public int getmId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

}
