package my.test.xkcd

import android.graphics.Bitmap
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import my.test.xkcd.utils.AppConstants
import my.test.xkcd.utils.AppUtil
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Instrumented test, which will execute on an Android device.
 * tests app util methods
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppUtilTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("my.test.xkcd", appContext.packageName)
    }

    @Test
    fun shouldReturnFile() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        val bitmap = Bitmap.createBitmap(100, 200, Bitmap.Config.ARGB_8888);
        val file = AppUtil.saveBitmapToFile(
            File(appContext.filesDir, AppConstants.COMIC_SHARING_FOLDER_NAME),
            AppConstants.COMIC_SHARING_FILE_NAME,
            bitmap,
            Bitmap.CompressFormat.PNG, 100
        )
        Assert.assertNotNull(file)
    }
}
