//
// Created by 2beone on 2023/08/21.
#include <jni.h>
#include <string>
#include <memory>
#include <android/asset_manager_jni.h>
#include <android/asset_manager.h>
#include <oboe/Oboe.h>
#include <android/log.h>


#define LOGE(...)
#define LOGI(...)

/*extern "C" JNIEXPORT jstring JNICALL
Java_com_boo_sample_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject  thiz ) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}*/

//Oboe
oboe::AudioStreamBuilder builder;

class AudioDataCallback : public oboe::AudioStreamDataCallback {
    public:
        oboe::DataCallbackResult
        onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) {
            //We requested AudioFormat::Float. So if the stream opens we know we got the Float format
            // If you do not specify a format then you should check what format
            // the stream has and cast to the appropriate type.
            auto *outputData = static_cast<float *>(audioData);

            //Generate random numbers (white noise) centered around zero.
            const float amplitude = 0.2f;
            for (int i = 0; i < numFrames; ++i) {
                outputData[i] = ((float) drand48() - 0.5f) * 2 * amplitude;
            }
            return oboe::DataCallbackResult::Continue;
        }
};

std::shared_ptr<oboe::AudioStream> mStream;

struct AudioProperties {
    int32_t channelCount;
    int32_t sampleRate;
};

//bool setUpAudioSource() {
//    AudioProperties targetProperties {
//        .channelCount = 2,
//        .sampleRate = 48000
//    };

//}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_stringFromJNI(JNIEnv *env, jclass clazz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_oboeStart(JNIEnv *env, jclass clazz, jobject asset_manager) {
//    oboe::Result result = builder.openStream(mStream);
//
//    if(result != oboe::Result::OK) {
//        LOGE("Failed to create stream. Error: %s", oboe::convertToText(result))
//    }
//
//    oboe::AudioFormat format = mStream->getFormat();
//    LOGI("AudioStream format is %s", oboe::convertToText(format))
//
//    mStream->requestStart();

}

extern "C"
JNIEXPORT void JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_oboeStop(JNIEnv *env, jclass clazz) {
    if(mStream) {
        mStream->stop();
        mStream->close();
        mStream.reset();
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_boo_sample_oboe_OboeUtilKt_oboeSetDefaultStreamValue(JNIEnv *env, jclass clazz,
                                                              jint sampleRate,
                                                              jint framesPerBurst) {
    oboe::DefaultStreamValues::SampleRate = (int32_t) sampleRate;
    oboe::DefaultStreamValues::FramesPerBurst = (int32_t) framesPerBurst;

    AudioDataCallback dataCallback;

    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setSharingMode(oboe::SharingMode::Exclusive);
    builder.setDataCallback(&dataCallback);
    builder.setFormat(oboe::AudioFormat::Float);
    builder.setChannelCount(oboe::ChannelCount::Mono);
}
