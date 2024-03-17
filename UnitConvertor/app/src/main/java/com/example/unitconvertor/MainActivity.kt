package com.example.unitconvertor

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconvertor.ui.theme.UnitConvertorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConvertorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //  modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}


var listOfWeightUnits: List<String> = listOf("Kilogram", "Gram", "Pound", "Ounce");

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String) {
    var inputUnit by remember { mutableStateOf("100") }
    var result by remember { mutableStateOf("") }
    var convertFromUnit by remember { mutableStateOf("") }
    var convertToUnit by remember { mutableStateOf("") }

    var context= LocalContext.current
    println("Greeting ")
    // var value by remember { mutableStateOf("") }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        TextField(
            value = inputUnit,
            onValueChange = { inputUnit = it },
            label = { Text("Enter value") },
            maxLines = 2,
            enabled = true, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )

        DropDown(onSelected = {
            println("DropDown1 $it")
            convertFromUnit = it
            if (convertToUnit.isNotEmpty() && convertFromUnit.isNotEmpty()) {
                val res = convertUnit(inputUnit.toDouble(), convertFromUnit, convertToUnit)
                result = res.toString(); }
        })

        Text(text = "To")

        DropDown(onSelected = {
            println("DropDown2 $it")
            convertToUnit = it
            if (convertToUnit.isNotEmpty() && convertFromUnit.isNotEmpty()) {
                val res = convertUnit(inputUnit.toDouble(), convertFromUnit, convertToUnit)
                result = res.toString()
            }
        })


        Text(
            text = "Result: \n $inputUnit $convertFromUnit \n" +
                    "$result $convertToUnit ", modifier = Modifier.padding(20.dp),
            style = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        )



        Button(
            modifier = Modifier.padding(vertical = 90.dp),
            onClick = {
                openScientificCalculator(inputUnit, context)

            }) {
            Text(text = "Open Scientific Calculator")
        }

    }
}


/*
DropDownMenu common Component
* */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDown(onSelected: (String) -> Unit = { }) {

    var convertUnit by remember { mutableStateOf(listOfWeightUnits[0]) }
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded, onExpandedChange = {
            isExpanded = !isExpanded
        },
        modifier = Modifier.padding(20.dp)

        //  .fillMaxWidth()
        //.background(Color.Green)
    ) {
        TextField(value = convertUnit,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            modifier = Modifier.menuAnchor(),
            onValueChange = {

            })

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                println("DropdownMenuItem")

                isExpanded = false
            },
        ) {
            listOfWeightUnits.forEach {
                DropdownMenuItem(text = {
                    Text(text = it, fontSize = 18.sp)
                },
                    onClick = {
                        println("DropdownMenuItem $it")
                        convertUnit = it
                        onSelected(convertUnit)
                        isExpanded = false
                    })
            }
        }
    }
}

fun convertUnit(value: Double, originalUnit: String, targetUnit: String): Double {
    return when (originalUnit) {
        "Kilogram" -> {
            when (targetUnit) {
                "Kilogram" -> value
                "Gram" -> value * 1000
                "Pound" -> value * 2.20462
                "Ounce" -> value * 35.274
                else -> throw IllegalArgumentException("Unsupported target unit: $targetUnit")
            }
        }

        "Gram" -> {
            when (targetUnit) {
                "Kilogram" -> value / 1000
                "Gram" -> value
                "Pound" -> value * 0.00220462
                "Ounce" -> value * 0.035274
                else -> throw IllegalArgumentException("Unsupported target unit: $targetUnit")
            }
        }

        "Pound" -> {
            when (targetUnit) {
                "Kilogram" -> value * 0.453592
                "Gram" -> value * 453.592
                "Pound" -> value
                "Ounce" -> value * 16
                else -> throw IllegalArgumentException("Unsupported target unit: $targetUnit")
            }
        }

        "Ounce" -> {
            when (targetUnit) {
                "Kilogram" -> value * 0.0283495
                "Gram" -> value * 28.3495
                "Pound" -> value * 0.0625
                "Ounce" -> value
                else -> throw IllegalArgumentException("Unsupported target unit: $targetUnit")
            }
        }

        else -> throw IllegalArgumentException("Unsupported original unit: $originalUnit")
    }
}



fun openScientificCalculator(  inputUnit:String,context:Context) {
    println("ConvertApp openScientificCalculator")
    FlutterInstance.openScientificCalculatorScreen(inputUnit, context)
}