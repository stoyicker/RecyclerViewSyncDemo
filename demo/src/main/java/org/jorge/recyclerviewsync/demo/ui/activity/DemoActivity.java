package org.jorge.recyclerviewsync.demo.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import org.jorge.recyclerviewsync.demo.R;
import org.jorge.recyclerviewsync.demo.datamodel.DemoDataModel;
import org.jorge.recyclerviewsync.demo.datamodel.DemoDataModelFactory;
import org.jorge.recyclerviewsync.demo.ui.adapter.TabFragmentPagerAdapter;
import org.jorge.recyclerviewsync.demo.ui.fragment.TabFragment;
import org.jorge.recyclerviewsync.demo.ui.widget.FloatingActionMenu;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class DemoActivity extends AppCompatActivity {

    private TabFragmentPagerAdapter mFragmentPagerAdapter;

    private RecyclerView.Adapter mFirstAdapter, mSecondAdapter;

    @Bind(R.id.main_content)
    View mainContent;

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.sliding_tabs)
    TabLayout mTabLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.action_menu)
    FloatingActionMenu mActionMenu;

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
        initTabLayout();
        initActionMenu();
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.app_name));
        }
    }

    private void initTabLayout() {
        mViewPager.setAdapter(mFragmentPagerAdapter = new TabFragmentPagerAdapter(getApplicationContext(), getSupportFragmentManager()));

        mTabLayout.setTabsFromPagerAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                updateAdapterReferences();
            }

            @Override
            public void onTabUnselected(final TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(final TabLayout.Tab tab) {

            }
        });

        updateAdapterReferences();
    }

    private void updateAdapterReferences() {
        final TabFragment currentTabFragment = ((TabFragment) mFragmentPagerAdapter.getItem
                (mTabLayout.getSelectedTabPosition()));

        mFirstAdapter = currentTabFragment.getFirstAdapter();
        mSecondAdapter = currentTabFragment.getSecondAdapter();
    }

    private void initActionMenu() {
        mActionMenu.setOnItemClickListener(0, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                ((TabFragment) mFragmentPagerAdapter.getItem(mTabLayout.getSelectedTabPosition())).clearAllItems();
            }
        });
        mActionMenu.setOnItemClickListener(1, new View.OnClickListener() {
            @Override
            public void onClick(@NonNull final View v) {
                ((TabFragment) mFragmentPagerAdapter.getItem(mTabLayout.getSelectedTabPosition())).bulkAddItems();
            }
        });
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mResources.getConfiguration().orientation != newConfig.orientation) {
            mFirstAdapter.notifyDataSetChanged();
            mSecondAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_activity_demo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.fab_add)
    void addNewItem() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final TabFragment currentTabFragment = ((TabFragment) mFragmentPagerAdapter.getItem
                        (mTabLayout.getSelectedTabPosition()));
                final List<DemoDataModel> selected = currentTabFragment.getRecyclerItems();

                selected.add(DemoDataModelFactory.createDemoDataModel());
                mFirstAdapter.notifyItemInserted(mFirstAdapter.getItemCount() - 1);
                mSecondAdapter.notifyItemInserted(mSecondAdapter.getItemCount() - 1);
                if (selected.size() == 1) {
                    currentTabFragment.updateItemsVisibility(Boolean.TRUE);
                }
                Snackbar.make(mainContent, getApplicationContext()
                        .getResources().getQuantityString(R
                                .plurals
                                .snack_bar_text_items_added, 1), Snackbar
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
                final TabFragment currentTabFragment = ((TabFragment) mFragmentPagerAdapter.getItem
                        (mTabLayout.getSelectedTabPosition()));
                final List<DemoDataModel> selected = currentTabFragment.getRecyclerItems();

                if (!selected.isEmpty()) {
                    selected.remove(selected.size() - 1);
                    mFirstAdapter.notifyItemRemoved(mFirstAdapter.getItemCount());
                    mSecondAdapter.notifyItemRemoved(mSecondAdapter.getItemCount());
                    if (selected.isEmpty()) {
                        currentTabFragment.updateItemsVisibility(Boolean.FALSE);
                    }
                    Snackbar.make(mainContent, R.string
                            .snack_bar_text_item_removed, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
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
