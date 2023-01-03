#include <jni.h>

JNIEXPORT jstring JNICALL
Java_me_zhang_laboratory_ui_JniActivity_callFromJni(JNIEnv *env, jobject thiz) {
    return (*env)->NewStringUTF(env, "Hello From JNI");
}