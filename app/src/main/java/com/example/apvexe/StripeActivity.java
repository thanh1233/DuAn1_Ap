package com.example.apvexe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.stripe.android.Logger;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StripeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe);
        // tao cau hinh thanh toan
        PaymentConfiguration.init(
                getApplicationContext(),
                "pk_test_51K1BNALYssbLNv8DBbCyoJW3WG44A20m0UUTxW7czzztkO2G5rWZ6dQLmyhZK3aSp8FjH5RXkjUhytdFDJeV1HZM00QmfcVb94"
        );
        payment();
    }

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public void payment(){
        OkHttpClient client = new OkHttpClient();

        String json = "{\"amount\":2000,\"currency\":\"usd\",\"payment_method_types\":[\"card\"]}";

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer sk_test_51K1BNALYssbLNv8D8dIGhvXdhpxm5VewFLTLsEK3iyNVWghiYZ8iRjqFJcKtTvOCEgOUUu11W1FGC57jrMKVDNmQ00Y7XqrJcy")
                .url("https://api.stripe.com/v1/payment_intents")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String s = response.body().string();
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}