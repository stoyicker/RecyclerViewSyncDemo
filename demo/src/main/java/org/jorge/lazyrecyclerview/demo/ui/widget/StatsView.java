package org.jorge.lazyrecyclerview.demo.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jorge.lazyrecyclerview.demo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public final class StatsView extends LinearLayout {

    @Bind({R.id.title, R.id.on_create_viewholder_calls, R.id.on_bind_viewholder_calls})
    TextView[] mTextViews;

    private Integer mOCVCallAmount = 0, mOBVCallAmount = 0;

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

        String title;

        try {
            title = a.getString(R.styleable.StatsView_sv_title);
        } finally {
            a.recycle();
        }

        if (!TextUtils.isEmpty(title)) {
            getTitleView().setText(title);
        }

        updateOnCreateViewholderCallView();
        updateOnBindViewholderCallView();
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

    public void increaseOnCreateViewholderCalls(@NonNull final Integer delta) {
        mOCVCallAmount += delta;
        updateOnCreateViewholderCallView();
    }

    public void increaseOnBindViewholderCalls(@NonNull final Integer delta) {
        mOBVCallAmount += delta;
        updateOnBindViewholderCallView();
    }

    private void updateOnCreateViewholderCallView() {
        getOnCreateViewholderCallsView().setText(mResources.getQuantityString(R.plurals.stat_desc_ocv, mOCVCallAmount, mOCVCallAmount));
    }

    private void updateOnBindViewholderCallView() {
        getOnBindViewholderCallsView().setText(mResources.getQuantityString(R.plurals.stat_desc_obv, mOBVCallAmount, mOBVCallAmount));
    }

    public void resetStats() {
        mOCVCallAmount = 0;
        mOBVCallAmount = 0;
        updateOnCreateViewholderCallView();
        updateOnBindViewholderCallView();
    }
}
