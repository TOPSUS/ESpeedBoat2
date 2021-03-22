package id.alin.espeedboat.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyPointRewardActivity;
import id.alin.espeedboat.R;

public class MyPointAdapter extends RecyclerView.Adapter<MyPointAdapter.ViewHolder> {

    private Context context;
    ArrayList<String> speedboat, poin;

    public MyPointAdapter(ArrayList<String> speedboat, ArrayList<String> poin, Context context) {
        this.speedboat = speedboat;
        this.poin = poin;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_my_point, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(speedboat.get(position));
        holder.textView1.setText(poin.get(position) + " Poin");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyPointRewardActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return speedboat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView, textView1;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivItemBeritaPelabuhan);
            textView = itemView.findViewById(R.id.tvnamaSpeedboatPoint);
            textView1 = itemView.findViewById(R.id.tvtotalSpeedboatPoin);
            cardView = itemView.findViewById(R.id.cardViewPoint);
        }
    }

}