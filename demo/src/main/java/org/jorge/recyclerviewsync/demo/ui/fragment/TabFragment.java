package org.jorge.recyclerviewsync.demo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jorge.recyclerviewsync.demo.R;
import org.jorge.recyclerviewsync.demo.datamodel.DemoDataModel;
import org.jorge.recyclerviewsync.demo.datamodel.DemoDataModelFactory;
import org.jorge.recyclerviewsync.demo.ui.activity.DemoActivity;
import org.jorge.recyclerviewsync.demo.ui.adapter.DemoRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import aligningrecyclerview.AligningRecyclerView;
import aligningrecyclerview.AlignmentManager;
import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public abstract class TabFragment extends Fragment {

    private static final String STATE_KEY_ITEMS = DemoActivity.class.getName() + ".STATE_KEY_ITEMS";

    static final int PAGE_VERTICAL = R.layout.tab_vertical, PAGE_HORIZONTAL = R.layout.tab_horizontal;

    public RecyclerView.Adapter getFirstAdapter() {
        return mFirstAdapter;
    }

    public RecyclerView.Adapter getSecondAdapter() {
        return mSecondAdapter;
    }

    @IntDef({PAGE_VERTICAL, PAGE_HORIZONTAL})
    @interface Page {
    }

    private final
    @Page
    int mLayoutResource;

    @Bind(R.id.empty)
    View mEmptyView;

    @Bind(R.id.fragment_main_content)
    View mRootView;

    @Bind(R.id.left_recycler_view)
    AligningRecyclerView mFirstRecyclerView;

    @Bind(R.id.right_recycler_view)
    AligningRecyclerView mSecondRecyclerView;

    private final RecyclerView.Adapter mFirstAdapter, mSecondAdapter;

    @Bind(R.id.recycler_view_container)
    View mRecyclerViewContainer;

    @BindInt(R.integer.bulk_add_amount)
    int mBulkAddAmount;

    private List<DemoDataModel> mRecyclerItems = new ArrayList<>();

    @DebugLog
    TabFragment(final @Page int page) {
        mLayoutResource = page;

        mFirstAdapter = new DemoRecyclerAdapter(mRecyclerItems);
        mSecondAdapter = new DemoRecyclerAdapter(mRecyclerItems);
    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(mLayoutResource, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    @DebugLog
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerViews(mLayoutResource);
    }

    private void initRecyclerViews(final @TabFragment.Page int page) {
        final Context context = getActivity().getApplicationContext();

        mFirstRecyclerView.setAdapter(mFirstAdapter);
        mFirstRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSecondRecyclerView.setAdapter(mSecondAdapter);
        mSecondRecyclerView.setItemAnimator(new DefaultItemAnimator());

        final @AligningRecyclerView.AlignOrientation int orientation;

        if (page == TabFragment.PAGE_VERTICAL) {
            mFirstRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            mSecondRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            orientation = AligningRecyclerView.ALIGN_ORIENTATION_VERTICAL;
        }
        else {
            mFirstRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            mSecondRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            orientation = AligningRecyclerView.ALIGN_ORIENTATION_HORIZONTAL;
        }

        AlignmentManager.join(orientation, mFirstRecyclerView, mSecondRecyclerView);
    }

    public void clearAllItems() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mRecyclerItems.clear();
                mFirstAdapter.notifyDataSetChanged();
                mSecondAdapter.notifyDataSetChanged();
                updateItemsVisibility(Boolean.FALSE);
                Snackbar.make(mRootView, R.string.snack_bar_text_items_cleared, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public void bulkAddItems() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                final Integer initialIndex = mRecyclerItems.size() - 1, finalIndex = initialIndex + mBulkAddAmount;
                for (Integer i = 0; i < mBulkAddAmount; i++)
                    mRecyclerItems.add(DemoDataModelFactory.createDemoDataModel());
                mFirstAdapter.notifyItemRangeInserted(initialIndex, finalIndex);
                mSecondAdapter.notifyItemRangeInserted(initialIndex, finalIndex);
                if (mRecyclerItems.size() == mBulkAddAmount) {
                    updateItemsVisibility(Boolean.TRUE);
                }
                Snackbar.make(mRootView, getContext().getResources().getQuantityString(R.plurals.snack_bar_text_items_added, mBulkAddAmount, mBulkAddAmount), Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @DebugLog
    public void updateItemsVisibility(final boolean recyclerViewHasItems) {
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
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        outState.putParcelableArrayList(STATE_KEY_ITEMS, (ArrayList<? extends Parcelable>) mRecyclerItems);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            final List<DemoDataModel> items = savedInstanceState.getParcelableArrayList
                    (STATE_KEY_ITEMS);

            if (items != null) {
                mRecyclerItems.addAll(items);
            }
        }
    }

    public List<DemoDataModel> getRecyclerItems() {
        return mRecyclerItems;
    }
}
