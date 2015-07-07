package org.jorge.lazyRecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Implements a {@link android.support.v7.widget.RecyclerView.Adapter} where items that have not
 * been scrolled through yet are ignored.
 *
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public abstract class LazyRecyclerAdapter<ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> {

    private final List mItems;

    protected LazyRecyclerAdapter(@NonNull final List items) {
        this.mItems = items;
    }

    /**
     * Intended to allow the replacement of the information provided by {@link RecyclerView
     * .Adapter#getItemCount}
     *
     * @return {@link Integer} The total number of items that this adapter holds at the moment of
     * this call.
     */
    public int getItemAmount() {
        return mItems.size();
    }

    /**
     * Returns the total number if items in the data set hold by the adapter that should be
     * loaded into view at the moment of the call.
     *
     * @return The total number of items from this adapter that should be loaded into view.
     */
    @Override
    public int getItemCount() {
        return Math.min(mItems.size(), Integer.MAX_VALUE); //TODO Calculate amount of items shown in screen and adapt to it
    }
}
