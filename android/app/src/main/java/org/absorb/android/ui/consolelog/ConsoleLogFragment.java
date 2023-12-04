package org.absorb.android.ui.consolelog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import org.absorb.android.databinding.FragmentConsoleLogBinding;
import org.absorb.android.launcher.AbsorbAndroidLauncher;

import java.io.IOException;
import java.io.PrintStream;

public class ConsoleLogFragment extends Fragment {

    private FragmentConsoleLogBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ConsoleLogModel dashboardViewModel =
                new ViewModelProvider(this).get(ConsoleLogModel.class);

        binding = FragmentConsoleLogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.startServerButton.setOnClickListener(view -> {
            showStartServerButton(false);
            
            System.setOut(new PrintStream(new ConsoleLogOutputStream(binding.consoleInput), true));
            System.setOut(new PrintStream(new ConsoleLogOutputStream(binding.consoleInput), true));
            try {
                //AbsrobServer.start();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        /*final TextView textView = binding.;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    public void showStartServerButton(boolean show){
        binding.startServerButton.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}