package com.vempower.eezyclinic.activities.stripe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.vempower.eezyclinic.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class StripCheckoutActivityJava extends AppCompatActivity {
    private static final String STRIPE_PUBLISHABLE_KEY="pk_test_HIesZRV8EySX7jJyGSKXnBJ500WWe53Ndf";
    private static final String CLIENTSECRET_KEY="pk_test_HIesZRV8EySX7jJyGSKXnBJ500WWe53Ndf";

    private Stripe stripe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stripe_activity_checkout);
        loadPage();
    }

    private void loadPage() {
        // Clear the card widget
        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        cardInputWidget.setPostalCodeEnabled(false);
        cardInputWidget.setUsZipCodeRequired(false);

        cardInputWidget.clear();

        onRetrievedKey(STRIPE_PUBLISHABLE_KEY);
    }

    private void pay() {
        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
        PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();

        if (params == null) {
            return;
        }
        stripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
            @Override
            public void onSuccess(@NonNull PaymentMethod result) {
                // Create and confirm the PaymentIntent by calling the sample server's /pay endpoint.
                pay(result.id, null);
            }

            @Override
            public void onError(@NonNull Exception e) {

            }
        });
    }

    private void pay(@Nullable String paymentMethodId, @Nullable String paymentIntentId) {

        try {
            finalCallStrip(this);
        } catch (Exception e) {
            //e.printStackTrace();
            displayAlert("Error",e.getMessage(),true);
        }


    }

    private void displayAlert(@NonNull final String title, @NonNull final String message, final boolean restartDemo) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final AlertDialog.Builder builder =
                        new AlertDialog.Builder(StripCheckoutActivityJava.this)
                                .setTitle(title)
                                .setMessage(message);
                new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                if (restartDemo) {
                    builder.setPositiveButton("Restart demo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loadPage();
                        }
                    });

                } else {
                    builder.setPositiveButton("Ok", null);
                }
                builder
                        .create()
                        .show();
            }
        });


    }

    private void onRetrievedKey(@NonNull String stripePublishableKey) {
        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
        final Context applicationContext = getApplicationContext();
        PaymentConfiguration.init(applicationContext, stripePublishableKey);
        stripe = new Stripe(applicationContext, stripePublishableKey);

        // Hook up the pay button to the card widget and stripe instance
        Button payButton = findViewById(R.id.payButton);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }




    private void finalCallStrip(final StripCheckoutActivityJava activity) throws IOException {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>(){}.getType();

            //
               /* PaymentIntentCreateParams params =
                        PaymentIntentCreateParams.builder()
                                .setAmount(1099L)
                                .setCurrency("usd")
                                .build();

                PaymentIntent intent = PaymentIntent.create(params);
                String clientSecret = intent.getClientSecret();*/
            //

            final String paymentIntentClientSecret = CLIENTSECRET_KEY;//responseMap.get("clientSecret");//TODO get key from backend
            String requiresAction =  "true";//responseMap.get("requiresAction");

             if (paymentIntentClientSecret != null) {
                if ("true".equals(requiresAction)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stripe.handleNextActionForPayment(activity, paymentIntentClientSecret);
                        }
                    });

                } else {
                    activity.displayAlert("Payment succeeded",
                            paymentIntentClientSecret, true);
                }
            }
        }


    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        private final WeakReference<StripCheckoutActivityJava> activityRef;

        PaymentResultCallback(@NonNull StripCheckoutActivityJava activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull final PaymentIntentResult result) {
            final StripCheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }

           final PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        activity.displayAlert("Payment completed",
                                gson.toJson(paymentIntent), true);

                    }
                });
                // Payment completed successfully

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final PaymentIntent.Error error = paymentIntent.getLastPaymentError();
                        final String errorMessage;
                        if (error != null && error.getMessage() != null) {
                            errorMessage = error.getMessage();
                        } else {
                            errorMessage = "Unknown error";
                        }
                        activity.displayAlert("Payment failed", errorMessage, false);
                    }
                });

            } else if (status == PaymentIntent.Status.RequiresConfirmation) {
                // After handling a required action on the client, the status of the PaymentIntent is
                // requires_confirmation. You must send the PaymentIntent ID to your backend
                // and confirm it to finalize the payment. This step enables your integration to
                // synchronously fulfill the order on your backend and return the fulfillment result
                // to your client.
                activity.pay(null, paymentIntent.getId());
            }
        }

        @Override
        public void onError(@NonNull final Exception e) {
            final StripCheckoutActivityJava activity = activityRef.get();
            if (activity == null) {
                return;
            }

            // Payment request failed – allow retrying using the same payment method
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.displayAlert("Error", e.toString(), false);
                }
            });


        }
    }
}
