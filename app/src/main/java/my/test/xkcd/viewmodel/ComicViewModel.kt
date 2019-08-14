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

    val progressVisibility = ObservableInt(View.GONE)
    lateinit var comicInfo: ComicResponse

    fun onClickProfile(): View.OnClickListener {
        return View.OnClickListener {
        }
    }

    fun getComicInfo() {
        progressVisibility.set(View.VISIBLE)
        runBlocking {
            val apiInterface = ApiFactory.provideApi()
            val comicInfo = try {
                apiInterface.getComicInfo("627")
            } catch (e: Exception) {
                Timber.e(e)
                progressVisibility.set(View.GONE)
                return@runBlocking
            }
            progressVisibility.set(View.GONE)
            // saving comic info for later use as well
            this@ComicViewModel.comicInfo = comicInfo
            dataListener.loadComicImage(comicInfo)
        }
    }

    override fun onDestroy() {

    }

    interface DataListener {
        fun loadComicImage(comicInfo: ComicResponse)
    }
}

