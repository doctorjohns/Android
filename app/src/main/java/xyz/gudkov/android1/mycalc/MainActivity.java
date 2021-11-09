package xyz.gudkov.android1.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    private TextView mTextViewMain;
    private TextView mTextViewResult;
    private Button mButton0;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private Button mButton6;
    private Button mButton7;
    private Button mButton8;
    private Button mButton9;
    private Button mButtonCE;
    private Button mButtonPercent;
    private Button mButtonSign;
    private Button mButtonPlus;
    private Button mButtonMinus;
    private Button mButtonMultiply;
    private Button mButtonDivide;
    private Button mButtonPoint;
    private Button mButtonEqually;

    private String mResult = "0";
    private String mResultText = "0";
    private String mResultOperation = " ";
    private String mDigit;
    private String mLastDigit;
    private String[] mData;
    private DecimalFormat decimalFormat = new DecimalFormat("#.##########");
    private Boolean checkAddZero = false;
    private Boolean checkFirstOperation = true;
    private Boolean checkComputeComplete = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        intent.getIntExtra(Settings.KEY_THEME, 0);
        int codeStyle = intent.getIntExtra(Settings.KEY_THEME, 0);

        if (codeStyle == 0) {
            setTheme((R.style.StandartTheme));

        } else {
            setTheme((R.style.DarkTheme));

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewMain = findViewById(R.id.textViewMain);
        mTextViewResult = findViewById(R.id.textViewResult);
        mButton0 = findViewById(R.id.button0);
        mButton1 = findViewById(R.id.button1);
        mButton2 = findViewById(R.id.button2);
        mButton3 = findViewById(R.id.button3);
        mButton4 = findViewById(R.id.button4);
        mButton5 = findViewById(R.id.button5);
        mButton6 = findViewById(R.id.button6);
        mButton7 = findViewById(R.id.button7);
        mButton8 = findViewById(R.id.button8);
        mButton9 = findViewById(R.id.button9);
        mButtonCE = findViewById(R.id.buttonCE);
        mButtonPercent = findViewById(R.id.buttonPercent);
        mButtonSign = findViewById(R.id.buttonSign);
        mButtonPlus = findViewById(R.id.buttonPlus);
        mButtonMinus = findViewById(R.id.buttonMinus);
        mButtonMultiply = findViewById(R.id.buttonMultiply);
        mButtonDivide = findViewById(R.id.buttonDivide);
        mButtonPoint = findViewById(R.id.buttonPoint);
        mButtonPercent = findViewById(R.id.buttonPercent);
        mButtonEqually = findViewById(R.id.buttonEqually);

        mButtonCE.setOnClickListener((view -> {
            resetData();
        }));

        mButton0.setOnClickListener((view -> {
            mDigit = "0";
            addZero(mDigit);
        }));
        mButton1.setOnClickListener((view -> {
            mDigit = "1";
            addNumber(mDigit);
        }));
        mButton2.setOnClickListener((view -> {
            mDigit = "2";
            addNumber(mDigit);
        }));
        mButton3.setOnClickListener((view -> {
            mDigit = "3";
            addNumber(mDigit);
        }));
        mButton4.setOnClickListener((view -> {
            mDigit = "4";
            addNumber(mDigit);
        }));
        mButton5.setOnClickListener((view -> {
            mDigit = "5";
            addNumber(mDigit);
        }));
        mButton6.setOnClickListener((view -> {
            mDigit = "6";
            addNumber(mDigit);
        }));
        mButton7.setOnClickListener((view -> {
            mDigit = "7";
            addNumber(mDigit);
        }));
        mButton8.setOnClickListener((view -> {
            mDigit = "8";
            addNumber(mDigit);
        }));
        mButton9.setOnClickListener((view -> {
            mDigit = "9";
            addNumber(mDigit);
        }));
        mButtonMinus.setOnClickListener((view -> {
            mDigit = "-";
            addOperation(mDigit);
        }));
        mButtonPlus.setOnClickListener((view -> {
            mDigit = "+";
            addOperation(mDigit);
        }));
        mButtonMultiply.setOnClickListener((view -> {
            mDigit = "*";
            addOperation(mDigit);
        }));
        mButtonDivide.setOnClickListener((view -> {
            mDigit = "/";
            addOperation(mDigit);
        }));
        mButtonEqually.setOnClickListener((view -> {
            getResult();
        }));
        MaterialButton settingsButton = findViewById(R.id.button_settings);
        settingsButton.setOnClickListener((view) -> {
            Intent intent2 = new Intent(this, Settings.class);
            startActivity(intent2);
        });
    }

    private void resetData() {
        mResult = "0";
        checkAddZero = false;
        mTextViewMain.setText("0");
        mTextViewResult.setText(" ");
        mResultOperation = " ";
    }

    private void addNumber(String mDigit) {

        if (mResult.equals("0") || checkComputeComplete) {
            mResult = mDigit;
            checkComputeComplete = false;
            checkFirstOperation = true;
        } else {
            mResult = mResult + mDigit;
        }
        checkAddZero = true;
        printToTextView(mResult);
    }

    private void addZero(String mDigit) {
        if (checkAddZero) {
            mResult = mResult + mDigit;
            printToTextView(mResult);
        }
    }

    private void addOperation(String mDigit) {
        if (checkFirstOperation && !(mResult.equals("0"))) {
            mResult = mResult + " " + mDigit + " ";
            mLastDigit = mDigit;
            checkFirstOperation = false;
            printToTextView(mResult);
        } else {
            if (!(mResult.endsWith(mDigit)) && !(mResult.equals("0")) && mDigit.equals(mLastDigit)) {
                getResult();
            } else {
                mResult = mResultText + " " + mDigit + " ";
                checkComputeComplete = false;
                printToTextView(mResult);
            }
        }
    }

    private void printToTextView(String mResultText) {
        if (mResultText.length() < 21) {
            mTextViewMain.setText(mResultText);
            mTextViewResult.setText(mResultOperation);
        } else
            mTextViewMain.setText("Error");
    }

    private void getResult() {
        mData = mResult.split(" ");
        if (mData.length == 3) {
            Double mNumberOne = Double.parseDouble(mData[0]);
            String operator = mData[1];
            Double mNumberTwo = Double.parseDouble(mData[2]);
            switch (operator) {
                case "+":
                    mResult = decimalFormat.format(mNumberOne + mNumberTwo);
                    break;
                case "-":
                    mResult = decimalFormat.format(mNumberOne - mNumberTwo);
                    break;
                case "*":
                    mResult = decimalFormat.format(mNumberOne * mNumberTwo);
                    break;
                case "/":
                    mResult = decimalFormat.format(mNumberOne / mNumberTwo);
                    break;
                default:
                    break;
            }
            mResultText = mResult;
            mResultOperation = mNumberOne + " " + operator + " " + mNumberTwo + " " + "=" + mResult;
            printToTextView(mResultText);
            mResult = mResult + " " + operator + " " + mNumberTwo;
            checkComputeComplete = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("save_mResult", mResult);
        state.putString("save_mResultOperation", mResultOperation);
        state.putString("save_mResultText", mResultText);
        state.putString("save_mDigit", mDigit);
        state.putString("save_mLastDigit", mLastDigit);
        state.putStringArray("save_mData", mData);
        state.putBoolean("save_checkAddZero", checkAddZero);
        state.putBoolean("save_checkFirstOperation", checkFirstOperation);
        state.putBoolean("save_checkComputeComplete", checkComputeComplete);
        state.putString("save_mTextView", (String) mTextViewMain.getText());
        state.putString("save_mTextViewResult", (String) mTextViewMain.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        mResult = state.getString("save_mResult");
        mResultOperation = state.getString("save_mResultOperation");
        mResultText = state.getString("save_mResultText");
        mDigit = state.getString("save_mDigit");
        mLastDigit = state.getString("save_mLastDigit");
        mData = state.getStringArray("save_mData");
        checkAddZero = state.getBoolean("save_checkAddZero");
        checkFirstOperation = state.getBoolean("save_checkFirstOperation");
        checkComputeComplete = state.getBoolean("save_checkComputeComplete");
        mTextViewMain.setText(state.getString("save_mTextView"));
        mTextViewResult.setText(state.getString("save_mTextViewResult"));

    }
}