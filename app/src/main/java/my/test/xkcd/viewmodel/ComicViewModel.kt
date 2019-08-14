package my.test.xkcd.viewmodel

import android.content.Context
import android.view.View
import my.test.xkcd.databinding.FragmentComicViewBinding

/**
 * Created by Shahzaib on 8/14/2019.
 */
class ComicViewModel(val context: Context?, val binding: FragmentComicViewBinding, val dataListener: DataListener) : ViewModel() {

    /*
     comic view model
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

