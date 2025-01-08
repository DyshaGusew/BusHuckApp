package com.example.busapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.busapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TextView minutesTextView, secondsTextView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализация TextView для минут и секунд
        minutesTextView = findViewById(R.id.minutesTextView);
        secondsTextView = findViewById(R.id.secondsTextView);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setItemBackgroundResource(R.color.black);
        // Настройка для навигации
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Генерация случайного времени от 20 до 30 минутd
        Random random = new Random();
        int totalTimeInMillis = 45 * 60 * 1000;  // Переводим минуты в миллисекунды

        // Запуск таймера
        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Вычисляем оставшиеся минуты и секунды
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;

                // Обновляем текстовые поля с оставшимися минутами и секундами
                minutesTextView.setText(String.format("%02d", minutes));
                secondsTextView.setText(String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                // Таймер завершен, например, можно сделать что-то по завершению
                minutesTextView.setText("00");
                secondsTextView.setText("00");
            }
        };

        // Запуск таймера
        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Остановить таймер, если активити уничтожена
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
