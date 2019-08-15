package my.test.xkcd.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import my.test.xkcd.R
import my.test.xkcd.databinding.ActivityExplainBinding
import my.test.xkcd.utils.AppBundles
import my.test.xkcd.utils.AppWebServices
import my.test.xkcd.viewmodel.ExplainViewModel


class ExplainActivity : AppCompatActivity(), ExplainViewModel.DataListener {

    private lateinit var binding: ActivityExplainBinding
    private lateinit var viewModel: ExplainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_explain)
        viewModel = ExplainViewModel(this, this)
        binding.viewModel = viewModel

        initComponents()
        initWebView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    // initializing all data and components on start of activity
    private fun initComponents() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // load url and show title of comic
        val comicId = intent.getIntExtra(AppBundles.COMIC_ID.key, 1)
        binding.webView.loadUrl(AppWebServices.COMIC_EXPLANATION_BASE_URL + comicId)
    }

    private fun initWebView() {
        binding.webView.setInitialScale(90)
        binding.webView.settings.builtInZoomControls = true
        binding.webView.settings.displayZoomControls = false
        binding.webView.settings.useWideViewPort = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // hiding progress
                viewModel.progressVisibility.set(View.GONE)
            }
        }
        binding.webView.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    override fun onMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}