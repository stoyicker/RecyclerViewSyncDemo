package org.jorge.recyclerviewsync.demo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.jorge.recyclerviewsync.demo.R;
import org.jorge.recyclerviewsync.demo.datamodel.DemoDataModel;
import org.jorge.recyclerviewsync.demo.datamodel.DemoDataModelFactory;
import org.jorge.recyclerviewsync.demo.ui.adapter.DemoRecyclerAdapter;
import org.jorge.recyclerviewsync.demo.ui.widget.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import aligningrecyclerview.AligningRecyclerView;
import aligningrecyclerview.AlignmentManager;
import butterknife.Bind;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class DemoActivity extends AppCompatActivity {

    private static final String KEY_ITEMS = "KEY_ITEMS";
    private List<DemoDataModel> mRecyclerItems = new ArrayList<>();

    @Bind(R.id.left_recycler_view)
    AligningRecyclerView mLeftRecyclerView;
    @Bind(R.id.right_recycler_view)
    AligningRecyclerView mRightRecyclerView;

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

    @BindInt(R.integer.bulk_add_amount)
    int mBulkAddAmount;

    Resources mResources;

    @BindString(R.string.repo_url)
    String mRepoUrl;

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

        mLeftRecyclerView.setAdapter(mLeftAdapter = new DemoRecyclerAdapter(mRecyclerItems));
        mLeftRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mLeftRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRightRecyclerView.setAdapter(mRightAdapter = new DemoRecyclerAdapter(mRecyclerItems));
        mRightRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRightRecyclerView.setItemAnimator(new DefaultItemAnimator());

        AlignmentManager.join(mLeftRecyclerView, mRightRecyclerView);
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
                    }
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
                Snackbar.make(mRootView, mResources.getQuantityString(R.plurals.snack_bar_text_items_added, mBulkAddAmount, mBulkAddAmount), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void updateItemsVisibility(final boolean recyclerViewHasItems) {
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

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_activity_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_github:
                openRepo();
                return Boolean.TRUE;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openRepo() {
        final Intent ghIntent = new Intent(Intent.ACTION_VIEW);
        ghIntent.setData(Uri.parse(mRepoUrl));
        startActivity(ghIntent);
    }
}
