package org.jorge.recyclerviewsync.demo.ui.listener;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public class SelfRemovingOnScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public final void onScrollStateChanged(@NonNull final RecyclerView recyclerView, final int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        Log.d("JORGETEST", "OnScrollStateChanged called");
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Log.d("JORGETEST", "onScrollListener removed");
            recyclerView.removeOnScrollListener(this);
        }
    }
}
