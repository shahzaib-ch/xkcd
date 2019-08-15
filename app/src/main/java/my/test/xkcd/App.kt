package my.test.xkcd

import android.app.Application
import timber.log.Timber


/**
 * Created by shahzaib on 8/14/2019.
 * Base application class, can be used for application level intialization
 */
class App : Application() {

    /*
     base application class
     to do application level initialization
     */

    override fun onCreate() {
        super.onCreate()
        //initializing timber to use for logging
        Timber.plant(Timber.DebugTree())
    }
}