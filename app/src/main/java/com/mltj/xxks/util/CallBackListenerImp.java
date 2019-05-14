package com.mltj.xxks.util;

import java.util.ArrayList;

public class CallBackListenerImp {
    public interface  CallbackListener{
        void callBack(ArrayList<String> mInputList);
    }
    public static CallbackListener mCallbackLisntener;
    public static void getCallbackListener(CallbackListener callbackLisntener){
        mCallbackLisntener = callbackLisntener;
    }
}
