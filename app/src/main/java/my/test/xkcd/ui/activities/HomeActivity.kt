package my.test.xkcd.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import my.test.xkcd.R
import my.test.xkcd.adapters.ViewPagerAdapter
import my.test.xkcd.databinding.ActivityHomeBinding
import my.test.xkcd.utils.AppConstants
import my.test.xkcd.viewmodel.HomeViewModel

class HomeActivity : AppCompatActivity(), HomeViewModel.DataListener {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = HomeViewModel(this, binding, this)
        binding.viewModel = viewModel

        initComponents()
        initViewPager()
    }

    // initializing all data on start of activity
    private fun initComponents() {

    }

    // initializing view pager
    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = viewPagerAdapter
    }

    override fun onNavigationToFirstComic() {
        binding.viewPager.currentItem = 0
    }

    override fun onNavigationToLastComic() {
        binding.viewPager.currentItem = AppConstants.MAX_NUMBER_OF_COMICS
    }

    override fun onNavigationToNextComic() {
        binding.viewPager.arrowScroll(View.FOCUS_RIGHT)
    }

    override fun onNavigationToPreviousComic() {
        binding.viewPager.arrowScroll(View.FOCUS_LEFT)
    }
}