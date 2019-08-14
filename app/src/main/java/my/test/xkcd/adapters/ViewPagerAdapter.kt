package my.test.xkcd.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import my.test.xkcd.ui.fragments.ComicViewFragment

/**
 * Created by Shahzaib on 8/14/2019.
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return ComicViewFragment()
    }

    override fun getCount(): Int {
        return 5
    }
}