package com.dicoding.aplikasidicodingevent.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.response.ListEventsItem

class EventAdapter(
    private val context: Context,
    private var events: List<ListEventsItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.itemText.text = event.name

        Log.d("EventAdapter", "Loading image from URL: ${event.mediaCover}")

        Glide.with(context)
            .load(event.mediaCover)
            .placeholder(R.drawable.baseline_image_24)
            .into(holder.itemImage)

        holder.itemView.setOnClickListener {
            event.id?.let { id ->
                onItemClick(id)
            }
        }
    }

    override fun getItemCount(): Int = events.size

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_Image)
        val itemText: TextView = itemView.findViewById(R.id.item_Text)
    }

    fun updateEvents(newEvents: List<ListEventsItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}