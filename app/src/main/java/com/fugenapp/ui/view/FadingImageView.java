package com.fugenapp.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class FadingImageView extends ImageView {
    private FadeSide mFadeSide;

    private Context c;

    public FadingImageView(Context c, AttributeSet attrs, int defStyle) {
        super(c, attrs, defStyle);

        this.c = c;

        init();
    }

    public FadingImageView(Context c, AttributeSet attrs) {
        super(c, attrs);

        this.c = c;

        init();
    }

    public FadingImageView(Context c) {
        super(c);

        this.c = c;

        init();
    }

    private void init() {
        // Enable horizontal fading
        this.setVerticalFadingEdgeEnabled(true);
        // Apply default fading length
        this.setEdgeLength(14);
        // Apply default side
        this.setFadeDirection(FadeSide.TOP_SIDE);
    }

    public void setFadeDirection(FadeSide side) {
        this.mFadeSide = side;
    }

    public void setEdgeLength(int length) {
        this.setFadingEdgeLength(getPixels(length));
    }

    @Override
    protected float getLeftFadingEdgeStrength() {
        return mFadeSide.equals(FadeSide.LEFT_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    protected float getRightFadingEdgeStrength() {
        return mFadeSide.equals(FadeSide.RIGHT_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return mFadeSide.equals(FadeSide.TOP_SIDE) ? 1.0f : 0.0f;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return true;
    }

    @Override
    public boolean onSetAlpha(int alpha) {
        return false;
    }

    private int getPixels(int dipValue) {
        Resources r = c.getResources();

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue, r.getDisplayMetrics());
    }

    public enum FadeSide {
        RIGHT_SIDE, LEFT_SIDE, TOP_SIDE
    }
}
