package id.alin.espeedboat.MyAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import id.alin.espeedboat.MainActivity;
import id.alin.espeedboat.MyPointHistoryActivity;
import id.alin.espeedboat.MyPointRewardActivity;
import id.alin.espeedboat.MyRetrofit.ApiClient;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Reward.ServerResponseRiwayatReward;
import id.alin.espeedboat.MyRetrofit.ServiceResponseModels.ServerResponseModels;
import id.alin.espeedboat.MyRetrofit.Services.PembelianServices;
import id.alin.espeedboat.MyRoom.Entity.RiwayatRewardEntity;
import id.alin.espeedboat.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPointHistoryAdapter extends RecyclerView.Adapter<MyPointHistoryAdapter.ViewHolder> {

    private Context context;
    List<RiwayatRewardEntity> riwayat;

    public MyPointHistoryAdapter(List<RiwayatRewardEntity> riwayat, Context context) {
        this.riwayat = riwayat;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_point_history, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try{
            StringBuilder url = new StringBuilder(ApiClient.BASE_IMAGE_USER);
            url.append(riwayat.get(position).getFoto());

            Glide.with(context).load(url.toString())
                    .placeholder(R.drawable.ic_piring)
                    .into(holder.imageView);

        }catch (Exception e){
            Log.d("TAG", e.toString());
        }

        //FORMAT TANGGAL
        String tanggal[] = riwayat.get(position).getCreated_at().split("-", 3);
        String day = tanggal[2];
        String month = tanggal[1];
        String year = tanggal[0];

        if(month.equals("01")){
            month = "Januari";
        }else if(month.equals("02")) {
            month = "Februari";
        }else if(month.equals("03")){
            month = "Maret";
        }else if(month.equals("04")){
            month = "April";
        }else if(month.equals("05")){
            month = "Mei";
        }else if(month.equals("06")){
            month = "Juni";
        }else if(month.equals("07")){
            month = "Juli";
        }else if(month.equals("08")){
            month = "Agustus";
        }else if(month.equals("09")){
            month = "September";
        }else if(month.equals("10")){
            month = "Oktober";
        }else if(month.equals("11")){
            month = "November";
        }else if(month.equals("12")){
            month = "Desember";
        }

        holder.textView.setText(riwayat.get(position).getReward());
        holder.textView1.setText(day + " " + month + " " + year);
        holder.textView2.setText(riwayat.get(position).getNama_kapal());
        if(riwayat.get(position).getStatus().equals("menunggu konfirmasi")) {
            holder.textView3.setText("Dikemas");
            holder.btn.setVisibility(View.GONE);
        }else if(riwayat.get(position).getStatus().equals("dikirim")){
            holder.textView3.setText("Sedang Dikirim");
            holder.btn.setText("Terima Barang");
        }else if(riwayat.get(position).getStatus().equals("selesai")){
            holder.textView3.setText("Selesai");
            holder.btn.setVisibility(View.GONE);
        }
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Ini Cardnya Diklik", Toast.LENGTH_SHORT).show();
//            }
//        });

        //PROGRES BAR INIT
        ProgressDialog dialogLoading;
        dialogLoading = new ProgressDialog(context);
        dialogLoading.setCancelable(false);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Terima Barang")
                        .setMessage("Apakah anda yakin barang telah sampai?")
                        .setCancelable(false)
                        .setAnimation(R.raw.animation_boat_2)
                        .setPositiveButton("Ya", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                dialogLoading.setMessage("Processing");
                                dialogLoading.show();
                                String authorization = MainActivity.mainActivityViewModel.getProfileLiveData().getValue().getToken();
                                PembelianServices pembelianServices = ApiClient.getRetrofit().create(PembelianServices.class);
                                Call<ServerResponseModels> call = pembelianServices.terimaReward(
                                        authorization,
                                        riwayat.get(position).getId()
                                );

                                call.enqueue(new Callback<ServerResponseModels>() {
                                    @Override
                                    public void onResponse(Call<ServerResponseModels> call, Response<ServerResponseModels> response) {
                                        Log.d("STATE",String.valueOf(response.body().getMessage()));
                                        if(response.body().getResponse_code().matches("200") && response.body().getStatus().matches("success")){
                                            MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                                                    .setTitle("Terima Barang")
                                                    .setMessage("Penerimaan Barang Berhasil")
                                                    .setCancelable(false)
                                                    .setAnimation(R.raw.animation_boat_2)
                                                    .setPositiveButton("Ok", new MaterialDialog.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int which) {
                                                            dialogInterface.dismiss();
                                                            Intent intent = new Intent(context, MyPointHistoryActivity.class);
                                                            ((Activity)context).finish();
                                                            context.startActivity(intent);
                                                        }
                                                    })
                                                    .setNegativeButton("Close", new AbstractDialog.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int which) {
                                                            dialogInterface.dismiss();
                                                        }
                                                    })
                                                    .build();

                                            // Show Dialog
                                            mDialog.show();
                                        }else{
                                            dialogLoading.dismiss();
                                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ServerResponseModels> call, Throwable t) {
                                        dialogLoading.dismiss();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Close", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
            }
        });
    }



    @Override
    public int getItemCount() {
        return riwayat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView1, textView2, textView3;
        CardView cardView;
        Button btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivBarangPointHistory);
            textView = itemView.findViewById(R.id.tvNamaBarangPointHistory);
            textView1 = itemView.findViewById(R.id.tvTotalBarangPointHistory);
            textView2 = itemView.findViewById(R.id.tvSpeedboatPointHistory);
            textView3 = itemView.findViewById(R.id.tvStatusPointHistory);
            cardView = itemView.findViewById(R.id.cardViewPointHistory);
            btn = itemView.findViewById(R.id.btnPojokPointHistory);
        }
    }

}

