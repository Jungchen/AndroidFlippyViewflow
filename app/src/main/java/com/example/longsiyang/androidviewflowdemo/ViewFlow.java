package com.example.longsiyang.androidviewflowdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

/**
 * Created by longsiyang on 15/10/17.
 */
public class ViewFlow extends ViewFlipper implements GestureDetector.OnGestureListener {

    Context mContext;
    Animation mLeftInAnim;
    Animation mLeftOutAnim;
    Animation mRightInAnim;
    Animation mRightOutAnim;
    double mAspectRatio = 2.0;
    int mFlipPeriod = 3000;
    GestureDetector gestureDetector;

    public ViewFlow(Context context) {
        super(context);
        mContext = context;
        initData();
        initView();
    }

    public ViewFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initData();
        initView();
        gestureDetector = new GestureDetector(mContext , this);
    }

    private void initData() {
        mLeftInAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_left_in);
        mLeftOutAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_left_out);
        mRightInAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_right_in);
        mRightOutAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_right_out);
    }

    private void initView() {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.test_page1, null);
        View view2 = LayoutInflater.from(mContext).inflate(R.layout.test_page2, null);
        this.addView(view1);
        this.addView(view2);
    }

    public void setAspectratio(double aspectratio) {
        this.mAspectRatio = aspectratio;
        if (this.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtil.getScreenWidthPixel(mContext), (int) (ScreenUtil.getScreenWidthPixel(mContext) / mAspectRatio));
            this.setLayoutParams(params);
        } else if (this.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ScreenUtil.getScreenWidthPixel(mContext), (int) (ScreenUtil.getScreenWidthPixel(mContext) / mAspectRatio));
            this.setLayoutParams(params);
        }
    }

    public void setPeriod(int period) {
        if (period > 0) {
            this.mFlipPeriod = period;
            this.setFlipInterval(period);
            mLeftInAnim.setDuration(period);
            mLeftOutAnim.setDuration(period);
            mRightInAnim.setDuration(period);
            mRightOutAnim.setDuration(period);
            this.setInAnimation(mRightInAnim);
            this.setOutAnimation(mLeftOutAnim);
        } else if (period < 0) {
            this.mFlipPeriod = period;
            this.setFlipInterval(-period);
            mLeftInAnim.setDuration(-period);
            mLeftOutAnim.setDuration(-period);
            mRightInAnim.setDuration(-period);
            mRightOutAnim.setDuration(-period);
            this.setInAnimation(mLeftInAnim);
            this.setOutAnimation(mRightOutAnim);
        }
    }

    public void flipStart() {
        if (!this.isFlipping()) {
            // 合法性检测
            if (this.getChildCount() == 0) {
                try {
                    throw new Throwable("Illegal ChildCount Error");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            if (this.mFlipPeriod == 0) {
                try {
                    throw new Throwable("Illegal PeriodTime Error");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            if (mFlipPeriod > 0) {
                this.setInAnimation(mRightInAnim);
                this.setOutAnimation(mLeftOutAnim);
            } else if (mFlipPeriod < 0) {
                this.setInAnimation(mLeftInAnim);
                this.setOutAnimation(mRightOutAnim);
            }
            this.setAutoStart(true);
            this.startFlipping();
        }
    }

    public void flipStop() {
        this.stopFlipping();
        this.setAutoStart(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        flipStop();             // 点击事件后，停止自动播放
        gestureDetector.onTouchEvent(event);         // 注册手势事件
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("tag" , "this down =====" );
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("tag" , "onShowPress =====" );
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("tag" , "onSingleTapUp =====" );
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d("tag" , "onScroll =====" );
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d("tag" , "onLongPress =====" );
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d("tag" , "this fling =====" + (e2.getX() - e1.getX() ));
        if (e2.getX() - e1.getX() > 120) {            // 从左向右滑动（左进右出）
            this.setInAnimation(mLeftInAnim);
            this.setOutAnimation(mRightOutAnim);
            this.showPrevious();
            return true;
        } else if (e2.getX() - e1.getX() < -120) {        // 从右向左滑动（右进左出）
            this.setInAnimation(mRightInAnim);
            this.setOutAnimation(mLeftOutAnim);
            this.showNext();
            return true;
        }
        return true;
    }
}
