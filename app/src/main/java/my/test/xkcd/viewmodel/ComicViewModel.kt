package my.test.xkcd.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import kotlinx.coroutines.runBlocking
import my.test.xkcd.data.ApiFactory
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.databinding.FragmentComicViewBinding
import timber.log.Timber

/**
 * Created by Shahzaib on 8/14/2019.
 */
class ComicViewModel(private val context: Context?, private val binding: FragmentComicViewBinding,
                     private val dataListener: DataListener) : ViewModel() {

    /*
     comic view model
     */

    val progressVisibility = ObservableInt(View.VISIBLE)
    var comicInfo: ComicResponse? = null

    fun onClickProfile(): View.OnClickListener {
        return View.OnClickListener {
        }
    }

    fun getComicInfo(comicId: String) {
        progressVisibility.set(View.VISIBLE)
        runBlocking {
            val apiInterface = ApiFactory.provideApi()
            val comicInfo = try {
                apiInterface.getComicInfo(comicId)
            } catch (e: Exception) {
                Timber.e(e)
                progressVisibility.set(View.GONE)
                dataListener.onMessage(e.message!!)
                return@runBlocking
            }
            // saving comic info for later use as well
            this@ComicViewModel.comicInfo = comicInfo
            dataListener.loadComicImageInWebView(comicInfo)
        }
    }

    override fun onDestroy() {

    }

    interface DataListener {
        fun loadComicImageInWebView(comicInfo: ComicResponse)
        fun onMessage(message: String)
    }

    interface HomeActivityDataListener {
        fun onUpdate(comicInfo: ComicResponse)
    }
}

