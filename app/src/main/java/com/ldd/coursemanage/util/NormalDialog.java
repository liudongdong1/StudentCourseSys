package com.ldd.coursemanage.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author ldd
 * @description 封装标准弹出对话框
 * @date 2019.3.16
 * */
public class NormalDialog {
    private Context context;
    /**
     * 定义一个静态私有变量(不初始化，不使用final关键字，
     * 使用volatile保证了多线程访问时instance变量的可见性，
     * 避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用
     * */
    private static volatile NormalDialog instance;

    private NormalDialog(Context context)
    {
        this.context = context;
    }

    /**
     * 采用单例模式,内存占用地，效率高，线程安全，多线程操作原子性。
     * @param1 context为上下文环境
     * @return NormalDialog实例
     *
     * */
    public static NormalDialog getInstance(Context context) {
        if (instance == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (NormalDialog.class) {
                //未初始化，则初始instance变量
                if (instance == null) {
                    instance = new NormalDialog(context);
                }
            }
        }
        return instance;
    }
    public  void showNormalDialog(String word){

        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setTitle("提示");
        normalDialog.setMessage(word);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        normalDialog.setCancelable(true);
                        //...To-do
                    }
                });

        // 显示
        normalDialog.show();
    }
}
