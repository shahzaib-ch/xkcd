package my.test.xkcd.ui.activities

import android.content.Intent
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
import my.test.xkcd.utils.AppBundles
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
        viewModel = HomeViewModel(this, this)
        binding.viewModel = viewModel

        initComponents()
        initViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val comicId = try {
                    Integer.parseInt(query!!)
                } catch (e: Exception) {
                    Timber.d("text search....")
                    searchItem.collapseActionView()
                    viewModel.comicSearchByText(query!!)
                    return false
                }
                // updating current fragment
                searchItem.collapseActionView()
                binding.viewPager.currentItem = comicId - 1
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
                startExplanationActivity()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    // initializing all data and components on start of activity
    private fun initComponents() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun startExplanationActivity() {
        if (viewModel.comicInfo == null)
            return

        val intent = Intent(this, ExplainActivity::class.java)
        intent.putExtra(AppBundles.COMIC_ID.key, viewModel.comicInfo?.num)
        intent.putExtra(AppBundles.COMIC_TITLE.key, viewModel.comicInfo?.title)
        startActivity(intent)
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

    override fun onComicSearchSuccess(comicId: Int) {
        binding.viewPager.currentItem = comicId - 1
    }

    override fun onMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onUpdate(comicInfo: ComicResponse) {
        binding.tvTitle.text = comicInfo.title
        binding.tvComicId.text = comicInfo.num.toString()
        viewModel.comicInfo = comicInfo
    }
}