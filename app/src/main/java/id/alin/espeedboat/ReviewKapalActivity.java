package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import id.alin.espeedboat.MyAdapter.MyPointRedeemAdapter;
import id.alin.espeedboat.MyAdapter.ReviewKapalAdapter;
import id.alin.espeedboat.MySharedPref.Config;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

public class ReviewKapalActivity extends AppCompatActivity {
    ReviewKapalAdapter reviewKapalAdapter;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    ArrayList<String> speedboat;
    ArrayList<Float> review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_kapal);
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences(Config.ESPEED_STORAGE, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReviewKapal);

        speedboat = new ArrayList<>();
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");
        speedboat.add("CASPLA");


        review = new ArrayList<>();
        review.add((float) 2.5);
        review.add((float) 3.5);
        review.add((float) 0);
        review.add((float) 5.0);
        review.add((float) 5.0);
        review.add((float) 1.5);
        review.add((float) 4.5);
        review.add((float) 2.0);
        review.add((float) 3.5);
        review.add((float) 4.5);

        reviewKapalAdapter = new ReviewKapalAdapter(speedboat, review, ReviewKapalActivity.this);
        recyclerView.setAdapter(reviewKapalAdapter);
    }
}