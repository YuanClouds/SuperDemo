package yuan.codelib;

import android.util.Log;

import yuan.dblib.DbATest;
import yuan.dblib.TestSingle;

/**
 * Created by Yuan on 2016/12/14.
 * Detail
 */

public class CoreTest {

    private TestSingle single;

    public static void test(){

        Object obj =  Router.getInstance().getTableHandler("DbATest");

        if (obj == null){
            Log.i("CoreTest","DbATest is null , not found dblib!!!");
            return ;
        }

        if (obj instanceof DbATest){
            DbATest dbATest = (DbATest) obj;
            dbATest.dbTestA();
        }
    }

}
