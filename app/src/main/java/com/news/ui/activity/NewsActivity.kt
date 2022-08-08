package com.news.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.news.R
import com.news.model.News
import com.news.ui.activity.extensions.fragmentTransaction
import com.news.ui.fragment.ListNewsFragment
import com.news.ui.fragment.NewsViewFragment

private const val TAG_FRAGMENT_NEWS_VIEW = "NewsView"

class NewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        configFragmentUsingState(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListNewsFragment -> configNewsList(fragment)
            is NewsViewFragment -> configNewsView(fragment)
        }
    }

    private fun configFragmentUsingState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            openNewsList()
        } else {
            reopenNewsViewFragment()
        }
    }

    private fun reopenNewsViewFragment() {
        supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_NEWS_VIEW)?.let { fragment ->
            val arguments = fragment.arguments
            val newsViewFragment = NewsViewFragment()
            newsViewFragment.arguments = arguments
            removeNewsViewFragment(fragment)
            fragmentTransaction {
                val frameLayout =
                    findViewById<FrameLayout>(R.id.activity_news_container_secondary)
                val container = configContainerNewsViewFragment(frameLayout)
                replace(container, newsViewFragment, TAG_FRAGMENT_NEWS_VIEW)
            }
        }
    }

    private fun removeNewsViewFragment(fragment: Fragment) {
        fragmentTransaction {
            remove(fragment)
        }
        supportFragmentManager.popBackStack()
    }

    private fun configNewsList(fragment: ListNewsFragment) {
        fragment.whenNewsSelect = this::openNewsViewer
        fragment.whenFabSaveNewsClicked = this::openFormInCreateMode
    }

    private fun configNewsView(fragment: NewsViewFragment) {
        fragment.whenFinish = {
            supportFragmentManager.findFragmentByTag(TAG_FRAGMENT_NEWS_VIEW)?.let { fragment ->
                removeNewsViewFragment(fragment)
            }
        }
        fragment.whenSelectEditMenu = this::openFormInEditMode
    }

    private fun openNewsList() {
        fragmentTransaction {
            replace(R.id.activity_news_container_primary, ListNewsFragment())
        }
    }

    private fun openFormInCreateMode() {
        val intent = Intent(this, FormNewsActivity::class.java)
        startActivity(intent)
    }

    private fun openFormInEditMode(news: News) {
        val intent = Intent(this, FormNewsActivity::class.java)
        intent.putExtra(NEWS_KEY, news.id)
        startActivity(intent)
    }

    private fun openNewsViewer(news: News) {
        val newsViewFragment = NewsViewFragment()
        val data = Bundle()
        data.putLong(NEWS_KEY, news.id)
        newsViewFragment.arguments = data
        fragmentTransaction {
            val frameLayout = findViewById<FrameLayout>(R.id.activity_news_container_secondary)
            val container = configContainerNewsViewFragment(frameLayout)
            replace(container, newsViewFragment, TAG_FRAGMENT_NEWS_VIEW)
        }
    }

    private fun FragmentTransaction.configContainerNewsViewFragment(frameLayout: FrameLayout?) =
        if (frameLayout != null) {
            R.id.activity_news_container_secondary
        } else {
            addToBackStack(null)
            R.id.activity_news_container_primary
        }
}