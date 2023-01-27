package be.ehb.eindwerk2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.adapters.EventAdapter;
import be.ehb.eindwerk2.databinding.FragmentDetailsBinding;
import be.ehb.eindwerk2.model.Event;

public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;
    Button deleteBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= FragmentDetailsBinding.inflate(inflater,container,false);
        deleteBtn = binding.deletBtn;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        assert getArguments() != null;
        Event event = (Event) getArguments().getSerializable("event");


        String title = event.getTitle();
        String city = event.getCity();
        String description = event.getDescription();
        String timestamp = timestampToString(event.getTimestamp());
        String time = event.getTime();


        binding.titleTextView.setText(title);
        binding.cityTv.setText(city);
        binding.descriptionTv.setText(description);
        binding.timestampTv.setText(timestamp);
        binding.timeTv.setText(time);


        deleteBtn.setOnClickListener((v -> {
            deleteEventFirebase(event);
        }));
    }

    void deleteEventFirebase(Event event){
        String docId = event.getDocId();
        DocumentReference documentReference;
        documentReference = getCollectionReferenceForEvents().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "Event Deleted", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(DetailsFragment.this).navigate(R.id.action_detailsFragment_to_overviewFragment);
                }else{
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    static CollectionReference getCollectionReferenceForEvents(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("events")
                .document(currentUser.getUid()).collection("my_events");
    }

    static String timestampToString(Timestamp ts){
        return new SimpleDateFormat("dd/MM/yyyy").format(ts.toDate());
    }
}
