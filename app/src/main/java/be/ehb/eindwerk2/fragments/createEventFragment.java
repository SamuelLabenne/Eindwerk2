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
import be.ehb.eindwerk2.databinding.FragmentCreateEventBinding;

public class createEventFragment extends Fragment {
    private FragmentCreateEventBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= FragmentCreateEventBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

   @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.saveEvent.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(createEventFragment.this).navigate(R.id.action_createEventFragment_to_overviewFragment);
        });
    }

    void saveEvent(){
        String title = binding.eventTitle.getText().toString();
        String description = binding.descriptionET.getText().toString();
        String time = binding.editText.getText().toString();
        String city = binding.editText2.getText().toString();
        if (title==null || title.isEmpty()){
            binding.eventTitle.setError("Title required");
            return;
        }

    }
}
