package com.pony.observable;

import java.util.Observable;

import com.pony.model.LeaveHouseModel;

/**
 * 步数被观察者
 * 
 * Created by waka on 2016/3/2.
 */
public class LookObservable extends Observable {

    //单例
    private static LookObservable instance = null;

    public static LookObservable getInstance() {

        if (null == instance) {
            instance = new LookObservable();
        }
        return instance;
    }

    //通知观察者更新数据
    public void notifyStepChange(String leaveHouseModel) {
        //关键方法，必须写，具体实现可以查看源码
        setChanged();//设置changeFlag
        notifyObservers(leaveHouseModel);//通知观察者
    }

}