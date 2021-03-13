package xyz.gudkov.android1.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;

import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {
    // Имя настроек
    private static final String NameSharedPreference = "LOGIN";
    // Имя параметра в настройках
    private static final String AppTheme = "APP_THEME";
    private static final int StandartCodeTheme = 0;
    private static final int AppThemeDarkCodeStyle = 1;
    public static final String KEY_NAME = "out.prefix.theme.name";
    private static final int codeStyle = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Устанавливать тему надо только до установки макета активити
        setTheme(getAppTheme(R.style.StandartTheme));
        setContentView(R.layout.activity_settings);
        initThemeChooser();
        MaterialButton buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener((view) -> {

            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                    MODE_PRIVATE);
            intent.putExtra(KEY_NAME, sharedPref.getInt(AppTheme, codeStyle));
            startActivity(intent);
            finish();
        });
    }

    // Инициализация радиокнопок
    private void initThemeChooser() {

        initRadioButton(findViewById(R.id.radioButtonStandartTheme),
                StandartCodeTheme);
        initRadioButton(findViewById(R.id.radioButtonDarkTheme),
                AppThemeDarkCodeStyle);
        RadioGroup rg = findViewById(R.id.radioButtons);
        //((MaterialRadioButton) rg.getChildAt(getCodeStyle(StandartCodeTheme))).setChecked(true);
    }

    // Все инициализации кнопок очень похожи, поэтому создадим метод дляпереиспользования
    private void initRadioButton(View button, int codeStyle) {
        button.setOnClickListener(v -> {
            // сохраним настройки

            setAppTheme(codeStyle);

            // пересоздадим активити, чтобы тема применилась
            recreate();
        });
    }
    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    // Чтение настроек, параметр «тема»
    private int getCodeStyle(int codeStyle) {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    // Сохранение настроек
    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference,
                MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle) {
        switch (codeStyle) {
            case AppThemeDarkCodeStyle:
                return R.style.DarkTheme;
            default:
                return R.style.StandartTheme;
        }
    }
}



