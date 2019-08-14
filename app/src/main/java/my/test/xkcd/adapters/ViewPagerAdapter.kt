package my.test.xkcd.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import my.test.xkcd.ui.fragments.ComicViewFragment
import my.test.xkcd.utils.AppBundles
import my.test.xkcd.utils.AppConstants

/**
 * Created by Shahzaib on 8/14/2019.
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var currentComicId = 0

    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt(AppBundles.COMIC_ID.key, ++currentComicId)
        val comicViewFragment = ComicViewFragment()
        comicViewFragment.arguments = bundle
        return comicViewFragment
    }

    override fun getCount(): Int {
        return AppConstants.MAX_NUMBER_OF_COMICS
    }
}