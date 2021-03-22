package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointHistoryAdapter;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MyPointHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyPointHistoryAdapter myPointHistoryAdapter;
    ArrayList<String> speedboat, barang, jumlah, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point_history);

        init();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMyPointHistory);

        speedboat = new ArrayList<>();
        speedboat.add("CASPLA");
        speedboat.add("FBI.1.LALALA");
        speedboat.add("CASPLA");
        speedboat.add("FBI.1.LALALA");
        speedboat.add("CASPLA");
        speedboat.add("FBI.1.LALALA");

        barang = new ArrayList<>();
        barang.add("Piring Cantik");
        barang.add("Piring Cantik");
        barang.add("Piring Cantik");
        barang.add("Piring Cantik");
        barang.add("Piring Cantik");
        barang.add("Piring Cantik");

        jumlah = new ArrayList<>();
        jumlah.add("5");
        jumlah.add("5");
        jumlah.add("5");
        jumlah.add("5");
        jumlah.add("5");
        jumlah.add("5");

        status = new ArrayList<>();
        status.add("Selesai");
        status.add("Sedang Dikirim");
        status.add("Selesai");
        status.add("Sedang Dikirim");
        status.add("Selesai");
        status.add("Selesai");

        myPointHistoryAdapter = new MyPointHistoryAdapter(speedboat, status, barang, jumlah, MyPointHistoryActivity.this);
        recyclerView.setAdapter(myPointHistoryAdapter);
    }
}