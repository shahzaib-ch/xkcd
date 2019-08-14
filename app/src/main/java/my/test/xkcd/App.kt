package my.test.xkcd

import android.app.Application
import timber.log.Timber


/**
 * Created by shahzaib on 8/14/2019.
 */
class App : Application() {

    /*
     base application class
     to do application level initialization
     */

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}