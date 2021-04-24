
package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class ReviewKapalDetailActivity extends AppCompatActivity {
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_kapal_detail);
        ratingBar = (RatingBar) findViewById(R.id.rating);

        ratingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReviewKapalDetailActivity.this, "value is " + ratingBar.getRating(), Toast.LENGTH_SHORT);
            }
        });
    }
}