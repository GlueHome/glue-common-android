package com.gluehome.common.data.log

import android.util.Log
import com.sematext.logseneandroid.Logsene
import org.json.JSONObject


class SematextTree(private val logsene: Logsene) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?, extraInfo: Map<String, String>) {

        when (priority) {
            Log.DEBUG, Log.VERBOSE -> {
                if (extraInfo.isNotEmpty()) {
                    logsene.event(enrichLog(message, extraInfo))
                } else {
                    logsene.debug(message)
                }
            }
            Log.WARN -> logsene.warn(message)
            Log.ERROR -> logsene.error(t)
        }
    }

    private fun enrichLog(
        message: String,
        extraInfo: Map<String, String>
    ): JSONObject {
        val fullInfo = hashMapOf(
            "level" to "debug",
            "message" to message
        )

        extraInfo.forEach { fullInfo[it.key] = it.value }

        return JSONObject(fullInfo)
    }
}