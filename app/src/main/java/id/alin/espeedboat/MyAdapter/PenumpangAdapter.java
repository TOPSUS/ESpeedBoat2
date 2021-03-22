package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.alin.espeedboat.InputIdentitasPemesanActivity;
import id.alin.espeedboat.MyViewModel.InputIdentitasPemesanAcitivyViewModel.ObjectData.PenumpangData;
import id.alin.espeedboat.R;

public class PenumpangAdapter extends RecyclerView.Adapter<PenumpangAdapter.MyViewHolder> {
    private Context context;
    public List<PenumpangData> penumpangData;

    public PenumpangAdapter(Context context, List<PenumpangData> PenumpangData) {
        this.context = context;
        this.penumpangData = PenumpangData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penumpang,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(penumpangData.size() > 0){
            if(penumpangData.get(position).getNama_pemegang_ticket().matches("")){
                String nama = "isi data penumpang ke - " + (position + 1);
                holder.tvNama.setText(nama);
                holder.tvNama.setError("");
            }else{
                holder.tvNama.setText(penumpangData.get(position).getNama_pemegang_ticket());
                holder.tvNama.setError(null);
                holder.cardView.setCardBackgroundColor(Color.WHITE);
            }
        }

        holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((InputIdentitasPemesanActivity)context).showBottomSheet(penumpangData.get(position).getNama_pemegang_ticket(),penumpangData.get(position).getNo_id_card(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.penumpangData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama;
        private CardView cardView;
        private ImageButton editbutton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.penumpang);
            cardView = itemView.findViewById(R.id.root);
            editbutton = itemView.findViewById(R.id.editbutton);
        }
    }
}
