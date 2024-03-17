package com.example.unitconvertor

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


class AppSingleton:Application() {


    companion object{
        lateinit var flutterEngine: FlutterEngine
        const val FLUTTER_ENGINE_NAME="nps_flutter_engine_name"

    }


    override fun onCreate() {
        super.onCreate()

        flutterEngine= FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )


        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_NAME, flutterEngine)

    }


}