package com.alexvit.baking.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.alexvit.baking.R;

/**
 * Created by Aleksandrs Vitjukovs on 8/1/2017.
 */

public class AspectRatioImageView extends android.support.v7.widget.AppCompatImageView {

    private static final float RATIO = 1.618F;

    private float aspectRatio;

    public AspectRatioImageView(Context context) {
        this(context, null);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        aspectRatio = array.getFloat(R.styleable.AspectRatioImageView_aspectRatio, RATIO);
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
