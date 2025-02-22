namespace MauiSample;

#if ANDROID
using NewBinding = NewBindingAndroid.DotnetNewBinding;
using Microsoft.Maui.Handlers;
using Microsoft.Maui.Platform;
#elif (NETSTANDARD || !PLATFORM) || (NET6_0_OR_GREATER && !IOS && !ANDROID)
using NewBinding = System.Object;
#endif

public partial class MainPage : ContentPage
{
    string publisherkey = ""; // Get it from stripe account Looks like pk_test*******
    string customerID = ""; // Get it from APIs Looks like cus_*******
    string ephemeralKey = ""; // Get it from APIs Looks like ek_test_*******
    string secretKey = ""; // Get it APIs Looks like pi_*******

#if ANDROID
    private readonly StripeIntegration _stripeIntegration = new StripeIntegration();
#endif
    public MainPage()
    {
        InitializeComponent();
#if ANDROID
        _stripeIntegration.InitializeStripe();
#endif
    }

#if ANDROID


#endif
    private void Button_Clicked(object sender, EventArgs e)
    {
#if ANDROID
        _stripeIntegration.InitializePayment(publisherkey, customerID, ephemeralKey, secretKey);
#endif
    }

#if ANDROID
    public class StripeIntegration
    {
        public delegate void PaymentResultHandler(string result);
        public event PaymentResultHandler PaymentResultReceived;
        public void InitializeStripe()
        {
            NewBindingAndroid.StripeIntegration.NewStripeIntegration(Platform.CurrentActivity);

            var listener = new PaymentResultListener(result =>
            {
                PaymentResultReceived?.Invoke(result);
            });
            NewBindingAndroid.StripeIntegration.SetPaymentResultListener(listener);
        }
        public void InitializePayment(string publishableKey, string customerId, string ephemeralKey, string paymentIntent)
        {
            NewBindingAndroid.StripeIntegration.InitializePayment(Platform.CurrentActivity, publishableKey, customerId, ephemeralKey, paymentIntent);
        }

        private class PaymentResultListener : Java.Lang.Object, NewBindingAndroid.StripeIntegration.IPaymentResultListener
        {
            private readonly Action<string> _callback;

            public PaymentResultListener(Action<string> callback)
            {
                _callback = callback;
            }

            public void OnPaymentResult(string result)
            {
                _callback?.Invoke(result);
            }
        }
    }
#endif
}