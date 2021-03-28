package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MyRoom.Entity.PenumpangDetailEntity;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.R;

public class PenumpangDetailAdapter extends RecyclerView.Adapter<PenumpangDetailAdapter.MyViewHolder> {
    private Context context;
    public List<PenumpangDetailEntity> penumpangDetailEntities;

    public PenumpangDetailAdapter(Context context, List<PenumpangDetailEntity> penumpangDetailEntities) {
        this.context = context;
        this.penumpangDetailEntities = penumpangDetailEntities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penumpang_detail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvNama.setText(penumpangDetailEntities.get(position).getNama_pemegang_tiket());
        holder.tvNama.setError(null);
        holder.tvIdentitas.setText(penumpangDetailEntities.get(position).getId_card() + "  : " + penumpangDetailEntities.get(position).getNo_id_card());
        holder.tvUrutan.setText(position+1 + ". ");
        holder.cardView.setCardBackgroundColor(Color.WHITE);
        Log.d("TEST", "hahahaha");
    }

    @Override
    public int getItemCount() {
        return this.penumpangDetailEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvIdentitas, tvUrutan;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.penumpang);
            tvIdentitas = itemView.findViewById(R.id.tvIdentitas);
            tvUrutan = itemView.findViewById(R.id.tvUrutanPenumpang);
            cardView = itemView.findViewById(R.id.root);

        }
    }
}
