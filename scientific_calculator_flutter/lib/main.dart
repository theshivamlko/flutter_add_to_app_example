import 'package:flutter/material.dart';

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
  String unit = "";
  String result = "";

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
              "Input: $unit",
              style: TextStyle(fontSize: 25),
            ),
            Padding(padding: EdgeInsets.all(10)),
            DropdownButton<String>(
              items: planetList.map((String planet) {
                return DropdownMenuItem<String>(
                  value: planet,
                  child: Text(planet,style: TextStyle(fontSize: 20),),
                );
              }).toList(),
              value: selectedPlanet,
              onChanged: (String? value) {
                setState(() {
                  selectedPlanet=value!;
                result=  calculateWeightOnPlanets(double.parse(unit), selectedPlanet);

                });
              },
            ),
            Padding(padding: EdgeInsets.all(10)),
            Text(
              "Output: $result",
              style: TextStyle(fontSize: 25),
            )
          ],
        ),
      ),
    );
  }


  String calculateWeightOnPlanets(double weightOnEarth, String selectedPlanet) {

      double weightOnPlanet;
      switch ( selectedPlanet) {
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
