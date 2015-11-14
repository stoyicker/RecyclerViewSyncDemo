package org.jorge.recyclerviewsync.demo.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import org.jorge.recyclerviewsync.demo.R;
import org.jorge.recyclerviewsync.demo.ui.fragment.TabFragment;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] mTabNames;
    private Context mContext;

    public TabFragmentPagerAdapter(final Context context, final android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);
        mTabNames = context.getResources().getStringArray(R.array.tab_names);
        mContext = context;
    }

    @Override
    public Fragment getItem(final int position) {
        final Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putInt(TabFragment.INSTANCE_KEY_PAGE, TabFragment.PAGE_VERTICAL);
                break;
            case 1:
                args.putInt(TabFragment.INSTANCE_KEY_PAGE, TabFragment.PAGE_HORIZONTAL);
                break;
            default:
                throw new IllegalArgumentException("Illegal page index for the pager adapter: " +
                        position);
        }

        return Fragment.instantiate(mContext, TabFragment.class.getName(), args);
    }

    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }
}
