package yuan.dblib;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuan on 2016/12/14.
 * Detail
 */

public class DbKit {

    Map<String,String> table = new HashMap<>();

    public void init(){
        table.clear();
        table.put("DbATest","yuan.dblib.DbATest");
    }

    public Map getTable(){
        return table;
    }

}
