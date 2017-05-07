package com.mindmade.mcom.adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mindmade.mcom.R;
import com.mindmade.mcom.activity.ProductListActivity;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.model.CategoryModel;

import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    public final int TYPE_CATEGORY = 0;
    public final int TYPE_LOAD = 1;
    private Activity mContext;
    private List<CategoryModel.Category> data;

    public CategoryAdapter(Activity context, List<CategoryModel.Category> passData) {
        mContext = context;
        data = passData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new CategoryViewHolder(inflater.inflate(R.layout.category_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryViewHolder) {
            Log.w("Success", "Data::: " + data.get(position).getResult().get("src"));
            Log.w("Success", "Data::: " + data.get(position).getName());
            Log.w("Success", "Data::: " + data.get(position).getId());
            Glide.with(mContext).load(data.get(position).getResult().get("src")).centerCrop().listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    ((CategoryViewHolder) holder).categoryAdapterProgressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    ((CategoryViewHolder) holder).categoryAdapterProgressBar.setVisibility(View.GONE);
                    return false;
                }
            }).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(((CategoryViewHolder) holder).categoryAdapterImageView);

            ((CategoryViewHolder) holder).categoryadapterTextView.setText(data.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryAdapterImageView;
        TextView categoryadapterTextView;
        ProgressBar categoryAdapterProgressBar;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            categoryAdapterImageView = (ImageView) itemView.findViewById(R.id.category_imageview);
            categoryadapterTextView = (TextView) itemView.findViewById(R.id.category_name_TV);
            categoryAdapterProgressBar = (ProgressBar) itemView.findViewById(R.id.category_progressbar);

            categoryAdapterImageView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (v == categoryAdapterImageView) {
              /*  Intent nextIntent = new Intent(mContext, CategoryActivity.class);
                nextIntent.putExtra(Const.NAME_INTENT_KEY, data.get(getAdapterPosition() - 1).getName());
                nextIntent.putExtra(Const.ID_INTENT_KEY, data.get(getAdapterPosition() - 1).getId());
                mContext.startActivity(nextIntent);*/
                Intent nextIntent = new Intent(mContext, ProductListActivity.class);
                nextIntent.putExtra(Const.ID_INTENT_KEY, String.valueOf(data.get(getAdapterPosition()).getId()));
                mContext.startActivity(nextIntent);
            }
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
}
