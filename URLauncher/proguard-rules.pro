-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclasses

-dontnote android.support.**
-dontnote androidx.**
-dontwarn android.support.**
-dontwarn androidx.**

-dontwarn android.util.FloatMath
-keep class androidx.annotation.Keep
-keep @androidx.annotation.Keep class * {*;}
-keepclasseswithmembers class * {@androidx.annotation.Keep <methods>;}
-keepclasseswithmembers class * {@androidx.annotation.Keep <fields>;}
-keepclasseswithmembers class * {@androidx.annotation.Keep <init>(...);}

-dontnote org.apache.http.**
-dontnote android.net.http.**
-dontnote java.lang.invoke.**

-keepclasseswithmembernames class * {native <methods>;}
-keepclassmembers public class * extends android.view.View {void set*(***); *** get*();}
-keepclassmembers class * extends android.app.Activity {public void *(android.view.View);}

-keepclassmembers enum * {public static **[] values(); public static ** valueOf(java.lang.String);}

-keepclassmembers class * implements android.os.Parcelable {public static final ** CREATOR;}

-keepclassmembers class **.R$* {public static <fields>;}
-keepclassmembers class * {@android.webkit.JavascriptInterface <methods>;}