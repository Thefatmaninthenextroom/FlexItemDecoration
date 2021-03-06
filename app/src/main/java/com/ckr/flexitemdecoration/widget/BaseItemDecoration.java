package com.ckr.flexitemdecoration.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by PC大佬 on 2018/1/6.
 */

public abstract class BaseItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "BaseItemDecoration";
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;
    protected static final int LINEAR = 1;
    protected static final int GRID = 2;
    protected int mFlag = 0;
    protected int mOrientation = VERTICAL;
    protected Drawable mDivider;
    protected boolean noDrawHeaderDivider;
    protected boolean noDrawFooterDivider;
    protected boolean noDrawLeftDivider;
    protected boolean noDrawRightDivider;
    protected int mDividerHeight;
    protected int mDividerWidth;
    private Context mContext;

    protected BaseItemDecoration(Context context, int mFlag, int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != GRID) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mContext = context;
        this.mFlag = mFlag;
        this.mOrientation = orientation;
        initDefaultDivider(context);
    }

    protected BaseItemDecoration(Context context, int mFlag, int orientation, int drawableId) {
        if (orientation != HORIZONTAL && orientation != VERTICAL && orientation != GRID) {
            throw new IllegalArgumentException("invalid orientation");
        }
        this.mContext = context;
        this.mFlag = mFlag;
        this.mOrientation = orientation;
        mDivider = ContextCompat.getDrawable(context.getApplicationContext(), drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
        mDividerWidth = mDivider.getIntrinsicWidth();
    }

    protected BaseItemDecoration(BaseBuilder baseBuilder) {
        this.mDivider = baseBuilder.mDivider;
        if (this.mDivider == null) {
            initDefaultDivider(baseBuilder.context);
        } else {
            mDividerHeight = mDivider.getIntrinsicHeight();
            mDividerWidth = mDivider.getIntrinsicWidth();
        }
        this.mContext = baseBuilder.context;
        this.mFlag = baseBuilder.mFlag;
        this.mOrientation = baseBuilder.mOrientation;
        this.noDrawHeaderDivider = baseBuilder.noDrawHeaderDivider;
        this.noDrawFooterDivider = baseBuilder.noDrawFooterDivider;
        this.noDrawLeftDivider = baseBuilder.noDrawLeftDivider;
        this.noDrawRightDivider = baseBuilder.noDrawRightDivider;
    }

    private void initDefaultDivider(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mDividerHeight = mDivider.getIntrinsicHeight() * 5;
        mDividerWidth = mDivider.getIntrinsicWidth() * 5;
    }

    public BaseItemDecoration setDivider(@DrawableRes int drawableId) {
        this.mDivider = ContextCompat.getDrawable(mContext.getApplicationContext(), drawableId);
        mDividerHeight = mDivider.getIntrinsicHeight();
        mDividerWidth = mDivider.getIntrinsicWidth();
        return this;
    }

    public BaseItemDecoration removeHeaderDivider(boolean noDrawHeaderDivider) {
        this.noDrawHeaderDivider = noDrawHeaderDivider;
        return this;
    }

    public BaseItemDecoration removeFooterDivider(boolean noDrawFooterDivider) {
        this.noDrawFooterDivider = noDrawFooterDivider;
        return this;
    }

    public BaseItemDecoration removeLeftDivider(boolean noDrawLeftDivider) {
        this.noDrawLeftDivider = noDrawLeftDivider;
        return this;
    }

    public BaseItemDecoration removeRightDivider(boolean noDrawRightDivider) {
        this.noDrawRightDivider = noDrawRightDivider;
        return this;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if (mFlag == LINEAR) {
            if (mOrientation == VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        } else if (mFlag == GRID) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }
    }

    protected abstract void drawVertical(Canvas c, RecyclerView parent);

    protected abstract void drawHorizontal(Canvas c, RecyclerView parent);

    public static abstract class BaseBuilder {
        protected Context context;
        protected Drawable mDivider;
        protected int mFlag;
        protected int mOrientation = VERTICAL;
        protected boolean noDrawHeaderDivider;
        protected boolean noDrawFooterDivider;
        protected boolean noDrawLeftDivider;
        protected boolean noDrawRightDivider;

        protected BaseBuilder(Context context, int flag) {
            this.context = context;
            this.mFlag = flag;
        }

        protected BaseBuilder(Context context, int flag, int mOrientation) {
            if (mOrientation != HORIZONTAL && mOrientation != VERTICAL) {
                throw new IllegalArgumentException("invalid orientation");
            }
            this.context = context;
            this.mFlag = flag;
            this.mOrientation = mOrientation;
        }

        public BaseBuilder setDivider(@DrawableRes int drawableId) {
            this.mDivider = ContextCompat.getDrawable(context.getApplicationContext(), drawableId);
            return this;
        }

        public BaseBuilder removeHeaderDivider(boolean noDrawHeaderDivider) {
            this.noDrawHeaderDivider = noDrawHeaderDivider;
            return this;
        }

        public BaseBuilder removeFooterDivider(boolean noDrawFooterDivider) {
            this.noDrawFooterDivider = noDrawFooterDivider;
            return this;
        }

        public BaseBuilder removeLeftDivider(boolean noDrawLeftDivider) {
            this.noDrawLeftDivider = noDrawLeftDivider;
            return this;
        }

        public BaseBuilder removeRightDivider(boolean noDrawRightDivider) {
            this.noDrawRightDivider = noDrawRightDivider;
            return this;
        }

        public abstract BaseBuilder setOrientation(int mOrientation);
    }

}
