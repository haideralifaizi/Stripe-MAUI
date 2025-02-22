package com.example.newbinding;

import android.app.Activity;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
public class StripeIntegration {

    static PaymentSheet paymentSheet;
    static Activity activity;
    static PaymentResultListener paymentResultListener;
    public interface PaymentResultListener {
        void onPaymentResult(String result);
    }
    public StripeIntegration() {
    }

    public static void setPaymentResultListener(PaymentResultListener listener) {
        paymentResultListener = listener;
    }
    public static void NewStripeIntegration(Activity _activity) {
        activity = _activity;
        paymentSheet = new PaymentSheet((ComponentActivity) activity, StripeIntegration::onPaymentSheetResult);
    }

    public static void InitializePayment(Activity activity,String publishableKey, String customerId, String ephemeralKey, String paymentIntent) {

        PaymentConfiguration.init(activity, publishableKey);
        PaymentSheet.CustomerConfiguration customerConfig = new PaymentSheet.CustomerConfiguration(customerId,ephemeralKey);
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("ABC Company")
                .customer(customerConfig)
                .allowsDelayedPaymentMethods(true)
                .allowsRemovalOfLastSavedPaymentMethod(true)
                .allowsPaymentMethodsRequiringShippingAddress(true)
                .paymentMethodLayout(PaymentSheet.PaymentMethodLayout.Automatic)
                .build();

        paymentSheet.presentWithPaymentIntent(paymentIntent, configuration);
    }

    public static void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            paymentResultListener.onPaymentResult("Completed");
            Toast toast = Toast.makeText(activity.getApplicationContext(),"Completed",Toast.LENGTH_LONG);
            toast.show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            paymentResultListener.onPaymentResult("Cancelled");
            Toast toast = Toast.makeText(activity.getApplicationContext(),"Cancelled",Toast.LENGTH_LONG);
            toast.show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            String errorMessage = ((PaymentSheetResult.Failed) paymentSheetResult).getError().getMessage();
            paymentResultListener.onPaymentResult("Failed: " + errorMessage);
            Toast toast = Toast.makeText(activity.getApplicationContext(), "Failed: " + errorMessage, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}