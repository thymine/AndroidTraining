#include <jni.h>
#include "stl/container.h"
#include<iostream>

using namespace std;

extern "C"
JNIEXPORT jstring

JNICALL
Java_me_zhang_laboratory_ui_JniActivity_callFromJni(JNIEnv *env, jobject thiz) {
    string hello = "Hello from C++";

    test_logcat();

    return env->NewStringUTF(hello.c_str());
}