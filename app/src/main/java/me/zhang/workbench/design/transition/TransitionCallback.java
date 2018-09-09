package me.zhang.workbench.design.transition;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Transition;

/**
 * Dummy implementations of TransitionListener methods.
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public abstract class TransitionCallback implements Transition.TransitionListener {

    @Override
    public void onTransitionStart(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionPause(Transition transition) {
        // no-op
    }

    @Override
    public void onTransitionResume(Transition transition) {
        // no-op
    }
}
