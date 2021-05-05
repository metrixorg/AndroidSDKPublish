package ir.metrix.webbridge

import android.webkit.WebView
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ir.metrix.AttributionData
import org.json.JSONException
import org.json.JSONObject


object MetrixBridgeUtil {

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    fun execAttributionCallbackCommand(
        webView: WebView?,
        commandName: String,
        attribution: AttributionData
    ) {
        if (webView == null) {
            return
        }
        webView.post(Runnable {
            val jsonAttribution = JSONObject()
            try {
                jsonAttribution.put(
                    "attributionStatus",
                    if (attribution.attributionStatus == null) JSONObject.NULL else attribution.attributionStatus.toString()
                )
                jsonAttribution.put(
                    "trackerToken",
                    if (attribution.trackerToken == null) JSONObject.NULL else attribution.trackerToken
                )
                jsonAttribution.put(
                    "acquisitionAd",
                    if (attribution.acquisitionAd == null) JSONObject.NULL else attribution.acquisitionAd
                )
                jsonAttribution.put(
                    "acquisitionAdSet",
                    if (attribution.acquisitionAdSet == null) JSONObject.NULL else attribution.acquisitionAdSet
                )
                jsonAttribution.put(
                    "acquisitionCampaign",
                    if (attribution.acquisitionCampaign == null) JSONObject.NULL else attribution.acquisitionCampaign
                )
                jsonAttribution.put(
                    "acquisitionSource",
                    if (attribution.acquisitionSource == null) JSONObject.NULL else attribution.acquisitionSource
                )
                val command =
                    "javascript:$commandName($jsonAttribution);"
                webView.loadUrl(command)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        })
    }

    fun execSingleValueCallback(
        webView: WebView?,
        commandName: String,
        value: String
    ) {
        if (webView == null) {
            return
        }
        webView.post(Runnable {
            val command = "javascript:$commandName('$value');"
            webView.loadUrl(command)
        })
    }

    fun jsonToMap(json: String): Map<String, String>? {
        val mapAdapter = moshi.adapter<Map<String, String>>(Types.newParameterizedType(
            Map::class.java, String::class.java, String::class.java
        ))

        return mapAdapter.fromJson(json)
    }
}