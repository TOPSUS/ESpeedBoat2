package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.PelabuhanEntity;
import id.alin.espeedboat.R;

public abstract class PelabuhanAdapter extends RecyclerView.Adapter<PelabuhanAdapter.MyHolder> {
    private Context context;
    private List<PelabuhanEntity> pelabuhanEntities;

    public PelabuhanAdapter(Context context, List<PelabuhanEntity> pelabuhanEntities) {
        this.context = context;
        this.pelabuhanEntities = pelabuhanEntities;
    }

    public void updateListPelabuhan(List<PelabuhanEntity> pelabuhanEntities) {
        this.pelabuhanEntities = pelabuhanEntities;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pelabuhan_adapter, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvnamapelabuhan.setText(this.pelabuhanEntities.get(position).getNama_pelabuhan());
        holder.tvcodepelabuhan.setText(this.pelabuhanEntities.get(position).getKode_pelabuhan());
        holder.itemPelabuhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
                getDataFomAdapter(pelabuhanEntities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.pelabuhanEntities.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public CardView itemPelabuhan;
        public TextView tvnamapelabuhan;
        public TextView tvcodepelabuhan;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.itemPelabuhan = itemView.findViewById(R.id.itempelabuhan);
            this.tvnamapelabuhan = itemPelabuhan.findViewById(R.id.itemPelabuhanNama);
            this.tvcodepelabuhan = itemPelabuhan.findViewById(R.id.itemPelabuhanCode);
        }
    }

    public void getDataFomAdapter(PelabuhanEntity pelabuhanEntity) {

    }
}
