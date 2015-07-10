package org.jorge.lazyrecyclerview.demo.ui.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.jorge.lazyrecyclerview.LazyRecyclerAdapter;
import org.jorge.lazyrecyclerview.demo.R;
import org.jorge.lazyrecyclerview.demo.datamodel.DemoDataModel;
import org.jorge.lazyrecyclerview.demo.datamodel.DemoDataModelFactory;
import org.jorge.lazyrecyclerview.demo.ui.adapter.DemoLazyRecyclerAdapter;
import org.jorge.lazyrecyclerview.demo.ui.adapter.TraditionalRecyclerAdapter;
import org.jorge.lazyrecyclerview.demo.ui.widget.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class DemoActivity extends AppCompatActivity {

    private static final String KEY_ITEMS = "KEY_ITEMS";
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

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recycler_view_container)
    View mRecyclerViewContainer;

    @Bind(R.id.action_menu)
    FloatingActionMenu mActionMenu;

    @BindInt(R.integer.bulk_add_amount)
    int mBulkAddAmount;

    Resources mResources;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mResources = getApplicationContext().getResources();

        initActionBar();
        initRecyclerViews();
        initActionMenu();
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    private void initActionMenu() {
        mActionMenu.setOnItemClickListener(0, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                clearAllItems();
            }
        });
        mActionMenu.setOnItemClickListener(1, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                bulkAddItems();
            }
        });
    }

    private void initRecyclerViews() {
        final Context context = getApplicationContext();

        mTraditionalRecyclerView.setAdapter(mTraditionalAdapter = new TraditionalRecyclerAdapter(mRecyclerItems));
        mTraditionalRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTraditionalRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mLazyRecyclerView.setAdapter(mLazyAdapter = new DemoLazyRecyclerAdapter(mRecyclerItems));
        mLazyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLazyRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mTraditionalAdapter.notifyDataSetChanged();
        mLazyAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fab_add)
    void addNewItem() {
        mRecyclerItems.add(DemoDataModelFactory.createDemoDataModel());
        mTraditionalAdapter.notifyItemInserted(mTraditionalAdapter.getItemCount() - 1);
        mLazyAdapter.notifyItemInserted(mLazyAdapter.getItemAmount() - 1);
        if (mRecyclerItems.size() == 1) {
            updateItemsVisibility(Boolean.TRUE);
        }
        Snackbar.make(mRootView, mResources.getQuantityString(R.plurals.snack_bar_text_items_added, 1), Snackbar
                .LENGTH_SHORT)
                .show();
    }

    @OnClick(R.id.fab_remove)
    public void removeItem() {
        if (!mRecyclerItems.isEmpty()) {
            mRecyclerItems.remove(mRecyclerItems.size() - 1);
            mTraditionalAdapter.notifyItemRemoved(mTraditionalAdapter.getItemCount());
            mLazyAdapter.notifyItemRemoved(mLazyAdapter.getItemAmount());
            updateItemsVisibility(!mRecyclerItems.isEmpty());
            Snackbar.make(mRootView, R.string.snack_bar_text_item_removed, Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void clearAllItems() {
        mRecyclerItems.clear();
        mTraditionalAdapter.notifyDataSetChanged();
        mLazyAdapter.notifyDataSetChanged();
        Snackbar.make(mRootView, R.string.snack_bar_text_items_cleared, Snackbar.LENGTH_SHORT)
                .show();
    }

    private void bulkAddItems() {
        final Integer initialIndex = mRecyclerItems.size() - 1, finalIndex =
                initialIndex + mBulkAddAmount;
        for (Integer i = 0; i < mBulkAddAmount; i++)
            mRecyclerItems.add(DemoDataModelFactory.createDemoDataModel());
        mTraditionalAdapter.notifyItemRangeInserted(initialIndex, finalIndex);
        mLazyAdapter.notifyItemRangeInserted(initialIndex, finalIndex);
        if (mRecyclerItems.size() == mBulkAddAmount) {
            updateItemsVisibility(Boolean.TRUE);
        }
        Snackbar.make(mRootView, mResources.getQuantityString(R.plurals.snack_bar_text_items_added, mBulkAddAmount, mBulkAddAmount), Snackbar.LENGTH_SHORT)
                .show();
    }

    private void updateItemsVisibility(@NonNull final Boolean recyclerViewHasItems) {
        if (recyclerViewHasItems) {
            if (mEmptyView.getVisibility() != View.GONE) {
                mEmptyView.setVisibility(View.GONE);
            }
            if (mRecyclerViewContainer.getVisibility() != View.VISIBLE) {
                mRecyclerViewContainer.setVisibility(View.VISIBLE);
            }
        }
        else {
            if (mEmptyView.getVisibility() != View.VISIBLE) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
            if (mRecyclerViewContainer.getVisibility() != View.GONE) {
                mRecyclerViewContainer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull final Bundle outState) {
        outState.putParcelableArrayList(KEY_ITEMS, (ArrayList<? extends Parcelable>) mRecyclerItems);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull final Bundle savedInstanceState) {
        final List<DemoDataModel> items = savedInstanceState.getParcelableArrayList(KEY_ITEMS);

        if (items != null) {
            mRecyclerItems.addAll(items);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }
}
