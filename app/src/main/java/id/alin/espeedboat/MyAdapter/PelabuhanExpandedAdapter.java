package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.R;

import net.cachapa.expandablelayout.ExpandableLayout;

public abstract class PelabuhanExpandedAdapter extends RecyclerView.Adapter<PelabuhanExpandedAdapter.MyViewHolder> {
    private List<PelabuhanEntity> pelabuhanEntities;
    private Context context;
    private RecyclerView recyclerViewcontainer;
    private List<ExpandableLayout> expandableLayouts;
    private DialogFragment fullscreenDialogAsal;

    public PelabuhanExpandedAdapter(List<PelabuhanEntity> pelabuhanEntities, Context context, RecyclerView recyclerViewcontainer, DialogFragment fullscreenDialogAsal) {
        this.pelabuhanEntities = pelabuhanEntities;
        this.context = context;
        this.recyclerViewcontainer = recyclerViewcontainer;
        this.expandableLayouts = new ArrayList<>();
        this.fullscreenDialogAsal = fullscreenDialogAsal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelabuhan_expanded,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvnamapelabuhan.setText(this.pelabuhanEntities.get(position).getNama_pelabuhan());
        holder.tvkodepelabuhan.setText(this.pelabuhanEntities.get(position).getKode_pelabuhan());
        holder.tvalamatkantor.setText(this.pelabuhanEntities.get(position).getAlamat_kantor());
        /*URL GAMBAR PELABUHAN*/
        StringBuilder url = new StringBuilder();
        url.append(ApiClient.BASE_IMAGE_PELABUHAN);

        try{
            url.append(this.pelabuhanEntities.get(position).getFoto());
            Glide.with(context).load(url.toString()).placeholder(R.drawable.no_image_pelabuhan).into(holder.ivpelabuhan);
        }catch (NullPointerException e){
            Glide.with(context).load(R.drawable.no_image_pelabuhan).into(holder.ivpelabuhan);
        }
        expandableLayouts.add(holder.expandableLayout);

        holder.itemroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayouts.forEach(new Consumer<ExpandableLayout>() {
                    @Override
                    public void accept(ExpandableLayout expandableLayout) {
                        expandableLayout.collapse();
                    }
                });

                holder.expandableLayout.toggle();

            }
        });

        /*SET BTN EVENT LISTENER*/
        holder.btnpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectedItemPelabuhan(pelabuhanEntities.get(position));
                fullscreenDialogAsal.dismiss();
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
        private TextView tvnamapelabuhan, tvkodepelabuhan, tvalamatkantor;
        private ImageView ivpelabuhan;
        private Button btnpilih;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.expandableLayout = itemView.findViewById(R.id.expandable_layout);
            this.expandableLayout.setInterpolator(new OvershootInterpolator());
            this.expandableLayout.setOnExpansionUpdateListener(this);
            this.itemroot = itemView.findViewById(R.id.itemPelabuhanRoot);
            this.tvnamapelabuhan = itemView.findViewById(R.id.itemPelabuhanName);
            this.tvkodepelabuhan = itemView.findViewById(R.id.itemPelabuhanKode);
            this.ivpelabuhan = itemView.findViewById(R.id.itempelabuhanimage);
            this.tvalamatkantor = itemView.findViewById(R.id.itemPelabuhanalamat);
            this.btnpilih = itemView.findViewById(R.id.btnpilih);
        }

        @Override
        public void onExpansionUpdate(float expansionFraction, int state) {
            Log.d("ExpandableLayout", "State: " + state);
            if (state == ExpandableLayout.State.EXPANDING) {
                PelabuhanExpandedAdapter.this.recyclerViewcontainer.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }

    public abstract void onSelectedItemPelabuhan(PelabuhanEntity pelabuhanEntity);
}
