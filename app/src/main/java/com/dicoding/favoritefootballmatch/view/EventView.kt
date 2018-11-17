package com.dicoding.favoritefootballmatch.view

import com.dicoding.favoritefootballmatch.other.Event

interface EventView : BaseView {
    fun showEventList(events: List<Event>)
}