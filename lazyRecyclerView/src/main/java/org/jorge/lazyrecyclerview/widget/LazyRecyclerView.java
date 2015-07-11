package org.jorge.lazyrecyclerview.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class LazyRecyclerView extends RecyclerView {

    public LazyRecyclerView(@NonNull final Context context) {
        super(context);
    }

    public LazyRecyclerView(final @NonNull Context context, final @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LazyRecyclerView(final @NonNull Context context, final @Nullable AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }
}