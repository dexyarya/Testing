package com.dicoding.favoritefootballmatch.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.favoritefootballmatch.other.Event
import com.dicoding.favoritefootballmatch.view.EventView
import com.dicoding.favoritefootballmatch.view.Presenter
import com.dicoding.favoritefootballmatch.R
import com.dicoding.favoritefootballmatch.activity.DetailActivity
import com.dicoding.favoritefootballmatch.adapter.MatchAdapter
import com.dicoding.favoritefootballmatch.api.ApiRepository
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_match.view.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class NextMatchFragment : Fragment(), EventView {
    private lateinit var presenter: Presenter<EventView>
    private var eventList: MutableList<Event> = mutableListOf()
    private lateinit var adapter: MatchAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val leagueId = "4328"

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = Presenter(this, ApiRepository(), Gson())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        adapter = MatchAdapter(activity!!.applicationContext, eventList) {
            startActivity<DetailActivity>("eventId" to it.idEvent)
        }
        recyclerView = view.recycler_view

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        swipeRefreshLayout = view.swipe_refresh
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipeRefreshLayout.onRefresh {
            presenter.getNextMatchEvent(leagueId)
        }

        presenter.getNextMatchEvent(leagueId)
        return view
    }

    override fun showEventList(events: List<Event>) {
        eventList.clear()
        eventList.addAll(events)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshLayout.isRefreshing = false
    }


}