//
// Created by admin on 2023/3/9.
//

#ifndef ANDROID_LAB_CONTAINER_H
#define ANDROID_LAB_CONTAINER_H

#endif //ANDROID_LAB_CONTAINER_H

#include<android/log.h>

#define  LOG_TAG    "android-lab"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

void
test_logcat();