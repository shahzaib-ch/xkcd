package my.test.xkcd.utils

/**
 * Created by Shahzaib on 8/14/2019.
 * used to store keys used in app for passing data in intent etc.
 */

enum class AppBundles(val key: String) {

    // Enums for usage as keys, or events types for event bus
    COMIC_ID("comic_id"), COMIC("comic"), SHARE("share"), UPDATE_HOME("update_home")
}
