package org.jorge.lazyrecyclerview.demo.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public interface IAdapterMethodCallListener {

    void onOCVCall(@NonNull final RecyclerView.Adapter src);

    void onOBVCall(@NonNull final RecyclerView.Adapter src);

    void onGICCall(@NonNull final RecyclerView.Adapter src);
}
