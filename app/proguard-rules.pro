# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\AndroidSDK_new\Sdk/tools/proguard/proguard-android.txt
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote android.app.**
-dontnote com.google.android.gms.common.internal.**
-dontnote android.app.**
-dontnote com.google.android.gms.internal.**
-dontnote com.google.gson.**
-dontnote okhttp3.internal.**
-dontnote twitter4j.*
-dontnote com.squareup.okhttp.**
-dontnote kotlin.**
-dontnote kotlin.jvm.**
-dontnote retrofit2.Platform
#-dontnote com.google.android.gms.common.internal.**


-dontwarn com.google.code.linkedinapi.**
-dontwarn com.google.code.linkedinapi.client.**
-dontwarn okhttp3.*
-dontwarn okhttp3.**
-dontwarn okio.*
-dontwarn twitter4j.*
-dontwarn twitter4j.**
-dontwarn retrofit.Platform.**
-dontwarn retrofit.Platform.*
-dontwarn retrofit.Platform$Java8
-dontwarn retrofit2.Platform$Java8

#-dontwarn com.google.code.linkedinapi.**


-keepattributes *Annotation*
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
-keep public class * extends com.android.vending.licensing.ILicensingService



-keep public class * extends com.vempower.eezyclinic.activities.AbstractActivity
-keep public class * extends com.vempower.eezyclinic.fragments.AbstractFragment
-keep public class * extends com.vempower.eezyclinic.fragments.*
-keep class com.vempower.eezyclinic.*
-keep class com.vempower.eezyclinic.**
-keep class com.vempower.eezyclinic.***


-keep class com.coremedia.iso.**
-keep class com.googlecode.mp4parser.*
-keep class com.googlecode.mp4parser.**
-keep class com.googlecode.***
-keep class com.google.android.gms.ads.*
-keep class com.google.android.gms.ads.*
-keep class com.android.webview.chromium.*
-keep class com.google.android.gms.ads.InterstitialAd

-keep class com.android.webview.**

-keep class com.google.android.***
-keep class com.google.android.gms.*

-keep class com.android.volley.**
-keep class com.appeaser.sublimepickerlibrary.**
-keep class com.crystal.crystalrangeseekbar.**
-keep class com.gc.materialdesign.**
-keep class com.github.aakira.expandablelayout.**
-keep class com.github.barteksc.pdfviewer.**
-keep class com.google.ads.mediation.**
-keep class com.google.firebase.**
-keep class com.hp.mss.hpprint.view.**
-keep class com.nex3z.togglebuttongroup.**
-keep class com.prolificinteractive.materialcalendarview.**
-keep class com.rey.material.widget.**
-keep class com.theartofdev.edmodo.cropper.**
-keep class com.vempower.eezyclinic.**
-keep class com.vempower.eezyclinic.***
-keep class me.zhanghai.android.materialratingbar.**
-keep class com.facebook.internal.**
-keep class com.facebook.widget.**
-keep class com.github.chuross.library.**
-keep class com.goodiebag.pinview.**
-keep class com.google.ads.**
-keep class com.hp.mss.hpprint.**
-keep class com.facebook.**
-keep class com.miguelcatalan.**
-keep class com.google.android.gms.**
-keep class com.google.gson.**
-keep class com.squareup.okhttp.**
-keep class kotlin.jvm.internal.**
-keep class com.google.android.gms.**
-keep class kotlin.internal.**
-keep class okhttp3.internal.**
-keep class okhttp3.*
-keep class twitter4j.*
-keep class twitter4j.**
-keep class kotlin.reflect.jvm.**
-keep class com.google.***
-keep class com.google.android.gms.**
-keep class android.app.AlertDialog.Builder
-keep class com.google.android.gms.common.internal.safeparcel.SafeParcelable
-keep class com.google.code.linkedinapi.schema.impl.*
-keep class com.google.code.linkedinapi.schema.*
-keep class javax.xml.bind.*
-keep class javax.xml.bind.**
-keep class om.google.code.linkedinapi.client.impl.*
-keep class org.apache.http.params.*
-keep class retrofit.Platform.**
-keep class retrofit.***
-keep class retrofit.*
-keep class retrofit.Platform.*
-keep class org.codehaus.mojo.**
-keep class android.support.v4.app.**
-keep class android.support.v4.app.*
-keep class android.support.v7.**
#-keep class org.codehaus.mojo.**
#-keep class org.codehaus.mojo.**
#-keep class org.codehaus.mojo.**

-dontwarn twitter4j.**
-keep  class twitter4j.conf.PropertyConfigurationFactory
-keep class twitter4j.** { *; }

-dontwarn twitter4j.**
-keep  class com.facebook.*
-keep class com.facebook.** { *; }




#-keep class com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder
#-keep class com.googlecode.mp4parser.authoring.container.mp4.MovieCreator
#-keep class com.googlecode.mp4parser.authoring.tracks.AppendTrack

#-keep public class * extends com.google.zxing.BarcodeFormat
#-keep public class * extends com.google.zxing.Result

#-keep public class * implements com.google.zxing.Result
##-keep public class * extends me.dm7.barcodescanner.zxing.ZXingScannerView

#-keep class com.google.zxing.** { *; }
#-keep class com.google.** { *; }
#-keep interface com.google.** { *; }

#-keep class org.apache.commons.codec.** { *; }
##-keep interface org.apache.commons.codec.** { *; }

#-keep class me.dm7.barcodescanner.** { *; }


-keep class android.app.AlertDialog.** { *; }
-keep class android.app.AlertDialog { *; }
-keep class android.app.AlertDialog.Builder
-keep class com.squareup.picasso.OkHttpDownloader
-dontwarn com.squareup.picasso.OkHttpDownloader

#Warning:com.squareup.picasso.OkHttpDownloader: can't find referenced class com.squareup.okhttp.ResponseBody
#import me.dm7.barcodescanner.core.IViewFinder;
#import me.dm7.barcodescanner.zxing.ZXingScannerView;


#import com.google.zxing.BarcodeFormat;
#import com.google.zxing.Result;

#Warning:butterknife.internal.ButterKnifeProcessor: can't find referenced class javax.lang.model.element.TypeElement

#-keep public class * extends com.vempower.stashdealcustomer.activities.AbstractActivity

#-keep public class * extends com.v_empowr.voicestry.activities.AbstractBackPressActivity
#-keep public class * extends com.v_empowr.voicestry.activities.AbstractMenuActivity
#-keep public class * extends com.v_empowr.voicestry.activities.AbstractMenuPlayerActivity
#-keep public class * extends com.v_empowr.voicestry.activities.AbstractPlayerBaseActivity

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class **.R$* {
    public static <fields>;
}



# Retrofit 2
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-dontwarn sun.misc.**

-dontwarn okio.**
-dontwarn okhttp3.**
-keep class retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}
-dontwarn retrofit.**

# Picasso
-dontwarn com.squareup.okhttp.**

-dontwarn android.support.**
-dontwarn java.lang.**
-dontwarn org.codehaus.**
-dontwarn com.google.**
-dontwarn java.nio.**
-dontwarn javax.annotation.**