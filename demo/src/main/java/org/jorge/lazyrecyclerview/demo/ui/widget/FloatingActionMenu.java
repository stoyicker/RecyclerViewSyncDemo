package org.jorge.lazyrecyclerview.demo.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import org.jorge.lazyrecyclerview.demo.R;
import org.jorge.lazyrecyclerview.demo.ui.extension.PushBehavior;

import butterknife.ButterKnife;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
@CoordinatorLayout.DefaultBehavior(PushBehavior.class)
public class FloatingActionMenu extends LinearLayout {

    public FloatingActionMenu(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            LayoutInflater.from(context).inflate(R.layout.widget_floating_action_menu, this);
            ButterKnife.bind(this);
        }
    }
}
