package com.boo.sample.oboe

import android.content.res.AssetManager

class OboeUtil {

}

external fun stringFromJNI() : String

external fun oboeStart(assetManager: AssetManager)

external fun oboeStop()

external fun oboeSetDefaultStreamValue(defaultSampleRate: Int, defaultFramesPerBurst: Int)

