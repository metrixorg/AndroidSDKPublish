package ir.metrix;

import java.util.Map;


public class Metrix {

    /**
     * Gets the current session id.
     *
     * @return The current sessionId value.
     */
    public static int getSessionNum() {
        return 0;
    }

    /**
     * Gets the user id.
     *
     * @return The current device id.
     */
    public static String getUserId() {
        return "";
    }

    /**
     * Gets the current session id.
     *
     * @return The current sessionId value.
     */
    public static String getSessionId() {
        return "";
    }

    /**
     * log a new custom event
     *
     * @param slug target event slug
     */
    public static void newEvent(final String slug) {
        newEvent(slug, null);
    }

    /**
     * log a new custom event
     *
     * @param slug        target event slug
     * @param customAttributes target customAttributes
     */
    public static void newEvent(final String slug,
                         final Map<String, String> customAttributes) {
        
    }

    /**
     * log new revenue
     *
     * @param slug     revenue slug
     * @param revenue  revenue value
     */
    public static void newRevenue(String slug, Double revenue) {
        newRevenue(slug, revenue, null);

    }
    /**
     * log new revenue
     *
     * @param slug     revenue slug
     * @param revenue  revenue value
     * @param orderId  order id
     */
    public static void newRevenue(String slug, Double revenue, String orderId) {
        
    }

    /**
     * Sets the attributes used to identify this user. These attributes are added to all
     * subsequent events.
     *
     * @param userAttrs Custom attributes for user
     */
    public static void addUserAttributes(final Map<String, String> userAttrs) {
        
    }

    /**
     * Set the FCM push token used by Metrix to track app uninstalls
     *
     * @param pushToken the FCM token
     */
    public static void setPushToken(String pushToken) {
        
    }
}