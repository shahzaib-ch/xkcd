package my.test.xkcd.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableInt
import kotlinx.coroutines.*
import my.test.xkcd.data.ApiFactory
import my.test.xkcd.data.model.comic.ComicResponse
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Created by Shahzaib on 8/14/2019.
 * comic view model for Comic view fragment
 */
class ComicViewModel(private val context: Context?,
                     private val dataListener: DataListener) : ViewModel() {


    // variables to handle api call job
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val progressVisibility = ObservableInt(View.VISIBLE)
    var comicInfo: ComicResponse? = null


    fun getComicInfo(comicId: String): ComicResponse? {
        progressVisibility.set(View.VISIBLE)
        var comicInfo: ComicResponse? = null
        runBlocking {
            val job = scope.launch {
                val apiInterface = ApiFactory.provideApi()
                comicInfo = try {
                    apiInterface.getComicInfo(comicId)
                } catch (e: Exception) {
                    Timber.e(e)
                    progressVisibility.set(View.GONE)
                    dataListener.onMessage(e.message!!)
                    return@launch
                }
                // saving comic info for later use as well
                this@ComicViewModel.comicInfo = comicInfo
            }
            job.join()
        }
        return comicInfo
    }


    override fun onDestroy() {
        // canceled api call coroutine as Fragment is destroyed
        parentJob.cancel()
    }

    interface DataListener {
        fun onMessage(message: String)
    }
}

