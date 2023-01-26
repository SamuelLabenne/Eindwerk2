package be.ehb.eindwerk2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.databinding.EventsOverviewBinding;

public class OverviewFragment extends Fragment {
    private EventsOverviewBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= EventsOverviewBinding.inflate(inflater,container,false);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addEventBtn.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(OverviewFragment.this).navigate(R.id.action_overviewFragment_to_createEventFragment);
        });
    }
}
