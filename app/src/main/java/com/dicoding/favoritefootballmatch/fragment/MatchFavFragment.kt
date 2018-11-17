package com.dicoding.favoritefootballmatch.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.favoritefootballmatch.R
import com.dicoding.favoritefootballmatch.activity.DetailActivity
import com.dicoding.favoritefootballmatch.adapter.MatchFavAdapter
import com.dicoding.favoritefootballmatch.database.MatchDatabase
import com.dicoding.favoritefootballmatch.favorites.MatchFavorites
import com.dicoding.favoritefootballmatch.favorites.MatchTable
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class MatchFavFragment : Fragment() {
    private lateinit var eventAdapter: MatchFavAdapter
    private var eventFavorite: MutableList<MatchFavorites> = mutableListOf()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        eventAdapter = MatchFavAdapter(activity!!.applicationContext, eventFavorite) {
            startActivity<DetailActivity>("eventId" to it.idEvent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.recycler_view

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = eventAdapter

        swipeRefreshLayout = view.swipe_refresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            getFavoriteEvent()
        }
        getFavoriteEvent()
        return view
    }

    private fun getFavoriteEvent() {
        MatchDatabase.getInstance(activity!!.applicationContext).use {
            val queryResult = select(MatchTable.TABLE_NAME)
            val favoriteEvent = queryResult.parseList(classParser<MatchFavorites>())
            eventFavorite.clear()
            eventFavorite.addAll(favoriteEvent)
            eventAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}