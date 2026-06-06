package es.ulpgc.eite.da.advmasterdetail.series;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.SerieEntity;

public class SerieListAdapter extends RecyclerView.Adapter<SerieListAdapter.ViewHolder> {

    private List<SerieEntity> itemList;
    private final View.OnClickListener clickListener;

    public SerieListAdapter(View.OnClickListener listener) {
        itemList = new ArrayList<>();
        clickListener = listener;
    }

    public void setItems(List<SerieEntity> items) {
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
                .inflate(R.layout.item_serie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        SerieEntity item = itemList.get(position);
        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(clickListener);
        holder.titleView.setText(item.title);

        Glide.with(holder.itemView.getContext())
                .load(item.imageUrl)
                .into(holder.imageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView titleView;
        final ImageView imageView;

        ViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.serie_title);
            imageView = view.findViewById(R.id.serie_image);
        }
    }
}
