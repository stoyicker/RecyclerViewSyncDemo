package org.jorge.recyclerviewsync.demo.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.jorge.recyclerviewsync.demo.R;
import org.jorge.recyclerviewsync.demo.ui.fragment.HorizontalTabFragment;
import org.jorge.recyclerviewsync.demo.ui.fragment.VerticalTabFragment;

import hugo.weaving.DebugLog;

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private final Fragment[] mFragments;
    private final String[] mTabNames;
    private Context mContext;

    public TabFragmentPagerAdapter(final Context context, final android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);
        mTabNames = context.getResources().getStringArray(R.array.tab_names);
        mFragments = new Fragment[mTabNames.length];
        mContext = context;
    }

    @DebugLog
    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0:
                if (mFragments[position] == null) {
                    mFragments[position] = Fragment.instantiate(mContext, VerticalTabFragment.class.getName());
                }
                break;
            case 1:
                if (mFragments[position] == null) {
                    mFragments[position] = Fragment.instantiate(mContext, HorizontalTabFragment.class.getName());
                }
                break;
            default:
                throw new IllegalArgumentException("Illegal page index for the pager adapter: " +
                        position);
        }


        return mFragments[position];
    }

    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }
}
