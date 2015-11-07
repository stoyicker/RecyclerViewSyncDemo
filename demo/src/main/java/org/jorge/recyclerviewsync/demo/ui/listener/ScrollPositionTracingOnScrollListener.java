package org.jorge.recyclerviewsync.demo.ui.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class ScrollPositionTracingOnScrollListener extends RecyclerView.OnScrollListener {

    public int getScrollX() {
        return mScrollX;
    }

    public int getScrollY() {
        return mScrollY;
    }

    private int mScrollX, mScrollY;

    @Override
    public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mScrollX += dx;
        mScrollY += dy;
        Log.d("RIGHTLOG", "Scroll updated to (" + mScrollX + ", " + mScrollY + ")");
    }
}
