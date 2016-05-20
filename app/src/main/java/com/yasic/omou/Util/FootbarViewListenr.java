package com.yasic.omou.Util;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Yasic on 2016/5/19.
 */
public abstract class FootbarViewListenr extends RecyclerView.OnScrollListener {
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy){
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 0){//down
            onHide();
        }
        else if (dy < -0){//up
            onShow();
        }
    }

    public abstract void onHide();
    public abstract void onShow();
}
