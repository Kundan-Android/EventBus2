package com.caliber.eventbus2;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Caliber on 26-05-2018.
 */

public class GlobalBus {
    private static EventBus sBus;
    public static EventBus getsBus(){
        if (sBus == null){
            sBus = EventBus.getDefault();
        }
        return sBus;
    }
}
