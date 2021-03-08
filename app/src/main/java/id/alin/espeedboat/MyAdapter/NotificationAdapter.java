package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.R;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private Context context;
    ArrayList<String> notifikasi, waktu;

    public NotificationAdapter(ArrayList<String> notifikasi, ArrayList<String> waktu, Context context) {
        this.notifikasi = notifikasi;
        this.waktu = waktu;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(notifikasi.get(position));
        holder.textView1.setText(waktu.get(position));
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ini Notifikasi", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {

        return notifikasi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView1;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iconNotification);
            textView = itemView.findViewById(R.id.namaNotifikasi);
            textView1 = itemView.findViewById(R.id.waktuNotifikasi);
            constraintLayout = itemView.findViewById(R.id.conslayuser);
        }
    }

}
