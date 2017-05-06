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
import com.mindmade.mcom.utilclasses.model.CategoryModel;
import com.mindmade.mcom.utilclasses.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies on 06-05-2017.
 */
public class ProductsAdapter  extends RecyclerView.Adapter {
    public final int TYPE_CATEGORY = 0;
    public final int TYPE_LOAD = 1;
    private Activity mContext;
    private List<ProductModel.Products> data;

    public ProductsAdapter(Activity context, ArrayList<ProductModel.Products> passData) {
        mContext = context;
        data = passData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new ProductsAdapter.ProductViewHolder(inflater.inflate(R.layout.product_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductsAdapter.ProductViewHolder) {
          //  Log.w("Success", "Data::: " + data.get(position).getResult().get("src"));
           /* Log.w("Success", "Data::: " + data.get(position).getName());
            Log.w("Success", "Data::: " + data.get(position).getId());
            ((ProductsAdapter.ProductViewHolder) holder).productNameTV.setText(data.get(position).getName());
         ((ProductsAdapter.ProductViewHolder)holder).productOfferPriceTV.setText(data.get(position).getVaraiants().get(0).getPrice());

            Glide.with(mContext).load(data.get(position).getImage().getSrc()).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
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

//
//            ((CategoryAdapter.CategoryViewHolder) holder).categoryadapterTextView.setText(data.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView productImageview;
        TextView productNameTV, productOfferPriceTV, productActulPriceTV;
        LinearLayout bottomLayout;
        ProgressBar productAdapterProgressBar;


        public ProductViewHolder(View itemView) {
            super(itemView);

            productImageview = (ImageView) itemView.findViewById(R.id.product_adapter_imageview);
            productNameTV = (TextView) itemView.findViewById(R.id.product_adapter_name_TV);
            productOfferPriceTV = (TextView) itemView.findViewById(R.id.product_adapter_offer_price_TV);
            productActulPriceTV = (TextView) itemView.findViewById(R.id.product_adapter_actual_price_TV);
            bottomLayout = (LinearLayout) itemView.findViewById(R.id.product_adapter_bottom_layout);
            productAdapterProgressBar = (ProgressBar) itemView.findViewById(R.id.product_progressbar);
          //  ProductsAdapterImageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
//            if (v == ) {
//              /*  Intent nextIntent = new Intent(mContext, CategoryActivity.class);
//                nextIntent.putExtra(Const.NAME_INTENT_KEY, data.get(getAdapterPosition() - 1).getName());
//                nextIntent.putExtra(Const.ID_INTENT_KEY, data.get(getAdapterPosition() - 1).getId());
//                mContext.startActivity(nextIntent);*/
//            }
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
}
