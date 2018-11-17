package com.dicoding.favoritefootballmatch.view

import com.dicoding.favoritefootballmatch.other.Team

interface TeamDetailView : BaseView {
    fun showTeamDetail(team: Team)
}