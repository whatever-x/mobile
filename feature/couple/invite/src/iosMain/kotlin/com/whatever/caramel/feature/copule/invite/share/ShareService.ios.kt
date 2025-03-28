package com.whatever.caramel.feature.copule.invite.share

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

class ShareController : ShareService {

    override fun shareContents(title: String, url: String) {
        val textToShare = "$title\n$url"
        val activityItems = listOf(textToShare)
        val controller = UIActivityViewController(
            activityItems = activityItems,
            applicationActivities = null
        )
        val rootController = UIApplication.sharedApplication.keyWindow?.rootViewController

        rootController?.presentViewController(controller, animated = true, completion = null)
    }

}
