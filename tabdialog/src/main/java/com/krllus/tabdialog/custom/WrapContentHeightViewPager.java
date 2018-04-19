package com.krllus.tabdialog.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.krllus.tabdialog.R;

/**
 * Created by João Carlos on 4/12/18.
 * joaocarlusferrera at gmail
 */
public class WrapContentHeightViewPager extends ViewPager {

    private int maxHeight;
    private int minHeight;
    //private Boolean mAnimStarted = false;

    /**
     * Constructor
     *
     * @param context the context
     */
    public WrapContentHeightViewPager(Context context) {
        super(context, null);
    }

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs   the attribute set
     */
    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray styledAttrs =
                context.obtainStyledAttributes(attrs, R.styleable.WrapContentHeightViewPager);
        try {
            maxHeight = styledAttrs.getDimensionPixelSize(R.styleable.WrapContentHeightViewPager_maxHeight, 0);
            minHeight = styledAttrs.getDimensionPixelSize(R.styleable.WrapContentHeightViewPager_minHeight, -1);
        } finally {
            styledAttrs.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //if (mAnimStarted || getAdapter() == null) return;

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            int h = child.getMeasuredHeight();
            if (h > height) height = h;
        }

        if (maxHeight > 0) {
            int hMode = MeasureSpec.getMode(heightMeasureSpec);

            switch (hMode) {
                case MeasureSpec.AT_MOST:
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(height, maxHeight), MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.EXACTLY:
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(height, maxHeight), MeasureSpec.EXACTLY);
                    break;
            }
        }


        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
