package com.mindmade.mcom.adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mindmade.mcom.R;
import com.mindmade.mcom.activity.ProductDescriptionActivity;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.model.ProductDescription;
import com.mindmade.mcom.utilclasses.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class ProductsAdapter  extends RecyclerView.Adapter  {
    public final int TYPE_PRODUCT = 0;
    public final int TYPE_CATEGORY = 0;
    public final int TYPE_LOAD = 1;
    private Activity mContext;
    private List<ProductModel.Products> data;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;
boolean like;
    public ProductsAdapter(Activity context, ArrayList<ProductModel.Products> passData) {
        mContext = context;
        data = passData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_PRODUCT) {
            return new ProductViewHolder(inflater.inflate(R.layout.product_adapter, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new LoadHolder(inflater.inflate(R.layout.bottom_loading, parent, false));
        } else {
            return new NoDataHolder(inflater.inflate(R.layout.no_data_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductsAdapter.ProductViewHolder) {
          //  Log.w("Success", "Data::: " + data.get(position).getResult().get("src"));

            Log.w("Success", "Data::: " + data.get(position).getName());
            Log.w("Success", "Data::: " + data.get(position).getId());
            ((ProductsAdapter.ProductViewHolder) holder).productNameTV.setText(data.get(position).getName());
         ((ProductsAdapter.ProductViewHolder)holder).productOfferPriceTV.setText(data.get(position).getVaraiants().get(0).getPrice());

            Glide.with(mContext).load(data.get(position).getImage().getSrc()).fitCenter().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                ((ProductsAdapter.ProductViewHolder) holder).productAdapterProgressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                ((ProductsAdapter.ProductViewHolder) holder).productAdapterProgressBar.setVisibility(View.GONE);
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(((ProductsAdapter.ProductViewHolder) holder).productImageview);

//            if (like==true) {
//                ((ProductsAdapter.ProductViewHolder) holder).productlikeImage.setImageResource(R.drawable.like_gray);
//                // likeTv++;
//            } else {
//                ((ProductsAdapter.ProductViewHolder) holder).productlikeImage.setImageResource(R.drawable.like_red);
//                // likeTv--;
//            }

            if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
                isLoading = true;
                loadMoreListener.onLoadMore();
            }
            if (getItemViewType(position) == TYPE_PRODUCT) {
                ((ProductViewHolder) holder).getClass();
            }
        }
    }
    @Override
    public int getItemViewType(int position) {
//        if (data.get(position).equals(Const.LATERST_TYPE_VALUE) || data.get(position).getType().equals(Const.CATEGORY_TYPE_VALUE)) {
//            return TYPE_PRODUCT;
//        } else if (data.get(position).getType().equals(Const.LOAD_TYPE_VALUE)) {
//            return TYPE_LOAD;
//        } else {
//            return TYPE_NO_DATA;
//        }
        return 0;

    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public boolean isLoading() {
        return isLoading;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        Log.d("Success", "Comes setLoadMoreListener");
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImageview;
        TextView productNameTV, productOfferPriceTV, productActulPriceTV;
        LinearLayout bottomLayout;
        ImageButton productlikeImage,productlikeImageRed;
        ProgressBar productAdapterProgressBar;


        public ProductViewHolder(View itemView) {
            super(itemView);

            productImageview = (ImageView) itemView.findViewById(R.id.product_adapter_imageview);
            productNameTV = (TextView) itemView.findViewById(R.id.product_adapter_name_TV);
            productOfferPriceTV = (TextView) itemView.findViewById(R.id.product_adapter_offer_price_TV);
            productActulPriceTV = (TextView) itemView.findViewById(R.id.product_adapter_actual_price_TV);
            bottomLayout = (LinearLayout) itemView.findViewById(R.id.product_adapter_bottom_layout);
            productAdapterProgressBar = (ProgressBar) itemView.findViewById(R.id.product_progressbar);
            productlikeImage= (ImageButton) itemView.findViewById(R.id.likeImage);
            productlikeImageRed= (ImageButton) itemView.findViewById(R.id.likeImageRed);

            productImageview.setOnClickListener(this);
            productlikeImage.setOnClickListener(this);
            productlikeImageRed.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v == productImageview ) {
                Log.d("Success","MMM"+data.get(getAdapterPosition() - 1).getId());
               Intent nextIntent = new Intent(mContext, ProductDescriptionActivity.class);
               //nextIntent.putExtra(Const.NAME_INTENT_KEY, data.get(getAdapterPosition() - 1).getName());
                nextIntent.putExtra(Const.PRODUCT_ID_KEY, data.get(getAdapterPosition() - 1).getId());
                mContext.startActivity(nextIntent);
            }else if(v== productlikeImage) {
                productlikeImageRed.setVisibility(View.VISIBLE);
                productlikeImage.setVisibility(View.GONE);

            }
            else if(v==productlikeImageRed){
                productlikeImageRed.setVisibility(View.GONE);
                productlikeImage.setVisibility(View.VISIBLE);
            }
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    static class NoDataHolder extends RecyclerView.ViewHolder {
        public NoDataHolder(View itemView) {
            super(itemView);
        }
    }
}
