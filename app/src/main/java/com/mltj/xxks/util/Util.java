package com.mltj.xxks.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.mltj.xxks.R;
import com.mltj.xxks.bean.FileJoin;
import com.mltj.xxks.bean.News;
import com.mltj.xxks.bean.Notice;
import com.mltj.xxks.bean.Study;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Util {
    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 修改状态栏颜色，支持4.4以上版本
     *
     * @param activity
     * @param colorId
     */
    public static void setStatusBarColor(Activity activity, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(activity.getResources().getColor(colorId));
        }
    }

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUISetStatusBarLightMode(activity, true)) {
                result = 1;
            } else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility
                        (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色文字、图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @param type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, true);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    /**
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    public static void StatusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility
                                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView()
                                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 将long类型的时间转换成字符串格式
     *
     * @param d
     * @return
     */
    public static String getDateStr(long d) {
        String dstr = "";
        String df = Contents.defaultDateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(df, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(d);
        dstr = sdf.format(calendar.getTime());
        return dstr;
    }

    public static String getStandardTime(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
        String hms = formatter.format(timestamp);
        return hms;
    }

    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap decodeResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * 弹窗系统对话框
     *
     * @param context
     */
    public static void showLogoutDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        /*true 代表点击空白可消失   false代表点击空白哦不可消失 */
        builder.setCancelable(true);
        View view = View.inflate(context, R.layout.fragment_system_alert_dialog, null);
        RelativeLayout cancel = view.findViewById(R.id.cancel);
        RelativeLayout ok = view.findViewById(R.id.ok);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public static boolean isLogin(Context context) {
        int userid = UserSPUtil.getInstance(context).getInt(Contents.KEY_USER_ID);
        if (userid != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 密码规则匹配
     * ^ 匹配一行的开头位置
     * (?![0-9]+$) 预测该位置后面不全是数字
     * (?![a-zA-Z]+$) 预测该位置后面不全是字母
     * [0-9A-Za-z] {6,16} 由6-16位数字或这字母组成
     * $ 匹配行结尾位置
     */
    public static boolean matchPassword(String pw) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pw);
        return m.matches();
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isMobiPhoneNum(str) || isHKPhoneLegal(str);
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 通用判断 ^(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}$
     *
     * @param telNum
     * @return
     */
    public static boolean isMobiPhoneNum(String telNum) {
//        String regex = "^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        String regex = "^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(telNum);
        return m.matches();
    }

    public static Pattern mPattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    /**
     * 更严格的判断
     *
     * @param
     * @return
     */
    public static boolean isMobileNum(String telNum) {
        Matcher m = mPattern.matcher(telNum);
        return m.matches();
    }


    /**
     * 手机号码格式验证
     *
     * @param mobiles
     * @return
     */
    public static boolean phoneNumCheck(String mobiles) {
        int len = 11;
        if (mobiles == null || mobiles.length() != len) {
            return false;
        } else {
            return isMobiPhoneNum(mobiles);
        }
    }

    /**
     * MD5加密，32位
     */
    public static String getMD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 随机颜色
     *
     * @return
     */
    public static int getRandomColor() {
        Random random = new Random();
        int ranColor = 0xff000000 | random.nextInt(0x00ffffff);
        return ranColor;
    }

    public static int versionCode(Context context, String packagename) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(packagename, 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("versionCode", packagename + "设备上不存在该应用");
            return -1;
        }
        return code;
    }

    public static String versionName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = "";
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo("com.mltj.xxks", 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        name = info.versionName;
        return name;
    }

    public static String getNewsHtml(News news) {
        if (news != null) {
            String url = "";
            ArrayList<FileJoin> flist = news.getFileJoins();
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    url = flist.get(i).getFileUrl();
                }
            } else {

            }

            String img = "";
            if (!url.equals("")) {
                img = "<img src=\"" + url + "\" width=\"100%\">\n";
            }
            String html = "<html>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1><font color=\"#000\">" + news.getTitle() + "</h1>\n" +
                    "<h4>" + Util.getDateStr(news.getLongCreateDate()) + "</h4>\n" +
                    "\n" +
                    img +
                    "<h4>" + news.getContent() + "</h4>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
            return html;
        }
        return "";
    }

    public static String getStudyHtml(Study study) {
        if (study != null) {
            String url = "";
            ArrayList<FileJoin> flist = study.getFileJoins();
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    url = flist.get(i).getFileUrl();
                }
            } else {

            }
            String img = "";
            if (!url.equals("")) {
                img = "<img src=\"" + url + "\" width=\"100%\">\n";
            }
            String html = "<html>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1><font color=\"#000\">" + study.getTitle() + "</h1>\n" +
                    "<h4>" + Util.getDateStr(study.getLongCreateDate()) + "</h4>\n" +
                    "\n" +
                    img +
                    "<h4>" + study.getContent() + "</h4>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
            return html;
        }
        return "";
    }

    public static String getNoticeHtml(Notice notice) {
        if (notice != null) {
            String url = "";
            ArrayList<FileJoin> flist = notice.getFileJoins();
            if (flist != null) {
                for (int i = 0; i < flist.size(); i++) {
                    url = flist.get(i).getFileUrl();
                }
            } else {

            }
            String img = "";
            if (!url.equals("")) {
                img = "<img src=\"" + url + "\" width=\"100%\">\n";
            }
            String html = "<html>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1><font color=\"#000\">" + notice.getTitle() + "</h1>\n" +
                    "<h4>" + Util.getDateStr(notice.getLongCreateDate()) + "</h4>\n" +
                    "\n" +
                    img +
                    "<h4>" + notice.getContent() + "</h4>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";
            return html;
        }
        return "";
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
