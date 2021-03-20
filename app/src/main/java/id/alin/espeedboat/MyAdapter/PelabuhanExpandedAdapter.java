package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.R;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.w3c.dom.Text;

public class PelabuhanExpandedAdapter extends RecyclerView.Adapter<PelabuhanExpandedAdapter.MyViewHolder> {
    private List<PelabuhanEntity> pelabuhanEntities;
    private Context context;
    private RecyclerView recyclerViewcontainer;

    public PelabuhanExpandedAdapter(List<PelabuhanEntity> pelabuhanEntities, Context context, RecyclerView recyclerViewcontainer) {
        this.pelabuhanEntities = pelabuhanEntities;
        this.context = context;
        this.recyclerViewcontainer = recyclerViewcontainer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelabuhan_expanded,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("LOOPING",String.valueOf(position));
        holder.tvnamapelabuhan.setText(this.pelabuhanEntities.get(position).getNama_pelabuhan());
        holder.tvkodepelabuhan.setText(this.pelabuhanEntities.get(position).getKode_pelabuhan());
        holder.itemroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.expandableLayout.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pelabuhanEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements ExpandableLayout.OnExpansionUpdateListener {
        private CardView itemroot;
        private ExpandableLayout expandableLayout;
        private TextView tvnamapelabuhan, tvkodepelabuhan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.expandableLayout = itemView.findViewById(R.id.expandable_layout);
            this.expandableLayout.setInterpolator(new OvershootInterpolator());
            this.expandableLayout.setOnExpansionUpdateListener(this);
            this.itemroot = itemView.findViewById(R.id.itemPelabuhanRoot);
            this.tvnamapelabuhan = itemView.findViewById(R.id.itemPelabuhanName);
            this.tvkodepelabuhan = itemView.findViewById(R.id.itemPelabuhanKode);
        }

        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            Log.d("ExpandableLayout", "State: " + state);
            if (state == ExpandableLayout.State.EXPANDING) {
                PelabuhanExpandedAdapter.this.recyclerViewcontainer.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }
}
