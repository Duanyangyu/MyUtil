package com.duan.util.bitmap.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.duan.util.R;


/**
 * Created by duanyy on 2017/9/27.
 */

public class RoundedImageView extends ImageView {

    private float mTopLeft = 0;
    private float mTopRight = 0;
    private float mBottomRight = 0;
    private float mBottomLeft = 0;
    private RoundRectShape mRoundRectShape;
    private final Paint paint = new Paint();
    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private Matrix mMatrix;

    public RoundedImageView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        getAttributes(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        getAttributes(context, attrs);
    }

    private void getAttributes(Context context, AttributeSet attrs) {

        final TypedArray typedArrayAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.RoundedImageView);

        mTopLeft = typedArrayAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_topLeftRadius, 0);
        mTopRight = typedArrayAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_topRightRadius, 0);
        mBottomLeft = typedArrayAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_bottomLeftRadius, 0);
        mBottomRight = typedArrayAttributes.getDimensionPixelSize(R.styleable.RoundedImageView_bottomRightRadius, 0);

        mRoundRectShape = new RoundRectShape(new float[]{
                mTopLeft, mTopLeft,
                mTopRight, mTopRight,
                mBottomRight, mBottomRight,
                mBottomLeft, mBottomLeft
        }, null, null);

        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (((BitmapDrawable) getDrawable()) != null) {
            mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        }

        float scale = Math.max(getWidth() * 1.0f / mBitmap.getWidth(), getHeight()
                * 1.0f / mBitmap.getHeight());

        mMatrix.setScale(scale,scale);
        mBitmapShader.setLocalMatrix(mMatrix);

        paint.setAntiAlias(true);
        paint.setShader(mBitmapShader);

        mRoundRectShape.resize(getWidth(), getHeight());
        mRoundRectShape.draw(canvas, paint);
    }

}
