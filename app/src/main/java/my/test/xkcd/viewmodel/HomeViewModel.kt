package my.test.xkcd.viewmodel

import android.content.Context
import android.view.View
import my.test.xkcd.databinding.ActivityHomeBinding

/**
 * Created by Shahzaib on 8/14/2019.
 */
class HomeViewModel(private val context: Context?, private val binding: ActivityHomeBinding,
                    private val dataListener: DataListener) : ViewModel() {

    /*
     home view model
     */


    fun onClickNext(): View.OnClickListener {
        return View.OnClickListener {
            dataListener.onNavigationToNextComic()
        }
    }

    fun onClickPrevious(): View.OnClickListener {
        return View.OnClickListener {
            dataListener.onNavigationToPreviousComic()
        }
    }

    fun onClickEnd(): View.OnClickListener {
        return View.OnClickListener {
            dataListener.onNavigationToLastComic()
        }
    }

    fun onClickStart(): View.OnClickListener {
        return View.OnClickListener {
            dataListener.onNavigationToFirstComic()
        }
    }

    override fun onDestroy() {

    }

    interface DataListener {
        fun onNavigationToNextComic()
        fun onNavigationToPreviousComic()
        fun onNavigationToLastComic()
        fun onNavigationToFirstComic()
    }
}

