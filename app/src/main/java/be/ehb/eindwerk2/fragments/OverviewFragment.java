package be.ehb.eindwerk2.fragments;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.adapters.EventAdapter;
import be.ehb.eindwerk2.databinding.EventsOverviewBinding;
import be.ehb.eindwerk2.model.Event;

public class OverviewFragment extends Fragment {
    private EventsOverviewBinding binding;
    RecyclerView rv;
    EventAdapter ea;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= EventsOverviewBinding.inflate(inflater,container,false);
        rv = binding.recyclerView;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addEventBtn.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(OverviewFragment.this).navigate(R.id.action_overviewFragment_to_createEventFragment);
        });

        binding.overviewBtn.setOnClickListener((v -> showMenu()));

        setupRecyclerView();
    }

    void showMenu(){
        //TODO fix menu when click

    }
    static CollectionReference getCollectionReferenceForEvents(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return FirebaseFirestore.getInstance().collection("events")
                .document(currentUser.getUid()).collection("my_events");
    }

    void setupRecyclerView(){

        Query query = getCollectionReferenceForEvents().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                .setQuery(query,Event.class).build();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ea = new EventAdapter(options,getActivity());
        rv.setAdapter(ea);

    }

    @Override
    public void onStart() {
        super.onStart();
        ea.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        ea.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        ea.notifyDataSetChanged();
    }
}
