# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\sdk\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings                     # 忽略警告，避免打包时某些警告出现
-optimizationpasses 5               # 指定代码的压缩级别
-dontusemixedcaseclassnames         # 表示混淆时不使用大小写混合类名
-dontskipnonpubliclibraryclasses    # 表示不跳过library中的非public的类
-dontpreverify                      # 表示不进行预校验。这个预校验是作用在Java平台上的，Android平台上不需要这项功能，去掉之后还可以加快混淆速度
-verbose                            # 表示打印混淆的详细信息
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法
#add
-dontoptimize #表示不进行优化，建议使用此选项，因为根据proguard-android-optimize.txt中的描述，优化可能会造成一些潜在风险，不能保证在所有版本的Dalvik上都正常运行
-dontwarn android.support.v4.**     #缺省proguard 会检查每一个引用是否正确，但是第三方库里面往往有些不会用到的类，没有正确引用。如果不配置的话，系统就会报错。
-dontwarn android.os.**
-keep class android.support.v4.** { *; }        # 保持哪些类不被混淆
#-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class android.os.**{*;}

-keep class android.support.**{*;}

-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment


-keepclasseswithmembernames class * {       # 保持 native 方法不被混淆
    native <methods>;
}
	# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {            # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {                  # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {    # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keep public class * extends android.preference.Preference


#-keep class com.baidu.**   {*;}
-keep class vi.com.**   {*;}

-dontwarn org.apache.commons.**
-keep class org.apache.http.impl.client.**
-dontwarn org.apache.commons.**
-keep class com.blueware.** { *; }
-dontwarn com.blueware.**
-keepattributes Exceptions, Signature, InnerClasses
-dontwarn butterknife.internal.**
-keep class butterknife.**{ *; }
-keep class **$$ViewInjector{ *; }
-keepclasseswithmembernames class *{
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class *{
    @InjectView.* <fields>;
}
-keepclasseswithmembernames class *{
    @butterknife.* <methods>;
}

-keep class com.mltj.xxks.bean.*{
    *;
}

-keep class com.sunday.eventbus.**{
 *;
}

-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

## 对WebView的处理
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, java.lang.String);
}

## 保留JS方法不被混淆
-keep class com.example.xxx.MainActivity$JSInterface1 {
    <methods>;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#不混淆资源类
-keepclassmembers class **.R$* {
  public static <fields>;
}

##---------------Begin: proguard configuration for fastjson  ----------
-keep public class * implements java.io.Serializable {
        public *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-keep class com.alibaba.fastjson.** { *; }

-keepclassmembers class * {
public <methods>;
}
-keep class * extends java.lang.annotation.Annotation { *; }
#add
-keepattributes *Annotation* #表示对注解中的参数进行保留。
#极光推送
#-dontoptimize
#-dontpreverify
#-dontwarn cn.jpush.**
#-keep class cn.jpush.** { *; }
#-dontwarn cn.jiguang.**
#-keep class cn.jiguang.** { *; }

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

#友盟
# -dontusemixedcaseclassnames
#    -dontshrink
#    -dontoptimize
#    -dontwarn com.google.android.maps.**
#    -dontwarn android.webkit.WebView
#    -dontwarn com.umeng.**
#    -dontwarn com.tencent.weibo.sdk.**
#    -dontwarn com.facebook.**
#    -keep public class javax.**
#    -keep public class android.webkit.**
#    -dontwarn android.support.v4.**
#    -keep enum com.facebook.**
#    -keepattributes Exceptions,InnerClasses,Signature
#    -keepattributes *Annotation*
#    -keepattributes SourceFile,LineNumberTable
#
#    -keep public interface com.facebook.**
#    -keep public interface com.tencent.**
#    -keep public interface com.umeng.socialize.**
#    -keep public interface com.umeng.socialize.sensor.**
#    -keep public interface com.umeng.scrshot.**
#    -keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#    -keep public class com.umeng.socialize.* {*;}
#
#
#    -keep class com.facebook.**
#    -keep class com.facebook.** { *; }
#    -keep class com.umeng.scrshot.**
#    -keep public class com.tencent.** {*;}
#    -keep class com.umeng.socialize.sensor.**
#    -keep class com.umeng.socialize.handler.**
#    -keep class com.umeng.socialize.handler.*
#    -keep class com.umeng.weixin.handler.**
#    -keep class com.umeng.weixin.handler.*
#    -keep class com.umeng.qq.handler.**
#    -keep class com.umeng.qq.handler.*
#    -keep class UMMoreHandler{*;}
#    -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
#    -keep class com.tencent.mm.sdk.modelmsg.** implements   com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
#    -keep class im.yixin.sdk.api.YXMessage {*;}
#    -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
#    -keep class com.tencent.mm.sdk.** {
#     *;
#    }
#    -keep class com.tencent.mm.opensdk.** {
#   *;
#    }
#    -dontwarn twitter4j.**
#    -keep class twitter4j.** { *; }
#
#    -keep class com.tencent.** {*;}
#    -dontwarn com.tencent.**
#    -keep public class com.umeng.com.umeng.soexample.R$*{
#    public static final int *;
#    }
#    -keep public class com.linkedin.android.mobilesdk.R$*{
#    public static final int *;
#        }
#    -keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#    }
#
#    -keep class com.tencent.open.TDialog$*
#    -keep class com.tencent.open.TDialog$* {*;}
#    -keep class com.tencent.open.PKDialog
#    -keep class com.tencent.open.PKDialog {*;}
#    -keep class com.tencent.open.PKDialog$*
#    -keep class com.tencent.open.PKDialog$* {*;}
#
#    -keep class com.sina.** {*;}
#    -dontwarn com.sina.**
#    -keep class  com.alipay.share.sdk.** {
#       *;
#    }
#    -keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#    }
#
#    -keep class com.linkedin.** { *; }
#    -keepattributes Signature

#支付宝
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}

#注解
-keep class * extends java.lang.annotation.Annotation { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }

#Glide
#-keepnames class * com.wufu.o2o.newo2o.glides.HomeGlideModule
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule
#or
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.integration.volley.VolleyGlideModule
#or
-keep public class * implements com.bumptech.glide.module.GlideModule

#xutil
#-libraryjars libs/x-2.6.14-src-1.3-modify.jar
-keep class com.lidroid.** { *; }
-keep interface * extends java.lang.annotation.Annotation { *; }




#Mpermission
-dontwarn com.zhy.m.**
-keep class com.zhy.m.** {*;}
-keep interface com.zhy.m.** { *; }
-keep class **$$PermissionProxy { *; }

-keepattributes *Annotation*
-keepattributes *JavascriptInterface*

#adapter
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(android.view.View);
}

-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.** { *;}
#这句非常重要，主要是滤掉 com.bgb.scan.model包下的所有.class文件不进行混淆编译
#-keep class com.yunjiaxiang.ztlib.net.bean.** {*;}
#-keep class com.yunjiaxiang.ztlib.bean.** {*;}
#-keep class com.zaaach.citypicker.model.** {*;}

#阿里云对象存储
#-keep class com.alibaba.sdk.android.oss.** { *; }
#-dontwarn okio.**
#-dontwarn org.apache.commons.codec.binary.**

#ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#okhttp
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

#微信支付
#-keep class com.tencent.mm.opensdk.** {
#   *;
#}
#-keep class com.tencent.wxop.** {
#   *;
#}
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#avLoading
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

#bugly
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
# tinker混淆规则
#-dontwarn com.tencent.tinker.**
#-keep class com.tencent.tinker.** { *; }

#-keepnames class com.yunjiaxiang.ztlib.global.GlobalGlideConfig
# or more generally:
#-keep public class * implements com.bumptech.glide.module.GlideModule

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule