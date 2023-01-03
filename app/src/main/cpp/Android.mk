LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := hello-jni

# We recommend avoiding absolute file paths; relative paths make your Android.mk file more portable.
# Always use Unix-style forward slashes (/) in build files. The build system does not handle Windows-style backslashes (\) properly.
LOCAL_SRC_FILES := $(LOCAL_PATH)/hello-jni.c

include $(BUILD_SHARED_LIBRARY)