import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:scientific_calculator_flutter/plugins.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  List<String> planetList = ["Moon", "Jupiter", "Mars"];

  String selectedPlanet = "Moon";
  double inputUnit = 0.0;
  String result = "";

  @override
  void initState() {
    // TODO: implement initState
    super.initState();

    listenFromNativeCall(
      onValueReceived: (value) {
        print("listenFromNativeCall $value");
        inputUnit=double.parse(value);
        result = calculateWeightOnPlanets(
            inputUnit, selectedPlanet);
        setState(() {

        });
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          children: <Widget>[
            Text(
              "Input: $inputUnit (Kg)",
              style: TextStyle(fontSize: 25),
            ),
            Padding(padding: EdgeInsets.all(10)),
            DropdownButton<String>(
              items: planetList.map((String planet) {
                return DropdownMenuItem<String>(
                  value: planet,
                  child: Text(
                    planet,
                    style: TextStyle(fontSize: 20),
                  ),
                );
              }).toList(),
              value: selectedPlanet,
              onChanged: (String? value) {
                setState(() {
                  selectedPlanet = value!;

                  result = calculateWeightOnPlanets(
                      inputUnit, selectedPlanet);
                });
              },
            ),
            Padding(padding: EdgeInsets.all(10)),
            Text(
              "Output: $selectedPlanet -> $result (Kg)",
              style: const TextStyle(fontSize: 25),
            ),

            Padding(padding: EdgeInsets.all(10)),
            ElevatedButton(onPressed: () {
              sendToNativeCall(jsonEncode({"planet":selectedPlanet,"weight":result}));
            }, child: const Text("Send Result to Native"))


          ],
        ),
      ),
    );
  }

  String calculateWeightOnPlanets(double weightOnEarth, String selectedPlanet) {
    double weightOnPlanet;
    switch (selectedPlanet) {
      case "Moon":
        weightOnPlanet = weightOnEarth * 0.165;
        break;
      case "Jupiter":
        weightOnPlanet = weightOnEarth * 2.354;
        break;
      case "Mars":
        weightOnPlanet = weightOnEarth * 0.376;
        break;
      default:
        throw Exception("Unsupported planet: $selectedPlanet");
    }

    return weightOnPlanet.toString();
  }
}
