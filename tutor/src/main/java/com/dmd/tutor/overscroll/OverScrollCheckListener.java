package com.dmd.tutor.overscroll;

/**
 * Created by Administrator on 2016/7/19.
 */
public interface OverScrollCheckListener {

    int getContentViewScrollDirection();

    boolean canScrollUp();

    boolean canScrollDown();

    boolean canScrollLeft();

    boolean canScrollRight();
}
