package com.alexvit.baking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.alexvit.baking.R;

/**
 * Created by alexander.vitjukov on 07.08.2017.
 */

public class AspectRatioFrameLayout extends FrameLayout {

    private static final float RATIO = 1.618F;

    private float aspectRatio;

    public AspectRatioFrameLayout(@NonNull Context context) {
        super(context);
    }

    public AspectRatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioFrameLayout);
        aspectRatio = array.getFloat(R.styleable.AspectRatioFrameLayout_aspectRatio, RATIO);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = Math.round(width / aspectRatio);

        setMeasuredDimension(width, height);
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float newAspectRatio) {
        if (newAspectRatio != aspectRatio) {
            this.aspectRatio = newAspectRatio;
            requestLayout();
        }
    }

}
