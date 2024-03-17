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
            try {
                val json = JSONObject()
                json.put("unit", unit)
                println("ConvertApp openScientificCalculator1 $json")

                val methodChannel = MethodChannel(
                    AppSingleton.flutterEngine.dartExecutor.binaryMessenger,
                    FLUTTER_CHANNEL
                )

                context.startActivity(
                    FlutterActivity.withCachedEngine(AppSingleton.FLUTTER_ENGINE_NAME)
                        .build(context)
                ).apply {
                    println("ConvertApp openScientificCalculator apply ${this}")
                    methodChannel.invokeMethod("calculateWeightOnPlanets", json.toString())

                }




            } catch (e: Exception) {
                println("ConvertApp openScientificCalculator error ${e.message}")
                e.printStackTrace()
            }

        }



        fun receiveResultsFromFlutter(onReceive:(String)->Unit){
            val methodChannel = MethodChannel(
                AppSingleton.flutterEngine.dartExecutor.binaryMessenger,
                FLUTTER_CHANNEL
            )

            methodChannel.setMethodCallHandler{
                call, result ->
                if(call.method=="receiveResultsFromFlutter"){
                    print("ConvertApp receiveResultsFromFlutter ${result} ${call.arguments}")
                    val data=call.arguments as String
                    onReceive(data)
                }
            }



        }
    }
}