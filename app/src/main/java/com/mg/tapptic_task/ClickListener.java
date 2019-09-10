package com.mg.tapptic_task;

import android.view.View;

/**
 * Created by magda on 2019-09-10.
 */

public  interface ClickListener{
    public void onDoubleClick(View view, int position);
    public void onLongClick(View view,int position);
    public void onFocus(View view,int position);

}

