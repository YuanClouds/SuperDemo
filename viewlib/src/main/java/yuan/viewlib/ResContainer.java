package yuan.viewlib;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

/**
 * Created by Yuan on 2016/12/5.
 * Detail 资源文件处理 for 映射
 */

public class ResContainer {

    private static ResContainer R = null;

    private static String mPackageName = "";

    public static synchronized ResContainer get(){
        if (R == null){
            R = new ResContainer();
        }
        return R;
    }

    public static int getResourceId(Context context, String var1, String var2) {
        Resources var3 = context.getResources();
        if(TextUtils.isEmpty(mPackageName)) {
            mPackageName = context.getPackageName();
        }

        int var4 = var3.getIdentifier(var2, var1, mPackageName);
        if(var4 <= 0) {
            throw new RuntimeException("获取资源ID失败:(packageName=" + mPackageName + " type=" + var1 + " name=" + var2);
        } else {
            return var4;
        }
    }
}
