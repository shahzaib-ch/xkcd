package my.test.xkcd.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import my.test.xkcd.R
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.databinding.FragmentComicViewBinding
import my.test.xkcd.viewmodel.ComicViewModel

/**
 * Created by Shahzaib on 8/14/2019.
 */
class ComicViewFragment : Fragment(), ComicViewModel.DataListener {

    private lateinit var binding: FragmentComicViewBinding
    private lateinit var viewModel: ComicViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comic_view, container, false)
        viewModel = ComicViewModel(activity, binding, this)
        binding.viewModel = viewModel
        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initWebView()
        // initially loading first comic
        viewModel.getComicInfo(viewModel.currentComicId.toString())
    }

    private fun initWebView(){
        binding.webView.setInitialScale(50)
    }

    override fun loadComicImageInWebView(comicInfo: ComicResponse) {
        binding.webView.loadUrl(comicInfo.img)
    }

    override fun onMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}