package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.alin.espeedboat.MyAdapter.PenumpangAdapter;

public class InputIdentitasPemesanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PenumpangAdapter penumpangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_identitas_pemesan);
        this.recyclerView = findViewById(R.id.rvInputIdentitas);
        this.layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        this.recyclerView.setLayoutManager(this.layoutManager);
        List<String> pemesan = new ArrayList<>();
        pemesan.add("ALIN 1");
        pemesan.add("ALIN 2");

        this.penumpangAdapter = new PenumpangAdapter(this,pemesan);
        this.recyclerView.setAdapter(this.penumpangAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);

    }
}