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
 * this adapter is used to manage fragments in view pager
 */
class ViewPagerAdapter(fm: FragmentManager)
    : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putInt(AppBundles.COMIC_ID.key, position)
        return ComicViewFragment.newInstance(bundle)
    }

    // -1 because index starts from 0
    override fun getCount(): Int {
        return AppConstants.MAX_NUMBER_OF_COMICS
    }
}