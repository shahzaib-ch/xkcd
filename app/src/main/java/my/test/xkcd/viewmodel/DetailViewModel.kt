package my.test.xkcd.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View

/**
 * Created by Shahzaib on 8/14/2019.
 */
class DetailViewModel(private val context: Context?,
                      private val dataListener: DataListener) : ViewModel() {

    /*
     detail view model
     */

    fun onClickBack(): View.OnClickListener {
        return View.OnClickListener {
            (context as Activity).onBackPressed()
        }
    }

    override fun onDestroy() {

    }

    interface DataListener {
        fun onMessage(message: String)
    }
}

