package id.alin.espeedboat.MyAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import id.alin.espeedboat.MyRoom.Entity.RewardEntity;
import id.alin.espeedboat.R;

public class MyPointRedeemAdapter extends RecyclerView.Adapter<MyPointRedeemAdapter.ViewHolder> {

    private Context context;
    int total_poin;
    List<RewardEntity> rewardEntityList;
    EditText etNama, etNohp, etAlamat;
    Button btnTukar, btnBatal;
    SharedPreferences sharedPreferences;

    public MyPointRedeemAdapter(List<RewardEntity> rewardEntityList, int total_poin, SharedPreferences sharedPreferences, Context context) {
        this.rewardEntityList = rewardEntityList;
        this.context = context;
        this.total_poin = total_poin;
        this.sharedPreferences = sharedPreferences;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_poin_reward, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(rewardEntityList.get(position).getReward());
        holder.textView1.setText(String.valueOf(rewardEntityList.get(position).getMinimal_point()));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total_poin < rewardEntityList.get(position).getMinimal_point()){
                    Toast.makeText(context, "Poin Tidak Cukup!", Toast.LENGTH_SHORT).show();
                }else{
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_tukar_poin);
                    int width = WindowManager.LayoutParams.MATCH_PARENT;
                    int height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setLayout(width,height);
                    dialog.show();

                    //ET INIT
                    etNama = dialog.findViewById(R.id.etTukarNama);
                    etNohp = dialog.findViewById(R.id.etTukarNohp);
                    etAlamat = dialog.findViewById(R.id.etAlamatTukar);
                    btnTukar = dialog.findViewById(R.id.btnTukarPoin);
                    btnBatal = dialog.findViewById(R.id.btnBatalTukar);

                    //SET DATA
                    etNama.setText(sharedPreferences.getString("USER_NAMA", null));
                    etNohp.setText(sharedPreferences.getString("USER_NOHP", null));
                    etAlamat.setText(sharedPreferences.getString("USER_ALAMAT", null));

                    //BTN CLICK LISTENER
                    btnBatal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    btnTukar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "Ini Fungsi Tukar", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView1;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivBarangReward);
            textView = itemView.findViewById(R.id.tvNamaBarangReward);
            textView1 = itemView.findViewById(R.id.tvTotalPoinReward);
            button = itemView.findViewById(R.id.btnTukarPoin);
        }
    }

}
