package org.jorge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public class LazyRecyclerView extends RecyclerView {

    private Integer mLastY;

    public LazyRecyclerView(@NonNull Context context, @NonNull final LinearLayoutManager layoutManager) {
        this(context, null, layoutManager);
    }

    public LazyRecyclerView(@NonNull final Context context, @Nullable final AttributeSet attrs, @NonNull final LinearLayoutManager layoutManager) {
        this(context, attrs, 0, layoutManager);
    }

    /**
     * Sets an scroll listener to detect gestures that require loading more data.
     *
     * @param context       {@link Context}
     * @param attrs         {@link AttributeSet}
     * @param defStyle      {@link Integer}
     * @param layoutManager {@link LinearLayoutManager} In order to find which items are shown, the layout manager is required to be of (or extend) this type.
     */
    public LazyRecyclerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyle, @NonNull final LinearLayoutManager layoutManager) {
        super(context, attrs, defStyle);

        this.mLastY = 0;
        this.setLayoutManager(layoutManager);

//        addOnScrollListener(new OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(final RecyclerView recyclerView, final int newState) {
//                final int currentFirstVisibleItem = recyclerView.getLayoutManager().findgetFirstVisiblePosition();
//
//                if (currentFirstVisibleItem > mLastFirstVisibleItem) {
//                    mIsScrollingUp = false;
//                }
//                else {
//                    if (currentFirstVisibleItem < mLastFirstVisibleItem) {
//                        mIsScrollingUp = true;
//                    }
//                }
//
//                mLastFirstVisibleItem = currentFirstVisibleItem;
//            }
//
//            @Override
//            public void onScrolled(final RecyclerView recyclerView, final int dx, final int dy) {
//                mLastY += dy;
//            }
//        });
    }
}
