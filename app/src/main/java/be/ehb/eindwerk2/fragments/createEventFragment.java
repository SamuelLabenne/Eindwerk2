package be.ehb.eindwerk2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.databinding.EventsOverviewBinding;
import be.ehb.eindwerk2.databinding.FragmentCreateEventBinding;
import be.ehb.eindwerk2.model.Event;

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

       binding.saveEvent.setOnClickListener((View v) -> saveEvent());
    }

    void saveEvent(){
        String title = binding.eventEt.getText().toString();
        String description = binding.descriptionET.getText().toString();
        String date = binding.dateET.getText().toString();
        String city = binding.cityET.getText().toString();
        if (title.isEmpty()){
            binding.eventEt.setError("Title required");
        }else{
            Event e1 = new Event();
            e1.setTitle(title);
            e1.setDescription(description);
            e1.setCity(city);
            e1.setTime(date);
            e1.setTimestamp(Timestamp.now());
            saveEventFirebase(e1);
        }

    }

    static CollectionReference getCollectionReferenceForEvents(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("events")
                .document(currentUser.getUid()).collection("my_events");
    }

    void saveEventFirebase(Event e1){
        DocumentReference documentReference;
        documentReference = getCollectionReferenceForEvents().document();
        documentReference.set(e1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Event Created", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(createEventFragment.this).navigate(R.id.action_createEventFragment_to_overviewFragment);
                }else{
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



}
