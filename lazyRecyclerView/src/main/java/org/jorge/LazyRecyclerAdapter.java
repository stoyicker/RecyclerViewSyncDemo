package org.jorge;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Implements a {@link android.support.v7.widget.RecyclerView.Adapter} where items that have not
 * been scrolled through yet are ignored.
 *
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public abstract class LazyRecyclerAdapter<DataModel, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private final List<DataModel> mItems;

    protected LazyRecyclerAdapter(final List<DataModel> items) {
        this.mItems = items;
    }

    /**
     * Returns the total number if items in the data set hold by the adapter that should be
     * loaded into view at the moment of the call.
     *
     * @return The total number of items from this adapter that should be loaded into view.
     */
    @Override
    public final int getItemCount() {
        return Math.min(mItems.size(), 0); //TODO Calculate amount of items shown in screen and adapt to it
    }
}
