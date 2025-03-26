package com.whatever.caramel.feature.copule.invite.share

import android.content.Context
import android.content.Intent

class ShareController(private val context: Context) : ShareService {

    override fun shareContents(title: String, url: String) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, "$title\n$url")
        }

        context.startActivity(
            Intent.createChooser(shareIntent, "공유하기").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

}
