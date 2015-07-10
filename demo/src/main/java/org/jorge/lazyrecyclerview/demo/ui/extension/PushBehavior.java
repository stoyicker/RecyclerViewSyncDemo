package org.jorge.lazyrecyclerview.demo.ui.extension;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author Jorge Antonio Diaz-Benito Soriano (github.com/Stoyicker).
 */
public class PushBehavior extends CoordinatorLayout.Behavior<LinearLayout> {

    public PushBehavior() {
    }

    public PushBehavior(@NonNull final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull final CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onDependentViewChanged(@NonNull final CoordinatorLayout parent, @NonNull final LinearLayout child, @NonNull final View dependency) {
        final Boolean apiCompatible = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
        if (apiCompatible) {
            final Float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
            child.setTranslationY(translationY);
        }
        return apiCompatible;
    }
}
