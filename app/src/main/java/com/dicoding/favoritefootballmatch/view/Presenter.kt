package com.dicoding.favoritefootballmatch.view

import com.dicoding.favoritefootballmatch.CoroutineContextProvider
import com.google.gson.Gson
import com.dicoding.favoritefootballmatch.api.ApiRepository
import com.dicoding.favoritefootballmatch.api.TheSportDBApi
import com.dicoding.favoritefootballmatch.other.Event
import com.dicoding.favoritefootballmatch.other.EventResponse
import com.dicoding.favoritefootballmatch.other.Team
import com.dicoding.favoritefootballmatch.other.TeamResponse
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class Presenter<in T>(private val view: T,
                      private val repository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailMatchEvent(idMatchEvent: String?) {
        if (view is EventDetailView) {
            view.showLoading()
            async(context.main) {
                val data = bg { gson.fromJson(repository.doRequest(TheSportDBApi.getDetailMatchEvent(idMatchEvent)),
                        EventResponse::class.java).events[0] }

                view.hideLoading()
                view.showEventDetail(data.await())
            }
        }
    }

    fun getNextMatchEvent(idLeague: String?) {
        if (view is EventView) {
            view.showLoading()
            async(context.main) {
                val data = bg { gson.fromJson(repository.doRequest(TheSportDBApi.getNextMatchEvent(idLeague)),
                        EventResponse::class.java).events }

                view.hideLoading()
                view.showEventList(data.await())
            }
        }
    }

    fun getPastMatchEvent(idLeague: String?) {
        if (view is EventView) {
            view.showLoading()
            async(context.main) {
                val data = bg { gson.fromJson(repository.doRequest(TheSportDBApi.getPastMatchEvent(idLeague)),
                        EventResponse::class.java).events }

                view.hideLoading()
                view.showEventList(data.await())
            }
        }
    }

    fun getSpecificTeam(searchInput: String?) {
        if (view is TeamDetailView) {
            view.showLoading()
            async(context.main) {
                val data = bg { gson.fromJson(repository.doRequest(TheSportDBApi.getSpecificTeam(searchInput)),
                        TeamResponse::class.java).teams[0] }

                view.hideLoading()
                view.showTeamDetail(data.await())
            }
        }
        if (view is EventDetailView) {
            view.showLoading()
            async(context.main) {
                val data = bg { gson.fromJson(repository.doRequest(TheSportDBApi.getSpecificTeam(searchInput)),
                        TeamResponse::class.java).teams[0] }

                view.hideLoading()
                view.showTeamEmblem(data.await())
            }
        }

    }
}