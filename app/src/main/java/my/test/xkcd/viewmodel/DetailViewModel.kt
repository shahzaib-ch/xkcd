package my.test.xkcd.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View

/**
 * Created by Shahzaib on 8/14/2019.
 * detail view model for detail activity
 */
class DetailViewModel(private val context: Context?,
                      private val dataListener: DataListener) : ViewModel() {

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

