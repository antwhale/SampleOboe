//
// Created by 2beone on 2023/08/23.
//

#ifndef BOOBASESAMPLE_AUDIOPLAYER_H
#define BOOBASESAMPLE_AUDIOPLAYER_H

#include <future>

#include <android/asset_manager.h>
#include <oboe/Oboe.h>

#include "Mixer.h"

#include "audio/Player.h"
#include "audio/AAssetDataSource.h"
#include "utils/LockFreeQueue.h"
#include "utils/UtilityFunctions.h"



using namespace oboe;

enum class AudioState {
    Loading,
    Playing,
    FailedToLoad
};

class AudioPlayer : public AudioStreamDataCallback, AudioStreamErrorCallback {
public:
    explicit AudioPlayer(AAssetManager&);
    void start();
    void stop();

    DataCallbackResult
    onAudioReady(AudioStream *oboeStream, void *audioData, int32_t numFrames) override;
//    void onErrorAfterClose(AudioStream *oboeStream, Result error) override;


private:
    AAssetManager& mAssetManager;
    std::unique_ptr<Player> mBackingTrack;
    std::unique_ptr<Player> mClap;
    std::shared_ptr<AudioStream> mAudioStream;


    std::atomic<AudioState> mAudioState { AudioState::Loading };
    std::future<void> mLoadingResult;

    void load();
    bool openStream();
    bool setupAudioSources();
};

#endif //BOOBASESAMPLE_AUDIOPLAYER_H
