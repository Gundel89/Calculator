package com.huneiko.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String expression;
    int sign = 0;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;
    Button btn0;
    Button btnPlus;
    Button btnMinus;
    Button btnDevide;
    Button btnMultiply;
    Button btnSqrt;
    Button btnExponent;
    Button btnPercent;
    Button btnEqual;
    Button btnCancel;
    Button btnDot;
    Button btnBack;
    Button btnChangeSign;
    Button btnReverseNum;
    Button btnPi;

    TextView pane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = "";

        btn0 = (Button)findViewById(R.id.button0);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        btn5 = (Button)findViewById(R.id.button5);
        btn6 = (Button)findViewById(R.id.button6);
        btn7 = (Button)findViewById(R.id.button7);
        btn8 = (Button)findViewById(R.id.button8);
        btn9 = (Button)findViewById(R.id.button9);
        btnPlus = (Button)findViewById(R.id.buttonPlus);
        btnMinus = (Button)findViewById(R.id.buttonMinus);
        btnMultiply = (Button)findViewById(R.id.buttonMultiply);
        btnDevide = (Button)findViewById(R.id.buttonDevide);
        btnSqrt = (Button)findViewById(R.id.buttonSqrt);
        btnExponent = (Button)findViewById(R.id.buttonExponent);
        btnPercent = (Button)findViewById(R.id.buttonPercent);
        btnEqual = (Button)findViewById(R.id.buttonEqual);
        btnDot = (Button)findViewById(R.id.buttonDot);
        btnCancel = (Button)findViewById(R.id.buttonCancel);
        btnBack = (Button)findViewById(R.id.buttonBack);
        btnChangeSign = (Button)findViewById(R.id.buttonChangeSign);
        btnReverseNum = (Button)findViewById(R.id.buttonReverseNum);
        btnPi = (Button)findViewById(R.id.buttonPi);

        pane = (TextView)findViewById(R.id.Pane);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDevide.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnExponent.setOnClickListener(this);
        btnPercent.setOnClickListener(this);
        btnSqrt.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnChangeSign.setOnClickListener(this);
        btnReverseNum.setOnClickListener(this);
        btnPi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Button currentBtn = (Button)findViewById(v.getId());

        if (isNumberButton(currentBtn)) {
            if (currentBtn.getId() == btn0.getId()) {
                if (String.valueOf(pane.getText()) == "0") {
                    return;
                }
                if (sign != 0) {
                    if (expression.length() == expression.indexOf(sign) + 2 && expression.endsWith("0")) {
                        return;
                    }
                }
            }
            addToPane(currentBtn);
        }
        else if (currentBtn.getId() == btnPi.getId()) {
            try {
                addPi(currentBtn);
            }
            catch (ArithmeticException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (isOperatorButton(currentBtn)) {
            try {
                addOperator(currentBtn);
            }
            catch (ArithmeticException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (currentBtn.getId() == btnChangeSign.getId()) {
            try {
                changeSign();
            }
            catch (ArithmeticException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (currentBtn.getId() == btnSqrt.getId() || currentBtn.getId() == btnReverseNum.getId()) {
            try {
                pane.setText(expression = getSqrtOrInverse(currentBtn));
            }
            catch (ArithmeticException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (currentBtn.getId() == btnDot.getId()) {
            if (!expression.isEmpty()) {
                if (sign == 0) {
                    if (expression.contains(".")) {
                        return;
                    }
                } else {
                    if (expression.endsWith(String.valueOf((char) sign)) ||
                            expression.substring(expression.indexOf(sign) + 1).contains(".")) {
                        return;
                    }
                }
                addToPane(currentBtn);
            }
        }
        else if (currentBtn.getId() == btnEqual.getId()) {
            try {
                pane.setText(expression = getResult());
            } catch (ArithmeticException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else if (currentBtn.getId() == btnCancel.getId()) {
            if (!expression.isEmpty()) {
                pane.setText("");
                expression = "";
                sign = 0;
            }
        }
        else if (currentBtn.getId() == btnBack.getId()) {
            if (!expression.isEmpty()) {
                if (expression.endsWith(String.valueOf((char)sign))) {
                    sign = 0;
                }
                expression = expression.substring(0, expression.length() - 1);
                pane.setText(expression);
            }
        }
    }

    private boolean isNumberButton (Button button) {

        int buttonId = button.getId();

        return buttonId == btn0.getId() || buttonId == btn1.getId() ||
                buttonId == btn2.getId() || buttonId == btn3.getId() ||
                buttonId == btn4.getId() || buttonId == btn5.getId() ||
                buttonId == btn6.getId() || buttonId == btn7.getId() ||
                buttonId == btn8.getId() || buttonId == btn9.getId();
    }

    private boolean isExpressionFull () {
        return !expression.isEmpty() &&
                sign != 0 &&
                !expression.endsWith(String.valueOf((char)sign));
    }

    private boolean isOperatorButton (Button button) {

        int buttonId = button.getId();

        return buttonId == btnPlus.getId() || buttonId == btnMinus.getId() ||
                buttonId == btnMultiply.getId() || buttonId == btnDevide.getId() ||
                buttonId == btnExponent.getId() || buttonId == btnPercent.getId();
    }

    private String getSqrtOrInverse (Button currentBtn) throws ArithmeticException {

        if (!expression.isEmpty()) {

            double result = 0;
            boolean isSqrtOrReverse = currentBtn.getId() == R.id.buttonSqrt;

            if (sign != 0 && !expression.endsWith(String.valueOf((char) sign))) {

                int tmpSign = sign;

                result = Double.parseDouble(getResult());

                if (isSqrtOrReverse ? result < 0 : result == 0) {
                    sign = tmpSign;
                    throw new ArithmeticException(currentBtn.getId() == R.id.buttonSqrt ?
                            "Корень из отрицательного числа невозможен!" : "У нуля не может быть обратной величины!");
                }
            }
            else if (sign == 0) {

                if (expression.contains(btnPi.getText())) {
                    result = Math.PI;
                }
                else {
                    result = Double.parseDouble(expression);
                }
                if (isSqrtOrReverse ? result < 0 : result == 0) {
                    throw new ArithmeticException(currentBtn.getId() == R.id.buttonSqrt ?
                            "Корень из отрицательного числа невозможен!" : "У нуля не может быть обратной величины!");
                }
            }
            result = isSqrtOrReverse ? Math.sqrt(result) : 1/result;
            return result - (int)result == 0 ? String.valueOf((int)result) : String.valueOf(result);
        }
        return expression;
    }

    private String getResult () {

        if (!isExpressionFull()) {
            return expression;
        }

        if (expression.contains(btnPi.getText())) {
            expression = expression.replace(btnPi.getText(), String.valueOf(Math.PI));
        }

        double firstOperand;
        double secondOperand;

        if (expression.startsWith("-")) {
            firstOperand = Double.parseDouble(expression.substring(1, expression.lastIndexOf(sign)));
            firstOperand = -firstOperand;
            secondOperand = Double.parseDouble(expression.substring(expression.lastIndexOf(sign) + 1));
        } else {
            firstOperand = Double.parseDouble(expression.substring(0, expression.indexOf(sign)));
            secondOperand = Double.parseDouble(expression.substring(expression.indexOf(sign) + 1));
        }

        double result = 0;

        switch (sign){
            case '+':
                result = firstOperand + secondOperand;
                break;
            case '-':
                result = firstOperand - secondOperand;
                break;
            case '*':
                result = firstOperand*secondOperand;
                break;
            case '/':
                if (secondOperand == 0) {
                    throw new ArithmeticException("Деление на ноль невозможно!");
                }
                result = firstOperand/secondOperand;
                break;
            case '^':
                result = 1;
                for (int i = 0; i < (int)secondOperand; ++i) {
                    result *= firstOperand;
                }
                break;
            case '%':
                if (secondOperand == 0) {
                    throw new ArithmeticException("Процент от нуля невозможен!");
                }
                result = Math.abs(firstOperand/secondOperand*100);
                break;
        }
        sign = 0;
        return result - (int)result == 0 ? String.valueOf((int)result) : String.valueOf(result);
    }

    private void addToPane (Button button) {
        expression += button.getText();
        pane.setText(expression);
        //button.playSoundEffect(R.raw.frogs);
    }

    private void addOperator (Button button) throws ArithmeticException {
        if (!expression.isEmpty()) {
            if (sign != 0) {
                if (expression.endsWith(String.valueOf((char) sign))) {
                    expression = expression.replace((char) sign, button.getText().charAt(0));
                } else {
                    expression = getResult() + button.getText();
                }
            } else {
                expression += button.getText();
            }
            sign = button.getText().charAt(0);
            pane.setText(expression);
        } else {
            if (button.getId() == R.id.buttonMinus) {
                expression += button.getText();
                pane.setText(expression);
            }
        }
    }

    private void addPi (Button button) throws ArithmeticException {
        if (!expression.isEmpty()) {
            if (sign != 0) {
                if (expression.endsWith(String.valueOf((char) sign))) {
                    addToPane(button);
                }
                else {
                    expression = getResult() + "*" + button.getText();
                    sign = '*';
                    pane.setText(expression);
                }
            }
            else if (sign == 0) {
                if (expression.equals("-")) {
                    addToPane(button);
                }
                else {
                    expression += ("*" + button.getText());
                    sign = '*';
                    pane.setText(expression);
                }
            }
        }
        else {
            addToPane(button);
        }
    }

    private void changeSign () throws ArithmeticException {
        if (!expression.isEmpty()) {
            if (sign != 0) {
                expression = getResult();
            }
            if (expression.equals("0")) {
                pane.setText(expression);
            }
            else {
                expression = expression.startsWith("-") ? expression.substring(1) : "-" + expression;
                pane.setText(expression);
            }
        }
    }
}
