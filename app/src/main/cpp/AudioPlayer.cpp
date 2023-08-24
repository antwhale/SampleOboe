//
// Created by 2beone on 2023/08/23.
//

#include <thread>
#include <cinttypes>

#include "AudioPlayer.h"
#include "utils/logging.h"
#include "audio/AAssetDataSource.h"

AudioPlayer::AudioPlayer(AAssetManager &assetManager): mAssetManager(assetManager) {
}

void AudioPlayer::load(){
    if(!openStream()) {
        mAudioState = AudioState::FailedToLoad;
        return;
    }

    if(!setupAudioSources()) {
        mAudioState = AudioState::FailedToLoad;
        return;
    }

    LOGD("Call AudioPlayer::load");

    Result result = mAudioStream->requestStart();
    if(result != Result::OK) {
        LOGE("Failed to start stream. Error: %s", convertToText(result));
        mAudioState = AudioState::FailedToLoad;
        return;
    }

    mAudioState = AudioState::Playing;
}

void AudioPlayer::start(){
    mLoadingResult = std::async(&AudioPlayer::load, this);
}

void AudioPlayer::stop() {
    if(mAudioStream) {
        mAudioStream->stop();
        mAudioStream->close();
        mAudioStream.reset();
    }
}

DataCallbackResult AudioPlayer::onAudioReady(AudioStream *oboeStream, void *audioData, int32_t numFrames) {
//    auto *outputBuffer = static_cast<float*>(audioData);
    mBackingTrack->renderAudio(static_cast<float *>(audioData), numFrames);
    return DataCallbackResult::Continue;
}

bool AudioPlayer::openStream() {
    AudioStreamBuilder builder;
    builder.setFormat(AudioFormat::Float);
    builder.setFormatConversionAllowed(true);
    builder.setPerformanceMode(PerformanceMode::LowLatency);
    builder.setSharingMode(SharingMode::Exclusive);
    builder.setSampleRate(48000);
    builder.setSampleRateConversionQuality(SampleRateConversionQuality::Medium);
    builder.setChannelCount(2);
    builder.setDataCallback(this);
    Result result = builder.openStream(mAudioStream);

    if(result != Result::OK){
        LOGE("Failed to open stream. Error: %s", convertToText(result));
        return false;
    }
    return true;
}

bool AudioPlayer::setupAudioSources() {
    //Set the properties of our audio source to match those of our audio stream.
    AudioProperties targetProperties {
        .channelCount = 2,
        .sampleRate = 48000
    };

    //Create a data source and player for the clap sound.
    std::shared_ptr<AAssetDataSource> mTrackSource {
        AAssetDataSource::newFromCompressedAsset(mAssetManager, "FUNKY_HOUSE.mp3", targetProperties)
    };

    if(mTrackSource == nullptr) {
        LOGE("Could not load source data for clap sound");
        return false;
    }

    mBackingTrack = std::make_unique<Player>(mTrackSource);
    mBackingTrack->setPlaying(true);
    mBackingTrack->setLooping(true);
    return true;
}


