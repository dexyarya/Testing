package com.dicoding.favoritefootballmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.favoritefootballmatch.R
import com.dicoding.favoritefootballmatch.favorites.MatchFavorites
import kotlinx.android.synthetic.main.activity_match.view.*

class MatchFavAdapter(private val context: Context, private val eventFavorite: List<MatchFavorites>, private val listener: (MatchFavorites) -> Unit) : RecyclerView.Adapter<MatchFavAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.activity_match,
                    parent,
                    false)
    )


    override fun getItemCount(): Int = eventFavorite.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(eventFavorite[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(eventFavorite: MatchFavorites, listener: (MatchFavorites) -> Unit) {

            itemView.dateMatch.text = eventFavorite.dateEvent
            itemView.homeTeam.text = eventFavorite.strHomeTeam
            itemView.homeScore.text = eventFavorite.intHomeScore
            itemView.awayTeam.text = eventFavorite.strAwayTeam
            itemView.awayScore.text = eventFavorite.intAwayScore
            itemView.setOnClickListener {
                listener(eventFavorite)
            }

        }
    }
}