package android.test.com.trips;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.test.com.trips.model.Trip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Trip> responses;

    Context ctx;

    public RecyclerAdapter(Context context, List<Trip> responses) {
        ctx = context;
        this.responses = responses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Trip trip = responses.get(position);


        holder.title.setText(trip.getTitle());

        holder.dest.setText(trip.getDestination());
        holder.date.setText(trip.getDate());

        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(ctx, TripActivity.class);
                i.putExtra("key", "FROM_LIST");
                i.putExtra("keyId", trip.getId());

                ctx.startActivity(i);


            }
        });

    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        protected TextView title, date, dest;

        RelativeLayout main;


        public MyViewHolder(View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.title);

            date = itemView.findViewById(R.id.date);
            dest = itemView.findViewById(R.id.destination);


            main = itemView.findViewById(R.id.main);


        }
    }
}
