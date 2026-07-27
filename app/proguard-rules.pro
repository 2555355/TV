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

# ============== R8 缺失类处理 ==============
# GeckoView 依赖 snakeyaml,它引用了 java.beans.*(Java SE 类,Android 上不存在)。
# 这些类在运行时不会被实际调用,只是 R8 静态分析时发现缺失就报错。
# 用 -dontwarn 抑制,R8 会跳过这些引用。
-dontwarn java.beans.**
-dontwarn org.yaml.snakeyaml.**
-dontwarn org.yaml.**

# R8 missing_rules.txt 自动生成的兜底规则
-keep,allowobfuscation class org.yaml.snakeyaml.** { *; }
