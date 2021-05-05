package ir.metrix.webbridge

import android.net.Uri
import android.webkit.JavascriptInterface
import android.webkit.WebView
import ir.metrix.*
import ir.metrix.messaging.RevenueCurrency


class MetrixBridgeInstance {
    private var webView: WebView? = null
    private var isInitialized = false
    private var shouldDeferredDeeplinkBeLaunched = true

    internal constructor()
    internal constructor(webView: WebView) {
        setWebView(webView)
    }

    private fun isInitialized(): Boolean {
        if (webView == null) {
            return false
        }
        return true
    }

    @JavascriptInterface
    fun newEvent(name: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.newEvent(name)
    }

    @JavascriptInterface
    fun newEvent(name: String, attributesJson: String) {
        if (!isInitialized()) {
            return
        }
        val attributes = MetrixBridgeUtil.jsonToMap(attributesJson)
        Metrix.newEvent(name, attributes)
    }

    @JavascriptInterface
    fun newRevenueSimple(slug: String, revenue: Double) {
        if (!isInitialized()) {
            return
        }
        Metrix.newRevenue(slug, revenue)
    }

    @JavascriptInterface
    fun newRevenueCurrency(slug: String, revenue: Double, currency: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.newRevenue(slug, revenue, RevenueCurrency.valueOf(currency))
    }

    @JavascriptInterface
    fun newRevenueOrderId(slug: String, revenue: Double, orderId: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.newRevenue(slug, revenue, orderId)
    }

    @JavascriptInterface
    fun newRevenueFull(slug: String, revenue: Double, currency: String, orderId: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.newRevenue(slug, revenue, RevenueCurrency.valueOf(currency), orderId)
    }

    @JavascriptInterface
    fun addUserAttributes(userAttributesJson: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.addUserAttributes(MetrixBridgeUtil.jsonToMap(userAttributesJson))
    }

    @JavascriptInterface
    fun setPushToken(token: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.setPushToken(token)
    }

    @JavascriptInterface
    fun getSessionNum() =
        if (!isInitialized()) null
        else Metrix.getSessionNum().toString()

    @JavascriptInterface
    fun getSessionId() =
        if (!isInitialized()) null
        else Metrix.getSessionId()

    @JavascriptInterface
    fun setOnAttributionChangedListener(attributionCallbackName: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.setOnAttributionChangedListener(object : OnAttributionChangeListener {
            override fun onAttributionChanged(attributionData: AttributionData) {
                MetrixBridgeUtil.execAttributionCallbackCommand(
                    webView,
                    attributionCallbackName,
                    attributionData
                )
            }
        })
    }

    @JavascriptInterface
    fun setUserIdListener(userIdCallbackName: String) {
        if (!isInitialized()) {
            return
        }
        Metrix.setUserIdListener(object : UserIdListener {
            override fun onUserIdReceived(userId: String) {
                MetrixBridgeUtil.execSingleValueCallback(
                    webView,
                    userIdCallbackName,
                    userId
                )
            }
        })
    }

    @JavascriptInterface
    fun setOnDeeplinkResponseListener(
        shouldLaunchDeeplink: Boolean,
        deeplinkCallbackName: String
    ) {
        if (!isInitialized()) {
            return
        }
        Metrix.setOnDeeplinkResponseListener(
            object : OnDeeplinkResponseListener {
                override fun launchReceivedDeeplink(deeplink: Uri): Boolean {
                    MetrixBridgeUtil.execSingleValueCallback(
                        webView,
                        deeplinkCallbackName,
                        deeplink.toString()
                    )
                    return shouldLaunchDeeplink
                }
            })
    }

    @JavascriptInterface
    fun teardown() {
        isInitialized = false
        shouldDeferredDeeplinkBeLaunched = true
    }

    fun setWebView(webView: WebView) {
        this.webView = webView
        webView.addJavascriptInterface(
            this,
            JAVASCRIPT_INTERFACE_NAME
        )
    }

    fun unregister() {
        if (!isInitialized()) {
            return
        }
        webView!!.removeJavascriptInterface(JAVASCRIPT_INTERFACE_NAME)
        webView = null
        isInitialized = false
    }

    companion object {
        private const val JAVASCRIPT_INTERFACE_NAME = "MetrixBridge"
    }
}