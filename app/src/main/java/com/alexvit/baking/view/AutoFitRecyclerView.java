package com.alexvit.baking.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.alexvit.baking.R;

/**
 * Created by Aleksandrs Vitjukovs on 8/1/2017.
 */

public class AutoFitRecyclerView extends RecyclerView {

    private static final int WIDTH_DP = 400;
    private static final int MIN_WIDTH_DP = 100;
    private int desiredWidth;

    private GridLayoutManager manager;

    public AutoFitRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public AutoFitRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoFitRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (desiredWidth >= MIN_WIDTH_DP) {
            int spanCount = Math.max(1, Math.round((float) getWidthDp() / desiredWidth));
            manager.setSpanCount(spanCount);
        }
    }

    private int getWidthDp() {
        return Math.round(
                (float) getMeasuredWidth() / Resources.getSystem().getDisplayMetrics().density);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoFitRecyclerView);
            desiredWidth = array.getInt(R.styleable.AutoFitRecyclerView_desiredColumnWidth, WIDTH_DP);
            array.recycle();
        } else {
            desiredWidth = WIDTH_DP;
        }

        manager = new GridLayoutManager(getContext(), 1);
        setLayoutManager(manager);
    }
}