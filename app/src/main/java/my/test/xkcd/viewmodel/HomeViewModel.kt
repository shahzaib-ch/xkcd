package my.test.xkcd.viewmodel

import android.content.Context
import android.view.View
import my.test.xkcd.databinding.ActivityHomeBinding

/**
 * Created by Shahzaib on 8/14/2019.
 */
class HomeViewModel(val context: Context?, val binding: ActivityHomeBinding, val dataListener: DataListener) : ViewModel() {

    /*
     home view model
     */

    fun onClickProfile(): View.OnClickListener {
        return View.OnClickListener {
        }
    }

    override fun onDestroy() {

    }

    interface DataListener {

    }
}

