package be.ehb.eindwerk2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.adapters.EventAdapter;
import be.ehb.eindwerk2.databinding.EventsOverviewBinding;
import be.ehb.eindwerk2.model.Event;

public class OverviewFragment extends Fragment {
    private EventsOverviewBinding binding;
    Menu menu;
    RecyclerView rv;
    EventAdapter ea;
    Button overviewBtn;
    //Switch switchBtn;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding= EventsOverviewBinding.inflate(inflater,container,false);
        rv = binding.recyclerView;
        overviewBtn = binding.overviewBtn;
        //switchBtn = binding.toggle;


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addEventBtn.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(OverviewFragment.this).navigate(R.id.action_overviewFragment_to_createEventFragment);

        });

        binding.overviewBtn.setOnClickListener((v -> showMenu()));

        setupMenu();


        setupRecyclerView();
    }

    private void setupMenu() {
        ((MenuHost) requireActivity()).addMenuProvider(new MenuProvider() {
            @Override
            public void onPrepareMenu(Menu menu) {
                // Handle for example visibility of menu items
            }

            @Override
            public void onCreateMenu(Menu menu, MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.event_menu, menu);
                MenuItem searchItem = menu.findItem(R.id.action_search);
                SearchView searchView = (SearchView) searchItem.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        ea.getFilter().filter(newText);
                        return false;
                    }
            });

            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                // Validate and handle the selected menu item
                return true;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
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
        List<Event> items = new ArrayList<>();
        Query query = getCollectionReferenceForEvents().orderBy("timestamp",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Event> options = new FirestoreRecyclerOptions.Builder<Event>()
                    .setQuery(query,Event.class).build();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ea = new EventAdapter(options,getActivity(),items);
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
