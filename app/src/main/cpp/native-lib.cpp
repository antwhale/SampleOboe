//
// Created by 2beone on 2023/08/21.
#include <jni.h>
#include <string>
#include <memory>
#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>
#include <oboe/Oboe.h>
#include <android/log.h>
#include "AudioPlayer.h"
#include "utils/logging.h"


//#define LOGE(...)
//#define LOGI(...)

//Oboe
std::unique_ptr<AudioPlayer> audioPlayer;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_stringFromJNI(JNIEnv *env, jclass clazz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_oboeStart(JNIEnv *env, jclass clazz, jobject jAssetManager) {
    LOGD("oboeStart");

    AAssetManager *assetManager = AAssetManager_fromJava(env, jAssetManager);
    if (assetManager == nullptr) {
        LOGE("Could not obtain the AAssetManager");
        return;
    }

    audioPlayer = std::make_unique<AudioPlayer>(*assetManager);
    audioPlayer->start();

}

extern "C"
JNIEXPORT void JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_oboeStop(JNIEnv *env, jclass clazz) {
    audioPlayer->stop();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_oboeSetDefaultStreamValue(JNIEnv *env, jclass clazz,
                                                              jint sampleRate,
                                                              jint framesPerBurst) {
    LOGD("oboeSetDefaultStreamValue");

    oboe::DefaultStreamValues::SampleRate = (int32_t) sampleRate;
    oboe::DefaultStreamValues::FramesPerBurst = (int32_t) framesPerBurst;
}
