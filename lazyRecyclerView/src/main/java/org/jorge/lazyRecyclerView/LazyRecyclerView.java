package org.jorge.lazyRecyclerView;

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

    public LazyRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LazyRecyclerView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Sets an scroll listener to detect gestures that require loading more data.
     *
     * @param context  {@link Context}
     * @param attrs    {@link AttributeSet}
     * @param defStyle {@link Integer}
     */
    public LazyRecyclerView(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        this.mLastY = 0;

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

    @Override
    public void setLayoutManager(@NonNull final LayoutManager layout) {
        if (!(layout instanceof LinearLayoutManager)) {
            throw new IllegalArgumentException("LazyRecylerView only supports layout managers of " +
                    "type LinearLayoutManager (and descendants).");
        }
        super.setLayoutManager(layout);
    }
}
