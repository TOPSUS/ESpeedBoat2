package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointAdapter;
import id.alin.espeedboat.MyAdapter.MyPointRedeemAdapter;
import id.alin.espeedboat.MySharedPref.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class MyPointRewardActivity extends AppCompatActivity {

    MyPointRedeemAdapter myPointRedeemAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<String> speedboat;
    ArrayList<String> point;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_point_reward);
        init();
    }

    private void init(){
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewMyPointRedeem);

        speedboat = new ArrayList<>();
        speedboat.add("Piring Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Piring Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Gelas Cantik");
        speedboat.add("Gelas Cantik");

        point =  new ArrayList<>();
        point.add("50");
        point.add("100");
        point.add("69");
        point.add("33");
        point.add("2");
        point.add("50");
        point.add("100");
        point.add("69");
        point.add("33");
        point.add("2");

        myPointRedeemAdapter = new MyPointRedeemAdapter(speedboat, point, sharedPreferences,MyPointRewardActivity.this);
        recyclerView.setAdapter(myPointRedeemAdapter);
    }
}