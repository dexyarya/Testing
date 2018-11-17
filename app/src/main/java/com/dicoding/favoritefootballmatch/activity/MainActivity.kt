package com.dicoding.favoritefootballmatch.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.favoritefootballmatch.fragment.NextMatchFragment
import com.dicoding.favoritefootballmatch.fragment.PastMatchFragment
import com.dicoding.favoritefootballmatch.R
import com.dicoding.favoritefootballmatch.fragment.MatchFavFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Match Info"
        supportFragmentManager.beginTransaction().replace(R.id.mainFrame, PastMatchFragment()).commit()

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.prevMatch -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, PastMatchFragment()).commit()
                    true
                }
                R.id.nextMatch -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, NextMatchFragment()).commit()
                    true
                }
                R.id.favorites -> {
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrame, MatchFavFragment()).commit()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
