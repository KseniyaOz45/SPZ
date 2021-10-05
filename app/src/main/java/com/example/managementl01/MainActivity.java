package com.example.managementl01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner typeSpinner;
    TextView locField;
    HashMap<String, Double> organicCoefficients = new HashMap();
    HashMap<String, Double> semi_dependentCoefficients = new HashMap<>();
    HashMap<String, Double> builtCoefficients = new HashMap<>();
    ArrayList<String> types = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        typeSpinner = (Spinner)findViewById(R.id.ProjectTypeSpinner);
        locField = (TextView)findViewById(R.id.LOK_Field);
        InitCoefficients();
        InitTypes();
    }

    private void InitCoefficients()
    {
        organicCoefficients.put("ai", 2.4);
        organicCoefficients.put("bi", 1.05);
        organicCoefficients.put("ci", 2.5);
        organicCoefficients.put("di", 0.38);

        semi_dependentCoefficients.put("ai", 3.0);
        semi_dependentCoefficients.put("bi", 1.12);
        semi_dependentCoefficients.put("ci", 2.5);
        semi_dependentCoefficients.put("di", 0.35);

        builtCoefficients.put("ai", 3.6);
        builtCoefficients.put("bi", 1.20);
        builtCoefficients.put("ci", 2.5);
        builtCoefficients.put("di", 0.32);
    }

    private void InitTypes()
    {
        types.add("Розповсюджений (органічний)");
        types.add("Напівнезалежний (напіврозподілений)");
        types.add("Вбудований");
        InitTypesSpinner();
    }

    private void InitTypesSpinner()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    public void CalculateChecker(View view)
    {
        String check = typeSpinner.getSelectedItem().toString();
        switch (check)
        {
            case "Розповсюджений (органічний)":
                Calculate(organicCoefficients);
                break;
            case "Напівнезалежний (напіврозподілений)":
                Calculate(semi_dependentCoefficients);
                break;
            case "Вбудований":
                Calculate(builtCoefficients);
                break;

        }
    }

    private void Calculate(HashMap coefficients)
    {
        try {
            int size = Integer.parseInt(locField.getText().toString());
            double PM, TM, SS, P;

            PM = (Double) coefficients.get("ai") * Math.pow(size, (Double) coefficients.get("bi"));
            TM = (Double) coefficients.get("ci") * Math.pow(PM, (Double) coefficients.get("di"));
            SS = PM / TM;
            P = size / PM;

            String message = "Трудомісткість: " + PM + "\nЧас розробки в місяцях: " + TM +
                    "\nСередня чисельність персоналу: " + SS + "\nПродуктивність: " + P;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG);
        }
    }
}