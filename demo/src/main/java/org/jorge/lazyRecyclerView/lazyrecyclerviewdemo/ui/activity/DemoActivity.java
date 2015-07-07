package org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.jorge.lazyRecyclerView.LazyRecyclerAdapter;
import org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.R;
import org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.adapter.DemoLazyRecyclerAdapter;
import org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.adapter.TraditionalRecyclerAdapter;
import org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.datamodel.DemoDataModel;
import org.jorge.lazyRecyclerView.lazyrecyclerviewdemo.ui.datamodel.DemoDataModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class DemoActivity extends Activity {

    private List<DemoDataModel> mRecyclerItems = new ArrayList<>();

    @Bind(R.id.traditional_recycler_view)
    RecyclerView mTraditionalRecyclerView;
    @Bind(R.id.lazy_recycler_view)
    RecyclerView mLazyRecyclerView;
    RecyclerView.Adapter mTraditionalAdapter;
    LazyRecyclerAdapter mLazyAdapter;

    @Bind(android.R.id.empty)
    View mEmptyView;

    @Bind(R.id.main_content)
    View mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerViews();
    }

    private void initRecyclerViews() {
        final Context context = getApplicationContext();
        final RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        mTraditionalRecyclerView.setAdapter(mTraditionalAdapter = new TraditionalRecyclerAdapter(mRecyclerItems));
        mTraditionalRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTraditionalRecyclerView.setItemAnimator(itemAnimator);

        mLazyRecyclerView.setAdapter(mLazyAdapter = new DemoLazyRecyclerAdapter(mRecyclerItems));
        mLazyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLazyRecyclerView.setItemAnimator(itemAnimator);
    }

    @OnClick(R.id.fab_add)
    public void addNewItem() {
        mRecyclerItems.add(DemoDataModelFactory.createDemoDataModel());
        mTraditionalAdapter.notifyItemInserted(mTraditionalAdapter.getItemCount() - 1);
        mLazyAdapter.notifyItemInserted(mLazyAdapter.getItemAmount() - 1);
        updateItemsVisibility(Boolean.TRUE);
        Snackbar.make(mRootView, R.string.snack_bar_text_new_message, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void updateItemsVisibility(@NonNull final Boolean recyclerViewHasItems) {
        if (recyclerViewHasItems) {
            if (mEmptyView.getVisibility() != View.GONE) {
                mEmptyView.setVisibility(View.GONE);
            }
            if (mTraditionalRecyclerView.getVisibility() != View.VISIBLE) {
                mTraditionalRecyclerView.setVisibility(View.VISIBLE);
            }
            if (mLazyRecyclerView.getVisibility() != View.VISIBLE) {
                mLazyRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        else {
            if (mEmptyView.getVisibility() != View.VISIBLE) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
            if (mTraditionalRecyclerView.getVisibility() != View.GONE) {
                mTraditionalRecyclerView.setVisibility(View.GONE);
            }
            if (mLazyRecyclerView.getVisibility() != View.GONE) {
                mLazyRecyclerView.setVisibility(View.GONE);
            }
        }
    }
}
