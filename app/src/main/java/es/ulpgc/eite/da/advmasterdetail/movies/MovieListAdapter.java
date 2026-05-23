package es.ulpgc.eite.da.advmasterdetail.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.MovieEntity;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<MovieEntity> itemList;
    private final View.OnClickListener clickListener;

    public MovieListAdapter(View.OnClickListener listener) {
        itemList = new ArrayList<>();
        clickListener = listener;
    }

    public void setItems(List<MovieEntity> items) {
        itemList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setTag(itemList.get(position));
        holder.itemView.setOnClickListener(clickListener);
        holder.titleView.setText(itemList.get(position).title);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.movie_title);
        }
    }
}
