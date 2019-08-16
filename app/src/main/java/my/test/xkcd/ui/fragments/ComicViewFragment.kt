package my.test.xkcd.ui.fragments

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.databinding.FragmentComicViewBinding
import my.test.xkcd.utils.AppBundles
import my.test.xkcd.utils.AppConstants
import my.test.xkcd.utils.AppUtil
import my.test.xkcd.utils.ShareEvent
import my.test.xkcd.viewmodel.ComicViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File


/**
 * Created by Shahzaib on 8/14/2019.
 * This fragment is responsible for showing comics and allow zoom comics
 */
class ComicViewFragment : Fragment(),
    ComicViewModel.DataListener {

    private lateinit var binding: FragmentComicViewBinding
    private lateinit var viewModel: ComicViewModel
    private var currentComicId: Int = 1

    companion object {
        // this methods to return new instance of fragment
        fun newInstance(args: Bundle): ComicViewFragment {
            val fragment = ComicViewFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, my.test.xkcd.R.layout.fragment_comic_view, container, false)
        viewModel = ComicViewModel(activity, this)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // initially loading first comic
        viewModel.getComicInfo(currentComicId.toString())
    }


    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        EventBus.getDefault().post(ShareEvent(AppBundles.UPDATE_HOME, viewModel.comicInfo))
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    private fun initComponents() {
        currentComicId = arguments!!.getInt(AppBundles.COMIC_ID.key)
        // increment as we first index is zero
        ++currentComicId
    }

    // this event bus method is being used to receive event, (not a unused method)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ShareEvent) {
        // performing sharing of comic
        if (event.eventType == AppBundles.SHARE) {
            val bitMap = binding.photoView.drawable.toBitmap()
            val comicFile = AppUtil.saveBitmapToFile(
                File(context!!.filesDir, AppConstants.COMIC_SHARING_FOLDER_NAME),
                AppConstants.COMIC_SHARING_FILE_NAME,
                bitMap,
                Bitmap.CompressFormat.PNG, 100
            )

            val uri = FileProvider.getUriForFile(context!!, AppConstants.COMIC_SHARE_AUTHORITY, comicFile!!)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

            }
        }
    }


    // loading comic image to image view
    @SuppressLint("CheckResult")
    override fun loadComicImage(comicInfo: ComicResponse) {
        Glide.with(this).asBitmap().load(comicInfo.img)
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewModel.progressVisibility.set(View.GONE)
                    return false
                }
            }).diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.photoView)
        if (viewModel.comicInfo != null && isResumed) {
            EventBus.getDefault().post(ShareEvent(AppBundles.UPDATE_HOME, viewModel.comicInfo))
        }

    }

    override fun onMessage(message: String) {
        if(isResumed)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}