package com.dicoding.favoritefootballmatch.activity

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dicoding.favoritefootballmatch.*
import com.dicoding.favoritefootballmatch.api.ApiRepository
import com.dicoding.favoritefootballmatch.database.MatchDatabase
import com.dicoding.favoritefootballmatch.favorites.MatchFavorites
import com.dicoding.favoritefootballmatch.favorites.MatchTable
import com.dicoding.favoritefootballmatch.other.Event
import com.dicoding.favoritefootballmatch.other.Team
import com.dicoding.favoritefootballmatch.view.EventDetailView
import com.dicoding.favoritefootballmatch.view.Presenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.onRefresh

class DetailActivity : AppCompatActivity(), EventDetailView, AnkoLogger {


    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var presenter: Presenter<EventDetailView>
    private lateinit var eventId: String
    private lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        eventId = intent.getStringExtra("eventId")
        presenter = Presenter(this, ApiRepository(), Gson())
        presenter.getDetailMatchEvent(eventId)

        swipe_refresh.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light
        )

        swipe_refresh.onRefresh {
            presenter.getDetailMatchEvent(eventId)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun parserGoal(input: String): String {
        return input.replace(";", "\n", false)
    }

    private fun parser(input: String): String {
        return input.replace("; ", "\n", false)
    }

    override fun showEventDetail(event: Event) {
        this.event = event
        date.text = event.dateEvent ?: " "

        team_home.text = event.strHomeTeam ?: " "
        score_home.text = event.intHomeScore ?: " "
        home_formation.text = event.strHomeFormation ?: " "
        home_goal.text = parserGoal(event.strHomeGoalDetails ?: " ")
        home_shot.text = event.intHomeShots?.toString()
        home_goalkeeper.text = parserGoal(event.strHomeLineupGoalkeeper ?: " ")
        home_defense.text = parser(event.strHomeLineupDefense ?: " ")
        home_midlefield.text = parser(event.strHomeLineupMidfield ?: " ")
        home_forward.text = parser(event.strHomeLineupForward ?: " ")
        home_subtituties.text = parser(event.strHomeLineupSubstitutes ?: " ")

        team_away.text = event.strAwayTeam ?: " "
        score_away.text = event.intAwayScore ?: " "
        away_formation.text = event.strAwayFormation ?: " "
        away_goal.text = parserGoal(event.strAwayGoalDetails ?: " ")
        away_shot.text = event.intAwayShots?.toString()
        away_goalkeeper.text = parserGoal(event.strAwayLineupGoalkeeper ?: " ")
        away_defense.text = parser(event.strAwayLineupDefense ?: " ")
        away_midlefield.text = parser(event.strAwayLineupMidfield ?: " ")
        away_forward.text = parser(event.strAwayLineupForward ?: " ")
        away_subtituties.text = parser(event.strAwayLineupSubstitutes ?: " ")

        presenter.getSpecificTeam(event.strHomeTeam)
        presenter.getSpecificTeam(event.strAwayTeam)
        getFavoriteState()

    }

    override fun showTeamEmblem(team: Team) {
        if (team.teamName.equals(event.strHomeTeam)) {
            Glide.with(this).load(team.teamBadge).into(logo_home)
        } else if (team.teamName.equals(event.strAwayTeam)) {
            Glide.with(this).load(team.teamBadge).into(logo_away)
        }
    }

    override fun showLoading() {
        swipe_refresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh.isRefreshing = false
    }

//    DATABASE STUFF

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.fav_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_favourite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
            }
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun getFavoriteState() {
        try {
            MatchDatabase.getInstance(this).use {
                val result = select(MatchTable.TABLE_NAME).whereArgs("${MatchTable.ID_EVENT} = ${event.idEvent}")
                val favorite = result.parseList(classParser<MatchFavorites>())
                if (!favorite.isEmpty()) isFavorite = true
                setFavorite()
            }
        } catch (e: SQLiteConstraintException) {
            info { e.localizedMessage }
        }
    }

    private fun addToFavorite() {
        try {
            val homeScore = event.intHomeScore ?: " "
            val awayScore = event.intAwayScore ?: " "
            MatchDatabase.getInstance(this).use {
                insert(MatchTable.TABLE_NAME,
                        MatchTable.ID_EVENT to event.idEvent,
                        MatchTable.EVENT_DATE to event.dateEvent,
                        MatchTable.HOME_TEAM to event.strHomeTeam,
                        MatchTable.HOME_SCORE to homeScore,
                        MatchTable.AWAY_TEAM to event.strAwayTeam,
                        MatchTable.AWAY_SCORE to awayScore
                )
            }
            snackbar(swipe_refresh, "added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipe_refresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            MatchDatabase.getInstance(this).use {
                delete(MatchTable.TABLE_NAME, "${MatchTable.ID_EVENT} = ${event.idEvent}")
            }
            snackbar(swipe_refresh, "removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipe_refresh, e.localizedMessage).show()
        }
    }
}