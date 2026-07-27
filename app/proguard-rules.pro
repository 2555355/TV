# ============== GeckoView (Firefox 内核) ==============
# native 调用大量使用 JNI 反射,必须保留全部
-keep class org.mozilla.geckoview.** { *; }
-keep class org.mozilla.gecko.** { *; }
-keepclassmembers class org.mozilla.geckoview.** { *; }
-keepclassmembers class org.mozilla.gecko.** { *; }

# ============== Gson 数据模型(反射反序列化) ==============
-keep class com.tvfoxbrowser.HistoryManager$Item { *; }
-keep class com.tvfoxbrowser.BookmarkManager$Item { *; }
-keep class com.tvfoxbrowser.SearchEngine$Engine { *; }

# Gson 自身
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**
# 保留泛型签名(否则 TypeToken 解析失败)
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# ============== Kotlin Coroutines ==============
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# ============== AndroidX / Leanback ==============
-dontwarn androidx.leanback.**
-keep class androidx.leanback.** { *; }
-keep class androidx.preference.** { *; }

# ============== ViewBinding / 数据绑定 ==============
-keep class com.tvfoxbrowser.databinding.** { *; }

# ============== Application 入口 ==============
-keep class com.tvfoxbrowser.TvFoxApp { *; }
-keep class com.tvfoxbrowser.MainActivity { *; }
-keep class com.tvfoxbrowser.** extends androidx.fragment.app.Fragment { *; }

# ============== 通用 ==============
-dontwarn org.mozilla.**
-dontwarn javax.annotation.**
