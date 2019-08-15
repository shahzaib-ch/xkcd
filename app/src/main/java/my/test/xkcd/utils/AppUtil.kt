package my.test.xkcd.utils

import android.graphics.Bitmap
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


/**
 * Created by Shahzaib on 8/15/2019.
 */
object AppUtil {

    fun saveBitmapToFile(dir: File, fileName: String, bm: Bitmap,
                         format: Bitmap.CompressFormat, quality: Int): File? {
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val imageFile = File(dir, fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(imageFile)
            bm.compress(format, quality, fos)
            fos.close()
            return imageFile
        } catch (e: IOException) {
            Timber.e(e)
            if (fos != null) {
                try {
                    fos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
        }
        return null
    }

}