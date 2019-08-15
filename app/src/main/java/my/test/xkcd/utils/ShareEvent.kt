package my.test.xkcd.utils

import my.test.xkcd.data.model.comic.ComicResponse

/**
 * Created by Shahzaib on 8/15/2019.
 * Even to be passed through event bus to communicate with different components
 */
class ShareEvent(val eventType: AppBundles, val comicInfo: ComicResponse?)