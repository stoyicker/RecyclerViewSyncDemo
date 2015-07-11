package org.jorge.recyclerviewsync.demo.ui.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public class SelfRemovingOnScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public final void onScrollStateChanged(@NonNull final RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView.removeOnScrollListener(this);
        }
    }
}
