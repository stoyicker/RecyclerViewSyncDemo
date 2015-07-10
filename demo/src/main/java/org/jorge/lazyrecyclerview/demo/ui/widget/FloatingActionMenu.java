package org.jorge.lazyrecyclerview.demo.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import org.jorge.lazyrecyclerview.demo.R;
import org.jorge.lazyrecyclerview.demo.ui.extension.PushBehavior;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
@CoordinatorLayout.DefaultBehavior(PushBehavior.class)
public class FloatingActionMenu extends LinearLayout {

    private Boolean isExpanded = Boolean.FALSE;

    @Bind({R.id.fab_index_zero, R.id.fab_index_one})
    View[] mEntries;

    private final Context mContext;

    private OnClickListener[] mListeners;

    public FloatingActionMenu(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.widget_floating_action_menu, this);
            ButterKnife.bind(this);

            init(context, attrs);
        }
    }

    private void init(@NonNull final Context context, final AttributeSet attrs) {
        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingActionMenu,
                0, 0);

        try {
            isExpanded = a.getBoolean(R.styleable.FloatingActionMenu_fam_expanded, Boolean.FALSE);
        } finally {
            a.recycle();
        }

        mListeners = new OnClickListener[mEntries.length];

        if (isExpanded) {
            toggleExpand();
        }
    }

    @OnClick(R.id.fab_control)
    void toggleExpand() {
        if (isExpanded) {
            Integer delayMs = 50;
            for (final View x : mEntries) {
                final Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.scale_from_zero_to_regular_size_from_center);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(@NonNull final Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(@NonNull final Animation animation) {
                        x.setVisibility(INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull final Animation animation) {
                    }
                });
                anim.setInterpolator(new Interpolator() {
                    @Override
                    public float getInterpolation(final float input) {
                        return Math.abs(input - 1f);
                    }
                });
                anim.setStartOffset(delayMs);
                delayMs -= 50;
                x.startAnimation(anim);
            }
        }
        else {
            Integer delayMs = 0;
            for (final View x : mEntries) {
                final Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.scale_from_zero_to_regular_size_from_center);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(@NonNull final Animation animation) {
                        x.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(@NonNull final Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(@NonNull final Animation animation) {
                    }
                });
                anim.setStartOffset(delayMs);
                delayMs += 50;
                x.startAnimation(anim);
            }
        }
        isExpanded = !isExpanded;
    }

    @OnClick(R.id.fab_index_zero)
    void onZeroClick() {
        triggerClickOnButton(0);
    }

    @OnClick(R.id.fab_index_one)
    void onOneClick() {
        triggerClickOnButton(1);
    }

    public void setOnItemClickListener(@NonNull final Integer actionIndex, final OnClickListener
            listener) {
        mListeners[actionIndex] = listener;
    }

    private void triggerClickOnButton(@NonNull final Integer index) {
        if (mListeners[index] != null) {
            mListeners[index].onClick(mEntries[index]);
        }
    }
}