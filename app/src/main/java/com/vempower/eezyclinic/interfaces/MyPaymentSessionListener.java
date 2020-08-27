package com.vempower.eezyclinic.interfaces;

import androidx.annotation.NonNull;

import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionData;
import com.stripe.android.model.PaymentMethod;

import org.jetbrains.annotations.NotNull;

public class MyPaymentSessionListener implements PaymentSession.PaymentSessionListener {


    @Override
    public void onError(int i, @NotNull String s) {

    }

    @Override
    public void onPaymentSessionDataChanged(@NotNull PaymentSessionData data) {
        if (data.getUseGooglePay()) {
            // customer intends to pay with Google Pay
        } else {
            final PaymentMethod paymentMethod = data.getPaymentMethod();
            if (paymentMethod != null) {
                // Display information about the selected payment method
            }
        }

        // Update your UI here with other data
        if (data.isPaymentReadyToCharge()) {
            // Use the data to complete your charge - see below.
        }

    }

    @Override
    public void onCommunicatingStateChanged(boolean isCommunicating) {
        if (isCommunicating) {
            // update UI to indicate that network communication is in progress
        } else {
            // update UI to indicate that network communication has completed
        }

    }
}
