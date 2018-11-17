package com.dicoding.favoritefootballmatch.view

import com.dicoding.favoritefootballmatch.TestContextProvider
import com.dicoding.favoritefootballmatch.api.ApiRepository
import com.dicoding.favoritefootballmatch.api.TheSportDBApi
import com.dicoding.favoritefootballmatch.other.Event
import com.dicoding.favoritefootballmatch.other.EventResponse
import com.dicoding.favoritefootballmatch.other.Team
import com.dicoding.favoritefootballmatch.other.TeamResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PresenterTest {
    @Mock
    private lateinit var eventView: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: Presenter<EventView>

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        presenter = Presenter(eventView, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextMatchEvent() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val league = "English Premiere League"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatchEvent(league)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextMatchEvent(league)

        verify(eventView).showLoading()
        verify(eventView).showEventList(event)
        verify(eventView).hideLoading()

    }

    @Test
    fun getPastMatchEvent() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        val league = "English Premiere League"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastMatchEvent(league)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getPastMatchEvent(league)

        verify(eventView).showLoading()
        verify(eventView).showEventList(event)
        verify(eventView).hideLoading()
    }
}