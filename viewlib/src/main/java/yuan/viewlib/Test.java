package yuan.viewlib;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Yuan on 2016/12/5.
 * Detail
 */

public class Test {

    public static void test(Context context){
        Toast.makeText(context,"这是来自打包的jar内容",Toast.LENGTH_SHORT).show();;
    }

    public static void addVieiw(Context context,ViewGroup parentView){
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(ResContainer.getResourceId(context,"drawable","img_test"));

        parentView.addView(imageView);
    }

    public void testRouter(){
        System.out.print("-测试testRouter -");
    }

}
