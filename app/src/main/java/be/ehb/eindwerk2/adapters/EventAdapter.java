package be.ehb.eindwerk2.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.fragments.DetailsFragment;
import be.ehb.eindwerk2.fragments.OverviewFragment;
import be.ehb.eindwerk2.fragments.createEventFragment;
import be.ehb.eindwerk2.model.Event;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventViewHolder> implements Filterable {
    Context context;
    public List<Event> items;
    public List<Event> allItems;


    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options, Context context, List<Event> items) {
        super(options);
        this.context = context;
        this.items = items;
        allItems = new ArrayList<>(items);


    }

    @Override
    protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event e) {
        holder.titleTV.setText(e.getTitle());
        holder.cityTV.setText(e.getCity());
        holder.timeStampTV.setText(timestampToString(e.getTimestamp()));
        holder.dateTV.setText(e.getTime());



        holder.itemView.setOnClickListener(view -> {
            Bundle dataToPass = new Bundle();
            String docId = this.getSnapshots().getSnapshot(position).getId();
            e.setDocId(docId);
            dataToPass.putSerializable("event", e);
            Navigation.findNavController(holder.itemView).navigate(R.id.action_overviewFragment_to_detailsFragment, dataToPass);

        });
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        return new EventViewHolder(view);
    }




    class EventViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV, timeStampTV, dateTV, cityTV;
        ConstraintLayout eventItem;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.row_title);
            cityTV = itemView.findViewById(R.id.row_city);
            timeStampTV = itemView.findViewById(R.id.row_timestamp);
            dateTV = itemView.findViewById(R.id.row_date);
            eventItem = itemView.findViewById(R.id.row_event_item);

        }
    }

    static String timestampToString(Timestamp ts){
       return new SimpleDateFormat("dd/MM/yyyy").format(ts.toDate());
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Event> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(allItems);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Event event : allItems) {
                    if ((event.getTitle().toLowerCase().contains(filterPattern))) {
                        filteredList.add(event);
                    }
                }
            } FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items.clear();
            items.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


}
