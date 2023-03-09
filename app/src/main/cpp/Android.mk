LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hello-jni

LOCAL_LDLIBS := -llog

# We recommend avoiding absolute file paths; relative paths make your Android.mk file more portable.
# Always use Unix-style forward slashes (/) in build files. The build system does not handle Windows-style backslashes (\) properly.
LOCAL_SRC_FILES := $(LOCAL_PATH)/hello-jni.cpp $(LOCAL_PATH)/stl/container.cpp

include $(BUILD_SHARED_LIBRARY)