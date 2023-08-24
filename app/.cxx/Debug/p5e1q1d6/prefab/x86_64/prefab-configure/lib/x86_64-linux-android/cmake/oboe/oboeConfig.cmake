if(NOT TARGET oboe::oboe)
add_library(oboe::oboe SHARED IMPORTED)
set_target_properties(oboe::oboe PROPERTIES
    IMPORTED_LOCATION "/Users/2beone/.gradle/caches/transforms-3/be171cc19768883435f535b93f53fd42/transformed/jetified-oboe-1.5.0/prefab/modules/oboe/libs/android.x86_64/liboboe.so"
    INTERFACE_INCLUDE_DIRECTORIES "/Users/2beone/.gradle/caches/transforms-3/be171cc19768883435f535b93f53fd42/transformed/jetified-oboe-1.5.0/prefab/modules/oboe/include"
    INTERFACE_LINK_LIBRARIES ""
)
endif()

