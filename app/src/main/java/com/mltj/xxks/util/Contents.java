package com.mltj.xxks.util;

import android.content.Context;

/**
 * @author milantiejiang
 */
public class Contents {
    public static String BASE_URL_RELEASE = "http://39.105.138.210:8081/";
    public static String BASE_URL_DEBUG = "http://39.105.138.210:8081/";
    public static final String defaultDateFormat="yyyy-MM-dd";
    public static final String KEY_USER_ID="KEY_USER_ID";
    public static final String KEY_USER_NAME="KEY_USER_NAME";
    public static final String KEY_USER_COMPANY="KEY_USER_COMPANY";
    public static final String KEY_USER_COMPANY_ID="KEY_USER_COMPANY_ID";
    public static final String KEY_USER_DEPMENT="KEY_USER_DEPMENT";
    public static final String KEY_USER_DEPMENT_ID="KEY_USER_DEPMENT_ID";
    public static final String KEY_DATA_CATEGORY_ALL="KEY_DATA_CATEGORY_ALL";
    public static final String KEY_DATA_CATEGORY_NEWS="KEY_DATA_CATEGORY_NEWS";
    public static final String KEY_DATA_CATEGORY_STUDY="KEY_DATA_CATEGORY_STUDY";
    public static final String KEY_DATA_CATEGORY_TM="KEY_DATA_CATEGORY_TM";
    public static final String KEY_DATA_CATEGORY_SJ="KEY_DATA_CATEGORY_SJ";
    public static final String KEY_DATA_CATEGORY_COMPANY="KEY_DATA_CATEGORY_COMPANY";

    public static String getBaseUrl(Context context){
        if(Util.isApkInDebug(context)){
            return BASE_URL_DEBUG;
        }else {
            return BASE_URL_RELEASE;
        }
    }
}
