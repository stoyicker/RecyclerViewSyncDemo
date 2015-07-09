package org.jorge.lazyrecyclerview.demo.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jorge.lazyrecyclerview.demo.R;
import org.jorge.lazyrecyclerview.demo.ui.extension.PushBehavior;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class StatsView extends LinearLayout {

    @Bind({R.id.title, R.id.on_create_viewholder_calls, R.id.on_bind_viewholder_calls, R.id.get_item_count_calls})
    TextView[] mTextViews;

    private Integer mOCVCallAmount = 0, mOBVCallAmount = 0, mGICCallAmount = 0;

    private Resources mResources;

    public StatsView(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            mResources = context.getResources();

            LayoutInflater.from(context).inflate(R.layout.widget_stats_view, this);
            ButterKnife.bind(this);

            init(context, attrs);
        }
    }

    private void init(@NonNull final Context context, final AttributeSet attrs) {
        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StatsView,
                0, 0);

        String fieldType;

        try {
            fieldType = a.getString(R.styleable.StatsView_sv_title);
        } finally {
            a.recycle();
        }

        if (!TextUtils.isEmpty(fieldType)) {
            getTitleView().setText(fieldType);
        }

        updateOnCreateViewholderCallView();
        updateOnBindViewholderCallView();
        updateGetItemCountCalls();
    }

    private TextView getTitleView() {
        return mTextViews[0];
    }

    private TextView getOnCreateViewholderCallsView() {
        return mTextViews[1];
    }

    private TextView getOnBindViewholderCallsView() {
        return mTextViews[2];
    }

    private TextView getGetItemCountCallsView() {
        return mTextViews[3];
    }

    public void increaseOnCreateViewholderCalls(@NonNull final Integer delta) {
        mOCVCallAmount += delta;
        updateOnCreateViewholderCallView();
    }

    public void increaseOnBindViewholderCalls(@NonNull final Integer delta) {
        mOBVCallAmount += delta;
        updateOnBindViewholderCallView();
    }

    public void increaseGetItemCountCalls(@NonNull final Integer delta) {
        mGICCallAmount += delta;
        updateGetItemCountCalls();
    }

    private void updateOnCreateViewholderCallView() {
        getOnCreateViewholderCallsView().setText(mResources.getQuantityString(R.plurals.stat_desc_ocv, mOCVCallAmount));
        redraw();
    }

    private void updateOnBindViewholderCallView() {
        getOnBindViewholderCallsView().setText(mResources.getQuantityString(R.plurals.stat_desc_obv, mOBVCallAmount));
        redraw();
    }

    private void updateGetItemCountCalls() {
        getGetItemCountCallsView().setText(mResources.getQuantityString(R.plurals.state_desc_gic, mGICCallAmount));
        redraw();
    }

    private void redraw() {
        //TODO What of this is really necessary?
        invalidate();
        requestLayout();
    }

    public void resetStats() {
        mOCVCallAmount = 0;
        mOBVCallAmount = 0;
        mGICCallAmount = 0;
        redraw();
    }
}
