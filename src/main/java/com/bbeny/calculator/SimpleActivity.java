package com.bbeny.calculator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SimpleActivity extends Activity implements View.OnClickListener {

    private TextView result;
    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button bksp;
    private Button c;
    private Button sign;
    private Button plus;
    private Button minus;
    private Button multiplication;
    private Button division;
    private Button equal;
    private Button dot;
    private String result1;
    private String result2;
    private String operation;
    private boolean signActive;
    private boolean equalState;
    private boolean errorState;
    private String zeroChar;
    private String plusChar;
    private String minusChar;
    private String multiplicationChar;
    private String divisionChar;
    private String equalsChar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        result = (TextView) findViewById(R.id.result);
        zero = (Button) findViewById(R.id.zero);
        zero.setOnClickListener(this);
        one = (Button) findViewById(R.id.one);
        one.setOnClickListener(this);
        two = (Button) findViewById(R.id.two);
        two.setOnClickListener(this);
        three = (Button) findViewById(R.id.three);
        three.setOnClickListener(this);
        four = (Button) findViewById(R.id.four);
        four.setOnClickListener(this);
        five = (Button) findViewById(R.id.five);
        five.setOnClickListener(this);
        six = (Button) findViewById(R.id.six);
        six.setOnClickListener(this);
        seven = (Button) findViewById(R.id.seven);
        seven.setOnClickListener(this);
        eight = (Button) findViewById(R.id.eight);
        eight.setOnClickListener(this);
        nine = (Button) findViewById(R.id.nine);
        nine.setOnClickListener(this);
        bksp = (Button) findViewById(R.id.bksp);
        bksp.setOnClickListener(this);
        c = (Button) findViewById(R.id.c);
        c.setOnClickListener(this);
        sign = (Button) findViewById(R.id.sign);
        sign.setOnClickListener(this);
        plus = (Button) findViewById(R.id.plus);
        plus.setOnClickListener(this);
        minus = (Button) findViewById(R.id.minus);
        minus.setOnClickListener(this);
        multiplication = (Button) findViewById(R.id.multiplication);
        multiplication.setOnClickListener(this);
        division = (Button) findViewById(R.id.division);
        division.setOnClickListener(this);
        equal = (Button) findViewById(R.id.equal);
        equal.setOnClickListener(this);
        dot = (Button) findViewById(R.id.dot);
        dot.setOnClickListener(this);
        zeroChar = getString(R.string.zero);
        plusChar = getString(R.string.plus);
        minusChar = getString(R.string.minus);
        multiplicationChar = getString(R.string.multiplication);
        divisionChar = getString(R.string.division);
        equalsChar = getString(R.string.equals);
        init();
    }

    private void init() {
        result.setText("0");
        result1 = "0";
        result2 = "0";
        operation = "";
        signActive = false;
        equalState = false;
        errorState = false;
        deactivateSigns();
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.zero: {
                addDigit(zeroChar);
                break;
            }
            case R.id.one: {
                addDigit(getString(R.string.one));
                break;
            }
            case R.id.two: {
                addDigit(getString(R.string.two));
                break;
            }
            case R.id.three: {
                addDigit(getString(R.string.three));
                break;
            }
            case R.id.four: {
                addDigit(getString(R.string.four));
                break;
            }
            case R.id.five: {
                addDigit(getString(R.string.five));
                break;
            }
            case R.id.six: {
                addDigit(getString(R.string.six));
                break;
            }
            case R.id.seven: {
                addDigit(getString(R.string.seven));
                break;
            }
            case R.id.eight: {
                addDigit(getString(R.string.eight));
                break;
            }
            case R.id.nine: {
                addDigit(getString(R.string.nine));
                break;
            }
            case R.id.dot: {
                addDot();
                break;
            }
            case R.id.plus: {
                addSign(plusChar);
                break;
            }
            case R.id.minus: {
                addSign(minusChar);
                break;
            }
            case R.id.multiplication: {
                addSign(multiplicationChar);
                break;
            }
            case R.id.division: {
                addSign(divisionChar);
                break;
            }
            case R.id.equal: {
                equalClicked();
                break;
            }
            case R.id.c: {
                init();
                break;
            }
            case R.id.sign: {
                changeSign();
                break;
            }
            case R.id.bksp: {
                backspace();
                break;
            }
        }
    }

    private void addDigit(String digit) {
        if(signActive) {
            clearText();
            signActive = false;
        }
        if(equalState || errorState) {
            init();
        }
        if(resultEmpty()) {
            setText(digit);
        } else if (resultMinusZero()) {
            setText(getText().substring(0, 1) + digit);
        } else {
            addText(digit);
        }
    }

    private void addDot() {
        String currentResult = getText();
        String dot = getString(R.string.dot);
        if (signActive) {
            clearText();
            signActive = false;
            addText(dot);
        }
        else if (equalState || errorState) {
            init();
            addText(dot);
        }
        else if (!currentResult.contains(dot)) {
            addText(dot);
        }
    }

    private void addSign(String sign) {
        if(errorState) {
            init();
        }
        activeSign(sign);
        if(!signActive && !equalState) {
            result1 = count(result1, getText());
            setText(result1);
        }
        operation = sign;
        equalState = false;
        signActive = true;
    }

    private void equalClicked() {
        if(!equalState) {
            result2 = getText();
        }
        result1 = count(result1, result2);
        setText(result1);
        equalState = true;
        activeSign(equalsChar);
    }

    private void changeSign() {
        if(equalState || errorState) {
            init();
        } else if (signActive) {
            setText(zeroChar);
            signActive = false;
        }
        String currentResult = getText();
        String firstDigit = currentResult.substring(0, 1);
        if(firstDigit.equals(minusChar)) {
            currentResult = currentResult.substring(1);
        } else {
            currentResult = minusChar + currentResult;
        }
        setText(currentResult);
    }

    private void backspace() {
        String currentResult = getText();
        String firstDigit = currentResult.substring(0, 1);
        int length = currentResult.length();
        if (errorState) {
            init();
        } else if(length == 1 || resultMinusZero()) {
            setText("0");
        } else if (length == 2 && firstDigit.equals(minusChar)) {
            setText("-0");
        } else {
            setText(currentResult.substring(0, currentResult.length() - 1));
        }
        if(equalState) {
            result1 = getText();
        }
    }

    private void activeSign(String sign) {
        deactivateSigns();
        if(sign.equals(plusChar)) {
            plus.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(minusChar)){
            minus.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(multiplicationChar)){
            multiplication.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(divisionChar)){
            division.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(equalsChar)){
            equal.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
    }

    private void deactivateSigns() {
        plus.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        minus.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        multiplication.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        division.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
        equal.getBackground().setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
    }

    private String count(String left, String right) {
        float x = Float.parseFloat(left);
        float y = Float.parseFloat(right);
        if (operation.equals(plusChar)) {
            x += y;
        } else if (operation.equals(minusChar)) {
            x -= y;
        } else if (operation.equals(multiplicationChar)) {
            x *= y;
        } else if (operation.equals(divisionChar)) {
            if (y == 0) {
                errorState = true;
                return("Error");
            }
            x /= y;
        } else {
            x = y;
        }
        if(x == (long) x) {
            return String.format("%d",(long)x);
        }
        else {
            return String.format("%s",x);
        }
    }

    private String getText() {
        return result.getText().toString();
    }

    private void setText(String text) {
        result.setText(text);
    }

    private void clearText() {
        setText("0");
    }

    private void addText(String text) {
        setText(getText() + text);
    }

    private boolean resultEmpty() {
        return getText().equals(zeroChar);
    }

    private boolean resultMinusZero() {
        return getText().equals(minusChar + zeroChar);
    }

}
