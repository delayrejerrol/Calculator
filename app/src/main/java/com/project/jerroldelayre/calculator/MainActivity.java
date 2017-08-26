package com.project.jerroldelayre.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText editTextInfo;
    EditText editTextOutput;

    private DecimalFormat df = new DecimalFormat("#.##########");
    private String sCurrentOperator = "", sPreviousOperator = "", sHistory = "", sCurrentNumber = "", sPreviousNumber = "", sTotal = "", sLastIndex = "";
    private String lastTempNumber, lastTempOperator;
    private double dTotal = 0;

    private int iHistoryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInfo = (EditText) findViewById(R.id.edittext_info);
        editTextOutput = (EditText) findViewById(R.id.edittext_output);
    }

    public void getValue(View view) {
        String getValue = ((Button) view).getText().toString();

        if (getValue.contains("C") || getValue.contains("CE")) {
            clear();
            return;
        }

        boolean isOperator = getValue.equals("+") || getValue.equals("-") || getValue.equals("*") || getValue.equals("/");
        boolean isEqual = getValue.equals("=");
        if (isEqual) {
            iHistoryCount++;

            if (sLastIndex.equals("=")) {
                sPreviousNumber = lastTempNumber;
            } else {
                sPreviousNumber = editTextOutput.getText().toString();
                lastTempNumber = sPreviousNumber;
            }
            Log.i(TAG, "sLastIndex: " + sLastIndex + " sPreviousNumber: " + sPreviousNumber + " sPreviousOperator: " + sPreviousOperator);
            computeNumber();
            editTextInfo.setText("");

            sLastIndex = "=";
        }
        else if (isOperator) {
            if (sCurrentNumber.equals("")) return;
            if (!sLastIndex.equals("") && !sLastIndex.equals("=")) {
                return;
            }

            if (sLastIndex.equals("=")) {
                iHistoryCount = 0;
                sHistory = "";
                sCurrentNumber = editTextOutput.getText().toString();
                //editTextInfo.setText(sCurrentNumber);
            }

            sCurrentOperator = getValue;
            sPreviousNumber = sCurrentNumber;
            sHistory += sPreviousNumber + sCurrentOperator;
            if (sPreviousOperator.equals("")) sPreviousOperator = sCurrentOperator;

            iHistoryCount++;
            switch (getValue) {
                case "+":
                    computeNumber();
                    break;
                case "-":
                    computeNumber();
                    //editTextInfo.setText(sHistory);
                    break;
                case "*":
                    computeNumber();
                    //editTextInfo.setText(sHistory);
                    break;
                case "/":
                    computeNumber();
                    //editTextInfo.setText(sHistory);
                    break;
            }
            sPreviousOperator = sCurrentOperator;
            sCurrentOperator = "";
            sCurrentNumber = "";

            sLastIndex = getValue;
        } else {
            if (sCurrentNumber.contains(".") && getValue.equals(".")) return;
            if (getValue.equals(".") && sCurrentNumber.equals("")) {
                sCurrentNumber = "0";
            }
            sCurrentNumber += getValue;
            editTextOutput.setText(sCurrentNumber);
            sLastIndex = "";
        }
    }

    private void clear() {
        editTextInfo.setText("");
        editTextOutput.setText("");
        sCurrentOperator = "";
        sPreviousOperator = "";
        sHistory = "";
        sCurrentNumber = "";
        sPreviousNumber = "";
        sTotal = "";
        sLastIndex = "";
        dTotal = 0;
        iHistoryCount = 0;
        lastTempNumber = "";
        lastTempOperator = "";
    }

    private void clearEqual() {
        editTextInfo.setText("");
        //editTextOutput.setText("");
        sCurrentOperator = "";
        sPreviousOperator = "";
        sHistory = "";
        sCurrentNumber = "";
        sPreviousNumber = "";
        sTotal = "";
        sLastIndex = "";
        dTotal = 0;
        iHistoryCount = 0;
    }

    private void addNumber() {
        //Log.i(TAG, "" + sPreviousNumber + sPreviousOperator + dTotal);
        double previousNumber = Double.parseDouble(sPreviousNumber);
        if (iHistoryCount == 1) {
            dTotal = previousNumber + 0;
            editTextInfo.setText(sHistory);
            editTextOutput.setText(df.format(previousNumber));
            return;
        }
        dTotal += previousNumber;
        editTextInfo.setText(sHistory);
        editTextOutput.setText(df.format(dTotal));
    }

    private void subtractNumber() {
        double previousNumber = Double.parseDouble(sPreviousNumber);
        if (iHistoryCount == 1) {
            dTotal = previousNumber - 0;
            editTextInfo.setText(sHistory);
            editTextOutput.setText(df.format(previousNumber));
            return;
        }
        dTotal -= previousNumber;
        editTextInfo.setText(sHistory);
        editTextOutput.setText(df.format(dTotal));
    }

    private void multiplyNumber() {
        double previousNumber = Double.parseDouble(sPreviousNumber);
        if (iHistoryCount == 1) {
            dTotal = previousNumber * 1;
            editTextInfo.setText(sHistory);
            editTextOutput.setText(df.format(previousNumber));
            return;
        }
        dTotal *= previousNumber;
        editTextInfo.setText(sHistory);
        editTextOutput.setText(df.format(dTotal));
    }

    private void divideNumber() {
        double previousNumber = Double.parseDouble(sPreviousNumber);
        if (iHistoryCount == 1) {
            dTotal = previousNumber;
            editTextInfo.setText(sHistory);
            editTextOutput.setText(df.format(previousNumber));
            return;
        }
        dTotal /= previousNumber;
        editTextInfo.setText(sHistory);
        editTextOutput.setText(df.format(dTotal));
    }

    private void computeNumber() {
        if (sPreviousOperator.equals("+")) {
            addNumber();
        } else if (sPreviousOperator.equals("-")) {
            subtractNumber();
        } else if (sPreviousOperator.equals("*")) {
            multiplyNumber();
        } else if (sPreviousOperator.equals("/")) {
            divideNumber();
        }
    }
}