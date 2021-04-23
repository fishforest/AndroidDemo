package com.example.androiddemo.clipchildren;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;

public class SimpleTouchDelegate extends TouchDelegate {
    /**
     * View that should receive forwarded touch events
     */
    private final View mDelegateView;

    /**
     * Bounds in local coordinates of the containing view that should be mapped to the delegate
     * view. This rect is used for initial hit testing.
     */
    private final Rect mTargetBounds;

    /**
     * Bounds in local coordinates of the containing view that are actual bounds of the delegate
     * view. This rect is used for event coordinate mapping.
     */
    private final Rect mActualBounds;

    /**
     * mTargetBounds inflated to include some slop. This rect is to track whether the motion events
     * should be considered to be be within the delegate view.
     */
    private final Rect mSlopBounds;

    private final int mSlop;

    /**
     * True if the delegate had been targeted on a down event (intersected mTargetBounds).
     */
    private boolean mDelegateTargeted;

    public SimpleTouchDelegate(Rect targetBounds, View delegateView) {
        super(targetBounds, delegateView);
        mSlop = ViewConfiguration.get(delegateView.getContext()).getScaledTouchSlop();
        mTargetBounds = new Rect();
        mSlopBounds = new Rect();
        mActualBounds = new Rect();
        setBounds(targetBounds, null);
        mDelegateView = delegateView;
    }

    public void setBounds(Rect desiredBounds, Rect actualBounds) {
        mTargetBounds.set(desiredBounds);
        mSlopBounds.set(desiredBounds);
        mSlopBounds.inset(-mSlop, -mSlop);
//        mActualBounds.set(actualBounds);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        boolean sendToDelegate = false;
        boolean hit = true;
        boolean handled = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mTargetBounds.contains(x, y)) {
                    mDelegateTargeted = true;
                    sendToDelegate = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_MOVE:
                sendToDelegate = mDelegateTargeted;
                if (sendToDelegate) {
                    if (!mSlopBounds.contains(x, y)) {
                        hit = false;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                sendToDelegate = mDelegateTargeted;
                mDelegateTargeted = false;
                break;
        }
        if (sendToDelegate) {
            if (hit) {

            } else {
                int slop = mSlop;
                event.setLocation(-(slop * 2), -(slop * 2));
            }

            handled = mDelegateView.dispatchTouchEvent(event);
        }
        return handled;
    }
}
