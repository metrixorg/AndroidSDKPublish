package ir.metrix.webbridge

import android.webkit.WebView

object MetrixBridge {
    private var defaultInstance: MetrixBridgeInstance? = null

    // New builder gets dependencies
    @Synchronized
    fun registerAndGetInstance(webView: WebView): MetrixBridgeInstance {
        if (defaultInstance == null) {
            defaultInstance = MetrixBridgeInstance(webView)
        }
        return defaultInstance as MetrixBridgeInstance
    }

    @Synchronized
    fun getDefaultInstance(): MetrixBridgeInstance {
        if (defaultInstance == null) {
            defaultInstance = MetrixBridgeInstance()
        }
        return defaultInstance as MetrixBridgeInstance
    }

    fun setWebView(webView: WebView) {
        getDefaultInstance().setWebView(webView)
    }

    @Synchronized
    fun unregister() {
        defaultInstance?.unregister()
        defaultInstance = null
    }
}