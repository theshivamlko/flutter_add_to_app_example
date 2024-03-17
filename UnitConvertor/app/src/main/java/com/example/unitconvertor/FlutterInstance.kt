package com.example.unitconvertor

import org.json.JSONObject

class FlutterInstance {


    companion object {
       private  val FLUTTER_CHANNEL="com.example.scientific_calculator_flutter"


        fun openScientificCalculatorScreen( unit:String) {
            val json=JSONObject()
            json.put("unit",unit)

            val methodChannel=MethodChannel

        }
    }
}