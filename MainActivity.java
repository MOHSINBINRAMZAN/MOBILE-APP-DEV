package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure layout resource exists and is correct

        // Assign UI elements by ID
        EditText heightEditText = findViewById(R.id.heightEditText);
        EditText weightEditText = findViewById(R.id.weightEditText);
        Button calculateButton = findViewById(R.id.calculateButton);
        TextView bmiResultTextView = findViewById(R.id.bmiResultTextView);

        // Set button click listener with error handling
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String heightText = heightEditText.getText().toString();
                    String weightText = weightEditText.getText().toString();

                    // Validate that inputs are not empty
                    if (heightText.isEmpty() || weightText.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Convert text to double with null handling
                    Double height = toDoubleOrNull(heightText);
                    Double weight = toDoubleOrNull(weightText);

                    // Check for valid inputs
                    if (height == null || weight == null) {
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Calculate BMI and determine the category
                    double bmi = calculateBMI(height, weight);
                    String bmiCategory = getBMICategory(bmi);

                    // Display the result
                    String resultText = String.format("Your BMI is %.2f. You are %s.", bmi, bmiCategory);
                    bmiResultTextView.setText(resultText);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "An error occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private Double toDoubleOrNull(String text) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }


    private double calculateBMI(double height, double weight) {
        return weight / (height * height);
    }


    private String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            return "normal weight";
        } else if (bmi >= 25 && bmi < 29.9) {
            return "overweight";
        } else {
            return "obese";
        }
    }
}
