package com.lyt.baseadapter.mylibrary.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class SlideInBottomAnimation  implements BaseAnimation {
    public SlideInBottomAnimation() {
    }

    public Animator[] getAnimators(View view) {
        return new Animator[]{ObjectAnimator.ofFloat(view, "translationY", new float[]{(float)view.getMeasuredHeight(), 0.0F})};
    }
}
