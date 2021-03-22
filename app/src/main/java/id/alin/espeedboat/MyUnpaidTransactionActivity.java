package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyUnpaidTransactionAdapter;

import android.os.Bundle;

import java.util.ArrayList;

public class MyUnpaidTransactionActivity extends AppCompatActivity {

    MyUnpaidTransactionAdapter myUnpaidTransactionAdapter;
    RecyclerView recyclerView;
    ArrayList<String> tanggal, harga, asal, tujuan, waktuasal, waktutujuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_unpaid_transaction);
        init();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewUnpaidTransaction);
        tanggal = new ArrayList<>();
        tanggal.add("Rabu, 13 Maret 2021");
        tanggal.add("Rabu, 13 Maret 2021");
        tanggal.add("Rabu, 13 Maret 2021");

        harga = new ArrayList<>();
        harga.add("Rp. 50.000");
        harga.add("Rp. 50.000");
        harga.add("Rp. 50.000");

        asal = new ArrayList<>();
        asal.add("Sanur");
        asal.add("Sanur");
        asal.add("Sanur");

        tujuan = new ArrayList<>();
        tujuan.add("Benoa");
        tujuan.add("Benoa");
        tujuan.add("Benoa");

        waktuasal = new ArrayList<>();
        waktuasal.add("13:30");
        waktuasal.add("13:30");
        waktuasal.add("13:30");

        waktutujuan = new ArrayList<>();
        waktutujuan.add("14.20");
        waktutujuan.add("14.20");
        waktutujuan.add("14.20");

        myUnpaidTransactionAdapter = new MyUnpaidTransactionAdapter(tanggal, harga, asal, tujuan, waktuasal, waktutujuan, MyUnpaidTransactionActivity.this);
        recyclerView.setAdapter(myUnpaidTransactionAdapter);
    }
}