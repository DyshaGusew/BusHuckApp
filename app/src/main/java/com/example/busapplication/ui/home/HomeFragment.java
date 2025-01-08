package com.example.busapplication.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.busapplication.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Handler handler;
    private Runnable updateDateTimeRunnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Запускаем обновление времени
        startDateTimeUpdates();

        return root;
    }

    private void startDateTimeUpdates() {
        handler = new Handler();
        updateDateTimeRunnable = new Runnable() {
            @Override
            public void run() {
                // Получение текущего времени и даты
                String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String currentDate = new SimpleDateFormat("dd MMMM", new Locale("ru")).format(new Date());

                // Установка значений в TextView
                if (binding != null) {
                    binding.timeTextView.setText(currentTime);
                    binding.dateTextView.setText(currentDate);
                }

                // Запускаем обновление снова через 1 минуту
                handler.postDelayed(this, 60000);
            }
        };

        // Первый запуск обновления
        handler.post(updateDateTimeRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Удаляем все callbacks, чтобы избежать утечек памяти
        if (handler != null && updateDateTimeRunnable != null) {
            handler.removeCallbacks(updateDateTimeRunnable);
        }

        binding = null;
    }
}
