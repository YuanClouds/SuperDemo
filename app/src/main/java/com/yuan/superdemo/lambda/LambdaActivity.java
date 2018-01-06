package com.yuan.superdemo.lambda;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Lambda 语法学习
 * Created by siven on 2018/1/4.
 */

public class LambdaActivity extends AppCompatActivity{

    private static final String TAG = LambdaActivity.class.getSimpleName();

    TextView tvResult;
    ScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScrollView = new ScrollView(this);
        tvResult = new TextView(this);
        mScrollView.addView(tvResult);
        setContentView(mScrollView);
        demo();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void demo(){

        LambdaProvider mLambdaProvider = new LambdaProvider();

        new View(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //...
            }
        });

        //引入举例
        // 无参数
        new View(this).setOnClickListener(view->{
            //.. doing
        });

        // 有参数
        new ScrollView(this).setOnScrollChangeListener((View view, int i, int i1, int i2, int i3)->{
            consoleInput(TAG,view.getId()+"");
            consoleInput(TAG,i+"");
            consoleInput(TAG,i1+"");
            consoleInput(TAG,i2+"");
            consoleInput(TAG,i3+"");
        });

        //# 1、线程利用lambada表达式
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).run();

        new Thread(()->{
            //这里是线程运行的区域，实质是抽象化了Runnable的run方法
            consoleInput(TAG,"Thread is running...");
        }).run();

        String value="";

        // # 2 针对返回值利用lambada表达式
        mLambdaProvider.setmTestInterface1(()->2); // 直接返回2
        mLambdaProvider.setmTestInterface1(()-> 2+4 ); // 直接返回计算表达式
        mLambdaProvider.setmTestInterface1(()->{ // 直接返回作用域
            consoleInput(TAG,"setmTestInterface1 here...");
            //... do something
            String str = value; // 不用final就可以访问到了
            this.demo(); // this指针不再模糊
            return 3;
        });

        // # 3 针对参数+返回值利用lambada表达式
        mLambdaProvider.setmTestInterface2((x)->2); // 直接返回2
        mLambdaProvider.setmTestInterface2((x)-> 2+4 ); //x 直接返回计算表达式
        mLambdaProvider.setmTestInterface2((x)->{ // 使用形参x计算后返回
            return x+2+4;
        });

        // #4 针对会抛出异常
        mLambdaProvider.setmTestInterface3(()->{
            throw new IllegalStateException("hi~");
        });

        //steam
        // TODO: 2018/1/4 以下代码必须运行在minsdk>24
        List<LambdaProvider.Model> listSteam = Arrays.asList(new LambdaProvider.Model("1","siven0"),
                new LambdaProvider.Model("2","siven1"),new LambdaProvider.Model("2","siven2")
                ,new LambdaProvider.Model("2","siven3"));
        for (LambdaProvider.Model i:listSteam){
            //...
        }

        listSteam.forEach(i->{
            //...
        });

        listSteam.stream()
                .filter(s->s.type.equals("2"))
                .forEach(s->consoleInput(TAG,"stream："+s)//这里只会遍历出满足等于2的字符串
                );

        // 或者只提取1到新数组
        // collect()操作会把其接收的元素聚集到一起（这里是List），collect()方法的参数则被用来指定如何进行聚集操作。在这里我们使用toList()以把元素输出到List中。
        List<LambdaProvider.Model> newList = listSteam.stream()
                .filter(s->s.type.equals("2"))
                .collect(Collectors.toList());
        newList.forEach(s-> consoleInput(TAG,"stream newList: "+s));

        //如果我要过滤出提取的数组1的位置结合，用map进行转化
        Set<String> setIndex = listSteam.stream()
                .filter(s->s.type.equals("2"))
                .map(s->s.name)
                .collect(Collectors.toSet());
        setIndex.stream().forEach(s-> consoleInput(TAG,"stream setIndex: "+ s));

        // 转化为list
        List<String> listIndex = listSteam.stream()
                .filter(s->s.type.equals("2"))
                .map(s->s.name)
                .collect(Collectors.toList());
        listIndex.stream().forEach(s-> consoleInput(TAG,"stream listIndex: "+s));

        consoleInput(TAG,"玩玩map转化");

        // 玩玩map转化
        List<String> stringList = Arrays.asList("1","2","3"); // 随便申请一个list

        consoleInput(TAG,"玩玩map转化 ----- flatmap ");
        List<String> tmp0  =  stringList.stream()
                .flatMap((s)->{
                    String str = (String) s + " - 转化（flatmap）";
                    consoleInput(TAG,"转化前 "+s + "，转化后 " + str);
                    List<String> result = new ArrayList<>();
                    result.add(str);
                    return result.stream();
                })
//                  为了方便读者阅读，我用非lambda表达式
//                  .flatMap(new Function<String, Stream<String>>() {
//                      @Override
//                      public Stream<String> apply(String s) {
//                          String str = (String) s + " - 转化";
//                          consoleInput(TAG,"转化前 "+s + "转化后 " + str);
//                          List<String> result = new ArrayList<>();
//                          result.add(str);
//                          return result.stream();
//                      }
//                  })
                .collect(Collectors.toList());// 流收集起来
        consoleInput(TAG,"转换后结果输出 ");
        tmp0.stream().forEach(c->consoleInput(TAG,"-> "+c));

        consoleInput(TAG,"玩玩map转化 ----- map ");
        tmp0 = stringList.stream()
                .map((s)->{
                    String str = (String) s + " - 转化（map）";
                    consoleInput(TAG,"转化前 "+s + "，转化后 " + str);
                    return str;
                })
//                  为了方便读者阅读，我用非lambda表达式
//                .map(new Function<String, String>() {
//                    @Override
//                    public String apply(String s) {
//                        String str = (String) s + " - 转化（map）";
//                        consoleInput(TAG,"转化前 "+s + "，转化后 " + str);
//                        return str;                    }
//                })
                .collect(Collectors.toList());// 收集
        consoleInput(TAG,"转换后结果输出 ");
        tmp0.stream().forEach(c->consoleInput(TAG,"-> "+c));

        consoleInput(TAG,"玩玩map转化 ----- sum ");
        int sum = stringList.stream()
                .mapToInt(s->Integer.valueOf(s)) // map转int
                .sorted() // 排序
                .sum();
        consoleInput(TAG,"转换后结果输出 "+sum);


    }

    public void consoleInput(String tag,String log){
        tvResult.setText(tvResult.getText().toString() + "\n\n"+log);
    }

    /**
     * 这个类完全提供一些接口测试
     */
    static class LambdaProvider{

        TestInterface0 mTestInterface0;
        TestInterface1 mTestInterface1;
        TestInterface2 mTestInterface2;
        TestInterface3 mTestInterface3;

        public void setmTestInterface0(TestInterface0 mTestInterface0) {
            this.mTestInterface0 = mTestInterface0;
        }

        public void setmTestInterface1(TestInterface1 mTestInterface1) {
            this.mTestInterface1 = mTestInterface1;
        }

        public void setmTestInterface2(TestInterface2 mTestInterface2) {
            this.mTestInterface2 = mTestInterface2;
        }

        public void setmTestInterface3(TestInterface3 mTestInterface3) {
            this.mTestInterface3 = mTestInterface3;
        }

        public interface TestInterface0{
            // 不带参数、不带返回值
            public void method();
        }

        public interface TestInterface1{
            // 带返回值
            public int method1();
        }

        public interface TestInterface2{
            // 带参数
            public int method2(int x);
        }

        public interface TestInterface3{
            // 抛出异常方法
            public void method3() throws Exception;
        }

        static class Model{
            public String type;//"1","2" 用于区别类型
            public String name;


            public Model(String type, String name) {
                this.type = type;
                this.name = name;
            }

            @Override
            public String toString() {
                return "Model (type: " + type +" name: "+name+")";
            }
        }

    }
}
