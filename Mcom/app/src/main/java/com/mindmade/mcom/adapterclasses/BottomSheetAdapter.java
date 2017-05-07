package com.mindmade.mcom.adapterclasses;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mindmade.mcom.R;
import com.mindmade.mcom.utilclasses.model.BottomSheetItem;

import java.util.List;


public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.ViewHolder> {

    private List<BottomSheetItem> mItems;
    private ItemListener mListener;

    public BottomSheetAdapter(List<BottomSheetItem> items, ItemListener listener) {
        mItems = items;
        mListener = listener;
    }

    public void setListener(ItemListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bottom_sheet_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public BottomSheetItem item;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.bottom_sheet_titile_TV);
            textView.setOnClickListener(this);
        }

        public void setData(BottomSheetItem item) {
            this.item = item;
            textView.setText(item.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (v==textView) {
                if (mListener != null) {
                    mListener.onItemClick(item,getAdapterPosition());
                }
            }
        }
    }

    public interface ItemListener {
        void onItemClick(BottomSheetItem item, int pos);
    }
}
