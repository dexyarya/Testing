package com.dicoding.favoritefootballmatch.api

import com.dicoding.favoritefootballmatch.BuildConfig

object TheSportDBApi {
    fun getPastMatchEvent(idLeague: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + idLeague
    }

    fun getNextMatchEvent(idLeague: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + idLeague
    }

    fun getDetailMatchEvent(idMatchEvent: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + idMatchEvent
    }

    fun getSpecificTeam(searchInput: String?): String {
        return BuildConfig.BASE_URL +
                "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + searchInput
    }
}