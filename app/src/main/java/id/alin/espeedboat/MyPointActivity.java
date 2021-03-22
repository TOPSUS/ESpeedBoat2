package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointAdapter;

import android.os.Bundle;

import java.util.ArrayList;

public class MyPointActivity extends AppCompatActivity {

    MyPointAdapter myPointAdapter;
    RecyclerView recyclerView;
    ArrayList<String> speedboat;
    ArrayList<String> point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point);
        init();
    }

    private void init(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMyPoint);

        speedboat = new ArrayList<>();
        speedboat.add("CASPLA");
        speedboat.add("FBI 1 LALA");
        speedboat.add("CASPLA");
        speedboat.add("SPEEDBOATBOS");
        speedboat.add("CASPLA");

        point =  new ArrayList<>();
        point.add("50");
        point.add("100");
        point.add("69");
        point.add("33");
        point.add("2");

        myPointAdapter = new MyPointAdapter(speedboat, point, MyPointActivity.this);
        recyclerView.setAdapter(myPointAdapter);
    }

}