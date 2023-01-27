package be.ehb.eindwerk2.fragments;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
    Button overviewBtn;
    Switch switchBtn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= EventsOverviewBinding.inflate(inflater,container,false);
        rv = binding.recyclerView;
        overviewBtn = binding.overviewBtn;
        switchBtn = binding.toggle;
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
        PopupMenu popupMenu = new PopupMenu(getActivity(),overviewBtn);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="Logout"){
                    FirebaseAuth.getInstance().signOut();
                    NavHostFragment.findNavController(OverviewFragment.this).navigate(R.id.action_overviewFragment_to_loginFragment);
                    return true;
                }return  false;
            }
        });


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
            //toggle(this.getView());



        }


    /*public void toggle(View view){
        switchBtn.setOnClickListener(v -> {
            if (switchBtn.isChecked()){
                switchBtn.setChecked(true);
                setupRecyclerView();


            }else{
                switchBtn.setChecked(false);
                view.refreshDrawableState();

            }
        });
    }*/





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
