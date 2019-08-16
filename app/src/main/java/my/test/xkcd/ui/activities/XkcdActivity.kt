package my.test.xkcd.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * added base activity class to have common functionality for all activities if needed
 */
@SuppressLint("Registered")
open class XkcdActivity : AppCompatActivity() {

    private var mCallingActivity: Activity? = null

    protected fun onCreate(savedInstanceState: Bundle, currentActivity: Activity) {
        super.onCreate(savedInstanceState)
        mCallingActivity = currentActivity
    }
}
