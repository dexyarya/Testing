package com.dicoding.favoritefootballmatch.view

import com.dicoding.favoritefootballmatch.other.Event
import com.dicoding.favoritefootballmatch.other.Team

interface EventDetailView : BaseView {
    fun showEventDetail(event: Event)
    fun showTeamEmblem(team: Team)
}