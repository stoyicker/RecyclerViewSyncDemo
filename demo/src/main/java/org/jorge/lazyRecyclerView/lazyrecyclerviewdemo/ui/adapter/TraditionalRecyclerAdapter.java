package org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.datamodel.DemoDataModel;
import org.lazyRecyclerView.lazyrecyclerviewdemo.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class TraditionalRecyclerAdapter extends RecyclerView.Adapter<TraditionalRecyclerAdapter
        .ViewHolder> {

    private final List<DemoDataModel> mItems;

    @LayoutRes
    private static final Integer ADAPTER_ITEM_LAYOUT_RES = R.layout.adapter_item_demo;

    public TraditionalRecyclerAdapter(@NonNull final List<DemoDataModel> items) {
        this.mItems = items;
    }

    @Override
    public TraditionalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                                    final int
                                                                            viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(ADAPTER_ITEM_LAYOUT_RES,
                parent,
                Boolean.FALSE);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final TraditionalRecyclerAdapter.ViewHolder holder, final int
            position) {
        final DemoDataModel item = getItem(position);
        holder.mTextView.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private DemoDataModel getItem(@NonNull final Integer position) {
        return mItems.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.entry_text)
        TextView mTextView;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
