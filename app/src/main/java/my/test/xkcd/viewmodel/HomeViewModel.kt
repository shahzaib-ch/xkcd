package my.test.xkcd.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import kotlinx.coroutines.*
import my.test.xkcd.data.ApiFactory
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.ui.fragments.ComicViewFragment
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Created by Shahzaib on 8/14/2019.
 */
class HomeViewModel(private val context: Context?,
                    private val dataListener: DataListener) : ViewModel() {

    /*
     home view model
     */
    // variables to handle api call job
    private val parentJob = Job()
    private val coroutineContext : CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    var comicInfo: ComicResponse? = null
    var currentComicViewFragment: ComicViewFragment? = null
    val progressVisibility = ObservableInt(View.GONE)

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

    fun comicSearchByText(query: String) {
        progressVisibility.set(View.VISIBLE)
        var comicId = 1
        runBlocking {
            val job = scope.launch {
                val apiInterface = ApiFactory.provideSearchApi()
                val comicSearchResponse = try {
                    apiInterface.comicSearchByText("xkcd", query)
                } catch (e: Exception) {
                    Timber.e(e)
                    progressVisibility.set(View.GONE)
                    dataListener.onMessage(e.message!!)
                    return@launch
                }
                comicId = parseAndGetMostRelevantComicId(comicSearchResponse)
            }
            job.join()
            dataListener.onComicSearchSuccess(comicId)
            progressVisibility.set(View.GONE)
        }
    }

    private fun parseAndGetMostRelevantComicId(response: String) : Int{
        val trimmedResponse = response.trim()
        val comicIdList = trimmedResponse.split(" ")
        // leaving first 2 as these are not comic ids
        return comicIdList[2].trim().toInt()
    }

    override fun onDestroy() {
        parentJob.cancel()
    }

    interface DataListener {
        fun onNavigationToNextComic()
        fun onNavigationToPreviousComic()
        fun onNavigationToLastComic()
        fun onNavigationToFirstComic()
        fun onComicSearchSuccess(comicId: Int)
        fun onMessage(message: String)
    }
}

