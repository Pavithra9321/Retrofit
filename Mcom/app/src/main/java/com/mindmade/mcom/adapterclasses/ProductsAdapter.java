package com.mindmade.mcom.adapterclasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mindmade.mcom.R;
import com.mindmade.mcom.utilclasses.model.Products;

import java.util.ArrayList;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class ProductsAdapter {
//        extends RecyclerView.Adapter {
//
//    public final int TYPE_PRODUCT = 0;
//    public final int TYPE_LOAD = 1;
//    public final int TYPE_NO_DATA = 2;
//    ArrayList<Products> data;
//    // static Context context;
//    // List<MovieModel> movies;
//    OnLoadMoreListener loadMoreListener;
//    boolean isLoading = false, isMoreDataAvailable = true;
//    private Context mContext;
//
//
//    public ProductsAdapter(Context context, ArrayList<Products> data) {
//        this.data = data;
//        mContext = context;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        if (viewType == TYPE_PRODUCT) {
//            return new ProductsViewHolder(inflater.inflate(R.layout.product_adapter, parent, false));
//        } else if (viewType == TYPE_LOAD) {
//            return new LoadHolder(inflater.inflate(R.layout.bottom_loading, parent, false));
//        } else {
//            return new NoDataHolder(inflater.inflate(R.layout.no_data_layout, parent, false));
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//        Log.d("Success", "Position::: " + position);
//        Log.d("Success", "getItemCount::: " + (getItemCount() - 1));
//        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
//            isLoading = true;
//            loadMoreListener.onLoadMore();
//        }
//        if (getItemViewType(position) == TYPE_PRODUCT) {
//            ((ProductsViewHolder) holder).bindData(data.get(position));
//        }
//
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//        if (data.get(position).getType().equals(Const.LATERST_TYPE_VALUE) || data.get(position).getType().equals(Const.CATEGORY_TYPE_VALUE)) {
//            return TYPE_PRODUCT;
//        } else if (data.get(position).getType().equals(Const.LOAD_TYPE_VALUE)) {
//            return TYPE_LOAD;
//        } else {
//            return TYPE_NO_DATA;
//        }
//    }
//
//    /* notifyDataSetChanged is final method so we can't override it
//            call bottom_sheet_adapter.notifyDataChanged(); after update the list*/
//    public void notifyDataChanged() {
//        notifyDataSetChanged();
//        isLoading = false;
//    }
//
//    public void setMoreDataAvailable(boolean moreDataAvailable) {
//        isMoreDataAvailable = moreDataAvailable;
//    }
//
//    public boolean isLoading() {
//        return isLoading;
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//
//    public interface OnLoadMoreListener {
//        void onLoadMore();
//    }
//
//    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
//        this.loadMoreListener = loadMoreListener;
//        Log.d("Success", "Comes setLoadMoreListener");
//    }
//
//    class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        ImageView productImageview;
//        TextView productNameTV, productOfferPriceTV, productActulPriceTV;
//        LinearLayout bottomLayout;
//
//        public ProductsViewHolder(View itemView) {
//            super(itemView);
//
//            productImageview = (ImageView) itemView.findViewById(R.id.product_adapter_imageview);
//            productNameTV = (TextView) itemView.findViewById(R.id.product_adapter_name_TV);
//            productOfferPriceTV = (TextView) itemView.findViewById(R.id.product_adapter_offer_price_TV);
//            productActulPriceTV = (TextView) itemView.findViewById(R.id.product_adapter_actual_price_TV);
//            bottomLayout = (LinearLayout) itemView.findViewById(R.id.product_adapter_bottom_layout);
//
//            productImageview.setOnClickListener(this);
//            bottomLayout.setOnClickListener(this);
//
//
//        }
//
//        void bindData(Products object) {
//            productNameTV.setText(object.getProductName());
//            productOfferPriceTV.setText(mContext.getString(R.string.rs_symbol)+" "+object.getOfferPrice());
//            productActulPriceTV.setText(mContext.getString(R.string.rs_symbol)+" "+object.getProductPrice());
//            productActulPriceTV.setPaintFlags(productActulPriceTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//            Glide
//                    .with(mContext)
//                    .load(object.getProductImgUrl())
//                    .placeholder(R.mipmap.ic_grasp_lancher)
//                    .crossFade()
//                    .dontAnimate()
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(productImageview);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (v == productImageview || v == bottomLayout) {
//                Intent nextIntent = new Intent(mContext, ProductDescriptionActivity.class);
//                nextIntent.putExtra(Const.PRODUCT_NAME_INTENT_KEY, data.get(getAdapterPosition()).getProductName());
//                nextIntent.putExtra(Const.PRODUCT_ID_INTENT_KEY, data.get(getAdapterPosition()).getProductid());
//                mContext.startActivity(nextIntent);
//            }
//        }
//    }
//
//    static class LoadHolder extends RecyclerView.ViewHolder {
//        public LoadHolder(View itemView) {
//            super(itemView);
//        }
//    }
//
//    static class NoDataHolder extends RecyclerView.ViewHolder {
//        public NoDataHolder(View itemView) {
//            super(itemView);
//        }
//    }
}

