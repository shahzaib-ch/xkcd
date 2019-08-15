package my.test.xkcd.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.databinding.FragmentComicViewBinding
import my.test.xkcd.utils.AppBundles
import my.test.xkcd.viewmodel.ComicViewModel


/**
 * Created by Shahzaib on 8/14/2019.
 */
class ComicViewFragment(private val dataListener: ComicViewModel.HomeActivityDataListener) : Fragment(),
    ComicViewModel.DataListener {

    private lateinit var binding: FragmentComicViewBinding
    private lateinit var viewModel: ComicViewModel
    private var currentComicId: Int = 1

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
        // update data on Home activity
        if (viewModel.comicInfo != null) {
            dataListener.onUpdate(viewModel.comicInfo!!)
        }
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

    override fun loadComicImage(comicInfo: ComicResponse) {
        Glide.with(this).load(comicInfo.img)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean = false

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    viewModel.progressVisibility.set(View.GONE)
                    return false
                }
            }).into(binding.photoView)
    }

    override fun onMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}