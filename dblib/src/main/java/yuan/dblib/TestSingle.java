package yuan.dblib;

import android.util.Log;

/**
 * Created by Yuan on 2017/1/3.
 * Detail 测试单利
 */

public class TestSingle {

    private static TestSingle instance;

    public static TestSingle getInstance(){
        if (instance == null){
            instance = new TestSingle();
        }
        return instance;
    }

    public void singleMethod(){
        Log.d("siven","singleMethod");
    }
}
