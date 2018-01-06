package com.yuan.superdemo.widgets;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Yuan on 2016/11/15.
 * Detail 可以悬浮在标题栏的dialog
 */

public class SuspendedDialog extends Dialog{

    public SuspendedDialog(Context context) {
        super(context);
    }

    public SuspendedDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init(Context context){

    }

    public static class Builder{

    }
}
