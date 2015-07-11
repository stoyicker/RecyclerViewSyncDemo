package org.jorge.lazyrecyclerview.demo.ui.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import org.jorge.lazyrecyclerview.demo.R;
import org.jorge.lazyrecyclerview.demo.datamodel.DemoDataModel;
import org.jorge.lazyrecyclerview.demo.datamodel.DemoDataModelFactory;
import org.jorge.lazyrecyclerview.demo.ui.adapter.DemoRecyclerAdapter;
import org.jorge.lazyrecyclerview.demo.ui.adapter.DemoRecyclerAdapter.IAdapterMethodCallListener;
import org.jorge.lazyrecyclerview.demo.ui.listener.SelfRemovingOnScrollListener;
import org.jorge.lazyrecyclerview.demo.ui.widget.FloatingActionMenu;
import org.jorge.lazyrecyclerview.demo.ui.widget.StatsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class DemoActivity extends AppCompatActivity implements IAdapterMethodCallListener {

    private static final String KEY_ITEMS = "KEY_ITEMS";
    private List<DemoDataModel> mRecyclerItems = new ArrayList<>();

    @Bind(R.id.traditional_recycler_view)
    RecyclerView mTraditionalRecyclerView;
    @Bind(R.id.lazy_recycler_view)
    RecyclerView mLazyRecyclerView;

    RecyclerView.Adapter mLeftAdapter, mRightAdapter;

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

    @Bind(R.id.traditional_stats_view)
    StatsView mTraditionalStatsView;

    @Bind(R.id.lazy_stats_view)
    StatsView mLazyStatsView;

    @Bind(R.id.length_view)
    TextView mLengthTextView;

    @BindInt(R.integer.bulk_add_amount)
    int mBulkAddAmount;

    Resources mResources;

    private final RecyclerView.OnScrollListener mTraditionalOSL = new SelfRemovingOnScrollListener() {
        @Override
        public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mLazyRecyclerView.scrollBy(dx, dy);
        }
    }, mLazyOSL = new SelfRemovingOnScrollListener() {

        @Override
        public void onScrolled(@NonNull final RecyclerView recyclerView, final int dx, final int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mTraditionalRecyclerView.scrollBy(dx, dy);
        }
    };

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

    private void initRecyclerViews() {
        final Context context = getApplicationContext();

        mTraditionalRecyclerView.setAdapter(mLeftAdapter = new DemoRecyclerAdapter(mRecyclerItems, this));
        mTraditionalRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTraditionalRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTraditionalRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull final RecyclerView rv, @NonNull final
            MotionEvent e) {
                final Boolean ret = rv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE;
                if (!ret) {
                    onTouchEvent(mTraditionalRecyclerView, e);
                }
                return Boolean.FALSE;
            }

            @Override
            public void onTouchEvent(@NonNull final RecyclerView rv, @NonNull final MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    mTraditionalRecyclerView.addOnScrollListener(mTraditionalOSL);
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {

            }
        });

        mLazyRecyclerView.setAdapter(mRightAdapter = new DemoRecyclerAdapter(mRecyclerItems, this));
        mLazyRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLazyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLazyRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull final RecyclerView rv, @NonNull final
            MotionEvent e) {
                final Boolean ret = rv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE;
                if (!ret) {
                    onTouchEvent(mLazyRecyclerView, e);
                }
                return Boolean.FALSE;
            }

            @Override
            public void onTouchEvent(@NonNull final RecyclerView rv, @NonNull final MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN) {
                    mLazyRecyclerView.addOnScrollListener(mLazyOSL);
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {

            }
        });
    }

    private void initActionMenu() {
        mActionMenu.setOnItemClickListener(0, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                clearAllItems();
                mTraditionalStatsView.resetStats();
                mLazyStatsView.resetStats();
            }
        });
        mActionMenu.setOnItemClickListener(1, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                bulkAddItems();
            }
        });
    }

    private void updateLengthView() {
        mLengthTextView.setText(getString(R.string.item_length, mRecyclerItems.size()));
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mResources.getConfiguration().orientation != newConfig.orientation) {
            mLeftAdapter.notifyDataSetChanged();
            mRightAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.fab_add)
    void addNewItem() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mRecyclerItems.add(DemoDataModelFactory.createDemoDataModel());
                mLeftAdapter.notifyItemInserted(mLeftAdapter.getItemCount() - 1);
                mRightAdapter.notifyItemInserted(mRightAdapter.getItemCount() - 1);
                if (mRecyclerItems.size() == 1) {
                    updateItemsVisibility(Boolean.TRUE);
                }
                updateLengthView();
                Snackbar.make(mRootView, mResources.getQuantityString(R.plurals.snack_bar_text_items_added, 1), Snackbar
                        .LENGTH_SHORT)
                        .show();
            }
        });
    }

    @OnClick(R.id.fab_remove)
    public void removeItem() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (!mRecyclerItems.isEmpty()) {
                    mRecyclerItems.remove(mRecyclerItems.size() - 1);
                    mLeftAdapter.notifyItemRemoved(mLeftAdapter.getItemCount());
                    mRightAdapter.notifyItemRemoved(mRightAdapter.getItemCount());
                    if (mRecyclerItems.isEmpty()) {
                        updateItemsVisibility(Boolean.FALSE);
                        mTraditionalStatsView.resetStats();
                        mLazyStatsView.resetStats();
                    }
                    updateLengthView();
                    Snackbar.make(mRootView, R.string.snack_bar_text_item_removed, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void clearAllItems() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mRecyclerItems.clear();
                mLeftAdapter.notifyDataSetChanged();
                mRightAdapter.notifyDataSetChanged();
                updateLengthView();
                updateItemsVisibility(Boolean.FALSE);
                Snackbar.make(mRootView, R.string.snack_bar_text_items_cleared, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void bulkAddItems() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final Integer initialIndex = mRecyclerItems.size() - 1, finalIndex =
                        initialIndex + mBulkAddAmount;
                for (Integer i = 0; i < mBulkAddAmount; i++)
                    mRecyclerItems.add(DemoDataModelFactory.createDemoDataModel());
                mLeftAdapter.notifyItemRangeInserted(initialIndex, finalIndex);
                mRightAdapter.notifyItemRangeInserted(initialIndex, finalIndex);
                if (mRecyclerItems.size() == mBulkAddAmount) {
                    updateItemsVisibility(Boolean.TRUE);
                }
                updateLengthView();
                Snackbar.make(mRootView, mResources.getQuantityString(R.plurals.snack_bar_text_items_added, mBulkAddAmount, mBulkAddAmount), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void updateItemsVisibility(@NonNull final Boolean recyclerViewHasItems) {
        if (recyclerViewHasItems) {
            if (mEmptyView.getVisibility() != View.GONE) {
                mEmptyView.setVisibility(View.GONE);
            }
            if (mRecyclerViewContainer.getVisibility() != View.VISIBLE) {
                mRecyclerViewContainer.setVisibility(View.VISIBLE);
            }
            if (mLengthTextView.getVisibility() != View.VISIBLE) {
                mLengthTextView.setVisibility(View.VISIBLE);
            }
        }
        else {
            if (mEmptyView.getVisibility() != View.VISIBLE) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
            if (mRecyclerViewContainer.getVisibility() != View.GONE) {
                mRecyclerViewContainer.setVisibility(View.GONE);
            }
            if (mLengthTextView.getVisibility() != View.GONE) {
                mLengthTextView.setVisibility(View.GONE);
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

    @Override
    public void onOCVCall(@NonNull final RecyclerView.Adapter src) {
        if (src == mLeftAdapter) {
            mTraditionalStatsView.increaseOnCreateViewholderCalls(1);
        }
        else {
            if (src == mRightAdapter) {
                mLazyStatsView.increaseOnCreateViewholderCalls(1);
            }
        }
    }

    @Override
    public void onOBVCall(@NonNull final RecyclerView.Adapter src) {
        if (src == mLeftAdapter) {
            mTraditionalStatsView.increaseOnBindViewholderCalls(1);
        }
        else {
            if (src == mRightAdapter) {
                mLazyStatsView.increaseOnBindViewholderCalls(1);
            }
        }
    }
}
