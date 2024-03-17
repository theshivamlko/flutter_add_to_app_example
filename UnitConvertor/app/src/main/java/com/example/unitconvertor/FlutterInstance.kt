package com.example.unitconvertor

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject

class FlutterInstance {


    companion object {
       private  val FLUTTER_CHANNEL="com.example.scientific_calculator_flutter"


        fun openScientificCalculatorScreen( unit:String, context: Context) {
            val json=JSONObject()
            json.put("unit",unit)

            val methodChannel= MethodChannel(
                AppSingleton.flutterEngine.dartExecutor.binaryMessenger,
                FLUTTER_CHANNEL
            )

            methodChannel.invokeMethod("calculateWeightOnPlanets",json.toString())

            context.startActivity(
                FlutterActivity.withCachedEngine(AppSingleton.FLUTTER_ENGINE_NAME).build(context))

        }
    }
}