package id.alin.espeedboat.MyAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRoom.Entity.BeritaEspeedEntity;
import id.alin.espeedboat.R;
import id.alin.espeedboat.WebviewActivity;

public class BeritaEspeedAdapter extends RecyclerView.Adapter<BeritaEspeedAdapter.ViewHolder> {

    private Context context;
    public List<BeritaEspeedEntity> beritaEspeedEntities;

    public BeritaEspeedAdapter(List<BeritaEspeedEntity> beritaEspeedEntities, Context context) {
        this.context = context;
        this.beritaEspeedEntities = beritaEspeedEntities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daftar_pelabuhan_item,parent,false);

        return new ViewHolder(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvBerita.setText(this.beritaEspeedEntities.get(position).getBerita());
        holder.tvJudul.setText(this.beritaEspeedEntities.get(position).getJudul());
        holder.cardroot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebviewActivity.class);
                String link_berita = ApiClient.BASE_BERITA_KAPAL+beritaEspeedEntities.get(position).id;
                Log.d("testtest",link_berita);
                intent.putExtra(WebviewActivity.LINK,link_berita);
                context.startActivity(intent);
            }
        });
        try{
            if(this.beritaEspeedEntities.get(position) == null){
                Glide.with(BeritaEspeedAdapter.this.context).load(R.drawable.berita).into(holder.ivBerita);
            }
            else{
                StringBuilder url = new StringBuilder();
                url.append(ApiClient.BASE_IMAGE_BERITA_ESPEED);
                url.append(this.beritaEspeedEntities.get(position).getFoto());

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.berita);

                Glide.with(BeritaEspeedAdapter.this.context).load(url.toString())
                        .apply(requestOptions)
                        .into(holder.ivBerita);
            }
        }catch (Exception ignored){

        }


    }

    @Override
    public int getItemCount() {
        return this.beritaEspeedEntities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvJudul, tvBerita;
        public ImageView ivBerita;
        public CardView cardroot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvJudul = itemView.findViewById(R.id.tvItemBeritaPelabuhanTitle);
            this.tvBerita = itemView.findViewById(R.id.tvItemBeritaPelabuhanDetail);
            this.ivBerita = itemView.findViewById(R.id.ivItemBeritaPelabuhan);
            this.cardroot = itemView.findViewById(R.id.beritaroot);
        }

    }

}
