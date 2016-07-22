package br.ufg.inf.homeshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String textoQRCode = rawResult.getText();
        String marketId = getMarketId(textoQRCode);
        String productId = getProductId(textoQRCode);

        SharedPreferences settings = this.getSharedPreferences(MainActivity.PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("marketId", Long.valueOf(marketId));
        editor.apply();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("marketId", productId);
        startActivity(intent);
    }

    private String getMarketId(String textoQRCode) {
        String marketsRegex = "markets/(\\d+)";
        return findFirst(textoQRCode, marketsRegex);
    }

    private String getProductId(String textoQRCode) {
        String productRegex = "products/(\\d+)";
        return findFirst(textoQRCode, productRegex);
    }

    private static String findFirst(String textToSearch, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textToSearch);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
