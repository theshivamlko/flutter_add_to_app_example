import 'dart:convert';

import 'package:flutter/services.dart';

const platform =
    const MethodChannel("com.example.scientific_calculator_flutter");

void listenFromNativeCall({required ValueChanged<String> onValueReceived}) {
  platform.setMethodCallHandler((MethodCall methodCall) async {
    receiveFromNativeCall(
        methodCall: methodCall, onValueReceived: onValueReceived);
  });
  
 }

void receiveFromNativeCall(
    {required MethodCall methodCall,
    required ValueChanged<String> onValueReceived}) {
  print("ConvertApp receiveFromNativeCall ${methodCall.method}");
  if (methodCall.method == "calculateWeightOnPlanets") {
    String args = methodCall.arguments;
    print("ConvertApp receiveFromNativeCall args $args");
    Map<String, dynamic> map = json.decode(args);

    String unit = map["unit"];
    onValueReceived(unit);
  }
}


void sendToNativeCall(String args) {
  print("ConvertApp sendToNativeCall args $args");

  platform.invokeMethod("receiveResultsFromFlutter", args);
}
