package ir.metrix.unity;

import static com.unity3d.player.UnityPlayer.UnitySendMessage;
import android.net.Uri;

import com.squareup.moshi.Moshi;

import java.util.Map;

import ir.metrix.AttributionDataJsonAdapter;

public class MetrixUnity {

    public static void setOnDeeplinkResponseListener(final boolean shouldLaunchDeeplink) {
        ir.metrix.Metrix.setOnDeeplinkResponseListener(new ir.metrix.OnDeeplinkResponseListener() {
            @Override
            public boolean launchReceivedDeeplink(Uri deeplink) {
                UnitySendMessage(UnityConfig.MANAGER_OBJECT, UnityConfig.ON_DEFERRED_DEEPLINK,
                        deeplink.toString());
                return shouldLaunchDeeplink;
            }
        });
    }

    public static void setUserIdListener() {
        ir.metrix.Metrix.setUserIdListener(new ir.metrix.UserIdListener() {
            @Override
            public void onUserIdReceived(String userId) {
                UnitySendMessage(UnityConfig.MANAGER_OBJECT, UnityConfig.ON_RECEIVE_USER_ID_LISTENER,
                        userId);
            }
        });
    }

    public static void setOnAttributionChangedListener() {
        ir.metrix.Metrix.setOnAttributionChangedListener(new ir.metrix.OnAttributionChangeListener() {
            @Override
            public void onAttributionChanged(ir.metrix.AttributionData attributionData) {
                UnitySendMessage(UnityConfig.MANAGER_OBJECT, UnityConfig.ON_ATTRIBUTION_CHANGE_LISTENER,
                        new AttributionDataJsonAdapter(new Moshi.Builder().build()).toJson(attributionData));
            }
        });
    }

    public static void newEvent(final String eventName) {
        ir.metrix.Metrix.newEvent(eventName);
    }

    public static void newEvent(final String eventName,
                                final Map<String, String> customAttributes) {
        ir.metrix.Metrix.newEvent(eventName, customAttributes);
    }


    public static void newRevenueSimple(String slug, double revenue) {
        ir.metrix.Metrix.newRevenue(slug, revenue);
    }

    public static void newRevenueCurrency(String slug, double revenue, String currency) {
        ir.metrix.Metrix.newRevenue(slug, revenue, ir.metrix.messaging.RevenueCurrency.valueOf(currency));
    }

    public static void newRevenueOrderId(String slug, double revenue, String orderId) {
        ir.metrix.Metrix.newRevenue(slug, revenue, orderId);
    }

    public static void newRevenueFull(String slug, double revenue, String currency,
                                      String orderId) {
        ir.metrix.Metrix.newRevenue(slug, revenue, ir.metrix.messaging.RevenueCurrency.valueOf(currency), orderId);
    }

    public static void addUserAttributes(final Map<String, String> userAttrs) {
        ir.metrix.Metrix.addUserAttributes(userAttrs);
    }

    public static int getSessionNum() {
        return ir.metrix.Metrix.getSessionNum();
    }

    public static void setPushToken(String token) {
        ir.metrix.Metrix.setPushToken(token);
    }

    public static String getSessionId() {
        return ir.metrix.Metrix.getSessionId();
    }
}
