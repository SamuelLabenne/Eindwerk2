package be.ehb.eindwerk2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.model.Event;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventViewHolder> {
    Context context;

    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event e) {
        holder.titleTV.setText(e.getTitle());
        holder.cityTV.setText(e.getCity());
        holder.timeStampTV.setText(e.getTimestamp().toString());
        holder.dateTV.setText(e.getTime());


    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        return new EventViewHolder(view);
    }

    class EventViewHolder extends RecyclerView.ViewHolder{
        TextView titleTV, timeStampTV, dateTV, cityTV;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV =itemView.findViewById(R.id.row_title);
            cityTV =itemView.findViewById(R.id.row_city);
            timeStampTV = itemView.findViewById(R.id.row_timestamp);
            dateTV = itemView.findViewById(R.id.row_date);

        }
    }
}
