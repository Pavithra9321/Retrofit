package com.mindmade.mcom.adapterclasses;

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

import com.mindmade.mcom.R;
import com.mindmade.mcom.utilclasses.Const;
import com.mindmade.mcom.utilclasses.model.CategoryModel;

import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    public final int TYPE_CATEGORY = 0;
    public final int TYPE_LOAD = 1;
    private Context mContext;
    private List<CategoryModel> data;

    public CategoryAdapter(Context context, List<CategoryModel> passData) {
        mContext = context;
        data.addAll(passData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new CategoryViewHolder(inflater.inflate(R.layout.category_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CategoryViewHolder) {
            Log.w("Success", "Data::: " + data.get(position).getCategoryList().get(position).getResult());
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
            }
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }
}
