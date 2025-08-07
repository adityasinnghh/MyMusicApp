package com.example.musicapp

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(
    private val context: Activity,
    private val datalist: List<Data> // ✅ Non-null
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentData = datalist[position]

        holder.title.text = currentData.title
        Picasso.get().load(currentData.album.cover).into(holder.image)

        holder.play.setOnClickListener {
            mediaPlayer?.release() // release previous instance
            mediaPlayer = MediaPlayer.create(context, currentData.preview.toUri())
            mediaPlayer?.start()

            mediaPlayer?.setOnCompletionListener {
                it.release()
                mediaPlayer = null
            }
        }

        holder.pause.setOnClickListener {
            mediaPlayer?.pause()
        }
    }

    override fun getItemCount(): Int = datalist.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.musicImage)
        val title: TextView = itemView.findViewById(R.id.musicTitle)
        val play: ImageButton = itemView.findViewById(R.id.btnPlay)
        val pause: ImageButton = itemView.findViewById(R.id.btnPause)
    }
}
