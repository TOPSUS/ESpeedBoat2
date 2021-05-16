package id.alin.espeedboat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import id.alin.espeedboat.MyRetrofit.ApiClient;

public class WebviewActivity extends AppCompatActivity {
    private ImageButton backButton;
    private LottieAnimationView lottieAnimationView;

    public static final String LINK = "LINK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initWidget();

        WebView myWebView = findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                WebviewActivity.this.lottieAnimationView.setVisibility(View.INVISIBLE);
            }
        });

        String link_berita = String.valueOf(getIntent().getStringExtra(LINK));;
        myWebView.loadUrl(link_berita);
    }

    private void initWidget() {
        this.backButton = findViewById(R.id.backButton);
        this.lottieAnimationView = findViewById(R.id.lottieanimation);
        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}