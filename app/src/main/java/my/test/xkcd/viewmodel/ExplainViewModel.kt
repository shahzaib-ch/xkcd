package my.test.xkcd.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt

/**
 * Created by Shahzaib on 8/14/2019.
 * explain view model for explain activity used to show explanation of comic
 */
class ExplainViewModel(private val context: Context?,
                       private val dataListener: DataListener) : ViewModel() {
    val progressVisibility = ObservableInt(View.VISIBLE)


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

