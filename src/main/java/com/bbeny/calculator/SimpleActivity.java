package com.bbeny.calculator;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class SimpleActivity extends Activity implements View.OnClickListener {

    TextView result;
    Button zero;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button bksp;
    Button c;
    Button sign;
    Button plus;
    Button minus;
    Button multiplication;
    Button division;
    Button equal;
    Button dot;
    String result1;
    String result2;
    String operation;
    boolean signActive;
    boolean equalState;
    boolean errorState;

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
                addDigit(getString(R.string.zero));
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
                addSign(getString(R.string.plus));
                break;
            }
            case R.id.minus: {
                addSign(getString(R.string.minus));
                break;
            }
            case R.id.multiplication: {
                addSign(getString(R.string.multiplication));
                break;
            }
            case R.id.division: {
                addSign(getString(R.string.division));
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

    private void backspace() {
        String currentResult = getText();
        String firstDigit = currentResult.substring(0, 1);
        int length = currentResult.length();
        if (errorState) {
            init();
        } else if(length == 1 || resultEmptyMinus()) {
            setText("0");
        } else if (length == 2 && firstDigit.equals(getString(R.string.minus))) {
            setText("-0");
        }
        else {
            setText(currentResult.substring(0, currentResult.length() - 1));
        }
        if(equalState) {
            result1 = getText();
        }
    }

    public void changeSign() {
        if(equalState || errorState) {
            init();
        } else if (signActive) {
            setText(getString(R.string.zero));
            signActive = false;
        }
        String currentResult = getText();
        String firstDigit = currentResult.substring(0, 1);
        if(firstDigit.equals(getString(R.string.minus))) {
            currentResult = currentResult.substring(1);
        } else {
            currentResult = getString(R.string.minus) + currentResult;
        }
        setText(currentResult);
    }
    public void addDigit(String digit) {
        if(signActive) {
            clearText();
            signActive = false;
        }
        if(equalState || errorState) {
            init();
        }
        if(resultEmpty()) {
            setText(digit);
        } else if (resultEmptyMinus()) {
            setText(getText().substring(0, 1) + digit);
        } else {
            addText(digit);
        }
    }

    public void addDot() {
        String currentResult = getText();
        String dot = getString(R.string.dot);
        if (signActive) {
            clearText();
            signActive = false;
            setText(getString(R.string.zero) + dot);
        }
        else if (equalState || errorState) {
            init();
            addText(dot);
        }
        else if (!currentResult.contains(dot)) {
            addText(dot);
        }
    }

    public void addSign(String sign) {
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
        activeSign(getString(R.string.equals));
    }

    private void activeSign(String sign) {
        deactivateSigns();
        if(sign.equals(getString(R.string.plus))) {
            plus.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(getString(R.string.minus))){
            minus.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(getString(R.string.multiplication))){
            multiplication.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(getString(R.string.division))){
            division.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
        }
        else if(sign.equals(getString(R.string.equals))){
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
        if (operation.equals(getString(R.string.plus))) {
            x += y;
        } else if (operation.equals(getString(R.string.minus))) {
            x -= y;
        } else if (operation.equals(getString(R.string.multiplication))) {
            x *= y;
        } else if (operation.equals(getString(R.string.division))) {
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

    public String getText() {
        return result.getText().toString();
    }

    public void setText(String text) {
        result.setText(text);
    }

    private void clearText() {
        setText("");
    }

    public void addText(String text) {
        setText(getText() + text);
    }

    public boolean resultEmpty() {
        return getText().equals(getString(R.string.zero));
    }

    public boolean resultEmptyMinus() {
        return getText().equals(getString(R.string.minus) + getString(R.string.zero));
    }

    public boolean resultSign() {
        List<String> a = Arrays.asList(getResources().getStringArray(R.array.signs));
        return a.contains(getText());
    }

}
