package yuan.codelib;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yuan.dblib.DbKit;

/**
 * Created by Yuan on 2016/12/13.
 * Detail
 */

public class Router {

    private final String TAG = "Router";

    private Context mContext;
    private static Router dbRouter;

    private List<Pair<Type,String>> routerDbType;
    private Map<Type, Object> platformHandlers;//

    private Map<String,String > routerTable;


    public Router() {
        routerDbType = new ArrayList<>();
        platformHandlers = new HashMap<>();
        routerTable = new HashMap<>();
        init();
        initHander();
    }

    public static Router getInstance(){

        if (dbRouter == null){
            synchronized (Router.class){
                if (dbRouter == null){
                    dbRouter = new Router();
                    return dbRouter;
                }
            }
        }
        return dbRouter;
    }

    public void registerRouterTable(String key,String classPath){
//        List router = this.routerDbType;
//        router.add(new Pair(key,classPath));
//        platformHandlers.put()
        routerTable.put(key,classPath);
    }

    private void init(){
        Log.i(TAG,"init");
        List router = this.routerDbType;
        platformHandlers.put(Type.DB,"yuan.dblib.DbKit");

    }

    private void initHander(){
        Log.i(TAG,"initHander");
        for (Map.Entry<Type, Object> entry : platformHandlers.entrySet()) {
            Object obj = getInitHandler((Type) entry.getKey());
            if (obj != null){
                if (obj instanceof DbKit){
                    DbKit kit = (DbKit) obj;
                    kit.init();
                    routerTable.putAll(kit.getTable());
                    Log.i("yuan","DbKit init");
                }
            }
        }
    }

    private Object getInitHandler(Type type){

        Object handler = null;

        if (platformHandlers.containsKey(type)){
            if (handler == null) {
                try {
                    Class claTmp = Class.forName((String) platformHandlers.get(type));
                    handler = claTmp.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return handler;
        }
        return handler;
    }

    public Object getTableHandler(String key){
        Object handler = null;
        if(routerTable.containsKey(key))
        if (handler == null) {
            try {
                Class claTmp = Class.forName(routerTable.get(key));
                handler = claTmp.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return handler;
    }

}
