package my.test.xkcd.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import my.test.xkcd.R
import my.test.xkcd.adapters.ViewPagerAdapter
import my.test.xkcd.data.model.comic.ComicResponse
import my.test.xkcd.databinding.ActivityHomeBinding
import my.test.xkcd.utils.AppConstants
import my.test.xkcd.viewmodel.ComicViewModel
import my.test.xkcd.viewmodel.HomeViewModel
import timber.log.Timber


class HomeActivity : AppCompatActivity(), HomeViewModel.DataListener, ComicViewModel.HomeActivityDataListener {

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val comicId = try {
                    Integer.parseInt(query!!)
                } catch (e: Exception) {
                    Timber.e(e)
                    onMessage("Not a number search")
                    return false
                }
                binding.viewPager.currentItem = comicId
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // not needed
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_detail -> {

            }

            R.id.action_explain -> {

            }

        }
        return super.onOptionsItemSelected(item)
    }

    // initializing all data and components on start of activity
    private fun initComponents() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    // initializing view pager
    private fun initViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPagerAdapter
    }

    override fun onNavigationToFirstComic() {
        binding.viewPager.currentItem = 0
    }

    override fun onNavigationToLastComic() {
        binding.viewPager.currentItem = AppConstants.MAX_NUMBER_OF_COMICS - 1
    }

    override fun onNavigationToNextComic() {
        binding.viewPager.arrowScroll(View.FOCUS_RIGHT)
    }

    override fun onNavigationToPreviousComic() {
        binding.viewPager.arrowScroll(View.FOCUS_LEFT)
    }

    override fun onMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onUpdate(comicInfo: ComicResponse) {
        binding.tvTitle.text = comicInfo.title
        binding.tvComicId.text = comicInfo.num.toString()
    }
}