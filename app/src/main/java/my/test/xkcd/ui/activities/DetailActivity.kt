package my.test.xkcd.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import my.test.xkcd.R
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.databinding.ActivityDetailBinding
import my.test.xkcd.utils.AppBundles
import my.test.xkcd.viewmodel.DetailViewModel

/**
 * This activity shows detail of a comic
 */
class DetailActivity : XkcdActivity(), DetailViewModel.DataListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = DetailViewModel(this, this)
        binding.viewModel = viewModel

        initComponents()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    // initializing all data and components on start of activity
    private fun initComponents() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // show comic details
        val comicInfo = intent.getParcelableExtra<ComicResponse?>(AppBundles.COMIC.key)
        binding.tvComicTitle.text = comicInfo?.title
        binding.tvDate.text = getString(R.string.date_template, comicInfo?.day, comicInfo?.month, comicInfo?.year)
        binding.tvTranscript.text = comicInfo?.transcript
    }


    override fun onMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}