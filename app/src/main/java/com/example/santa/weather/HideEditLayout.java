package com.example.santa.weather;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by santa on 16/7/28.
 */
public class HideEditLayout extends ViewGroup {
    private View mContent;
    private float mDensity;
    private ImageView mImageView;
    private EditText mEditText;
    private LinearLayout mContainer;
    private int mMoveMax;
    private int mMoveMin;
    private Drawable mDrawableAdd;
    private Drawable mDrawableOk;
    private InputMethodManager imm;
    private AddCityListener mListener;

    public HideEditLayout(Context context) {
        this(context, null);
    }

    public HideEditLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HideEditLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HideEditLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        mDensity = context.getResources().getDisplayMetrics().density;
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableAdd = context.getDrawable(R.mipmap.add);
            mDrawableOk = context.getDrawable(R.mipmap.ok);
        } else {
            mDrawableAdd = context.getResources().getDrawable(R.mipmap.add);
            mDrawableOk = context.getResources().getDrawable(R.mipmap.ok);
        }

        mImageView = new ImageView(context);
        mImageView.setLayoutParams(layoutParams);
        mImageView.setImageDrawable(mDrawableAdd);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                changeState();
            }
        });

        mEditText = new EditText(context);
        mEditText.setHint("请输入城市名");
        mEditText.setLayoutParams(layoutParams);
        mEditText.setPadding(0, 0, 0, 0);
        mEditText.setBackground(null);
        mEditText.setTextColor(Color.WHITE);
        mEditText.setSingleLine();

        mContainer = new LinearLayout(context);
        mContainer.setLayoutParams(layoutParams);
        mContainer.setOrientation(LinearLayout.HORIZONTAL);

        mContainer.addView(mImageView);
        mContainer.addView(mEditText);

        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount()>1) {
            throw new IllegalStateException("HideEditLayout only can host 1 elements");
        } else {
            mContent = getChildAt(0);
        }
        addView(mContainer);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        MarginLayoutParams layoutParams;
        int heightMeasure = 0;
        int widthMeasure = 0;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mContent) {
            measureChildWithMargins(mContent, widthMeasureSpec, 0, heightMeasureSpec, 0);
            layoutParams = (MarginLayoutParams) mContent.getLayoutParams();
            int height =  layoutParams.topMargin + layoutParams.bottomMargin + mContent.getMeasuredHeight() + paddingTop + paddingBottom;
            heightMeasure = Math.max(height, heightMeasure);
            widthMeasure += layoutParams.leftMargin + layoutParams.rightMargin + mContent.getMeasuredWidth() + paddingLeft + paddingRight;
        }

        //match
        if(null != mContainer) {
            layoutParams = (LayoutParams) mContainer.getLayoutParams();
            measureChildWithMargins(mContainer, widthMeasureSpec, 0, heightMeasureSpec, 0);
            int height =  layoutParams.topMargin + layoutParams.bottomMargin + mContainer.getMeasuredHeight() + paddingTop + paddingBottom;
            if(layoutParams.height != LayoutParams.MATCH_PARENT) {
                heightMeasure = Math.max(height, heightMeasure);
            }
            widthMeasure += layoutParams.leftMargin + layoutParams.rightMargin + mContainer.getMeasuredWidth() + paddingLeft + paddingRight;
            mMoveMax = MeasureSpec.getSize(widthMeasureSpec) - mImageView.getMeasuredWidth();
            mMoveMin = 0;
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : widthMeasure, heightMode == MeasureSpec.EXACTLY ? heightSize : heightMeasure);

        forceUniformHeight(widthMeasureSpec);
    }


    private void forceUniformHeight(int WidthMeasureSpec) {
        int count = getChildCount();
        // Pretend that the linear layout has an exact size.
        int uniformMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
        for (int i = 0; i< count; ++i) {
            final View child = getChildAt(i);
//            if (child.getVisibility() != GONE) {
            LayoutParams lp = ((LayoutParams)child.getLayoutParams());

            if (lp.height == LayoutParams.MATCH_PARENT) {
                // Temporarily force children to reuse their old measured height
                // FIXME: this may not be right for something like wrapping text?
                int oldWidth = lp.width;
                lp.width = child.getMeasuredWidth();

                // Remeasue with new dimensions
                measureChildWithMargins(child, WidthMeasureSpec, 0, uniformMeasureSpec, 0);
                lp.width = oldWidth;
            }
        }
//        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft   = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        if (null != mContent) {
            MarginLayoutParams lp = (MarginLayoutParams) mContent.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin;
            final int right = left + mContent.getMeasuredWidth();
            final int bottom = top + mContent.getMeasuredHeight();
            mContent.layout(left, top, right, bottom);
        }

        if (null != mContainer) {
            LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
            final int left = getWidth() + lp.leftMargin -paddingRight - mImageView.getMeasuredWidth();
            final int right = left + mContainer.getMeasuredWidth();

            final int top = getMeasuredHeight()/2 - mContainer.getMeasuredHeight()/2;
            final int bottom = top + mContainer.getMeasuredHeight();
            mContainer.layout(left, top, right, bottom);
        }

    }

    private void changeState() {
        if (getScrollX() == mMoveMax) {
            smoothScrollTo(mMoveMin);
            softInputHide();
            mImageView.setImageDrawable(mDrawableAdd);
            if (null != mListener) {
                mListener.onAdd(mEditText.getText().toString());
            }
            mEditText.setText("");
        } else {
            smoothScrollTo(mMoveMax);
            softInputShow();
            mImageView.setImageDrawable(mDrawableOk);

        }

    }


    private void softInputShow() {
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
    }

    private void softInputHide() {
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    private void smoothScrollTo(int targetPosition) {
        ValueAnimator animator = ObjectAnimator.ofFloat(getScrollX(), targetPosition);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curX = (float) animation.getAnimatedValue();
                scrollBy((int)curX - getScrollX(), 0);
            }
        });
        animator.start();
    }


    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends MarginLayoutParams {

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        @SuppressWarnings({"unused"})
        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }


    public void setAddCityListener(AddCityListener listener) {
        mListener = listener;
    }

    public interface AddCityListener {
        void onAdd(String cityName);
    }


}
