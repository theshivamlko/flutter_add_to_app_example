package com.example.unitconvertor

import android.app.Application


class ApplicationSingleton:Application() {



    companion object{
        lateinit var flutterEngine:FlutterEngine

    }


}