package com.cardinalHealth.test.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.databinding.LayoutAlbumRowBinding
import com.cardinalHealth.test.viewModel.AlbumViewModel

class AlbumAdapter(private val lifeCycleOwner: LifecycleOwner,private val onRowClick:(Int)->Unit):RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    private val albumList = ArrayList<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutAlbumRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = albumList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(albumList[position])
    }

    inner class ViewHolder(private val binding:LayoutAlbumRowBinding):RecyclerView.ViewHolder(binding.root){
        private var albumViewModel:AlbumViewModel?=null

        init {
            binding.root.setOnClickListener {
                if(adapterPosition>=0){
                    onRowClick(albumList[adapterPosition].id)
                }
            }
        }
        fun bind(album: Album){
            albumViewModel = AlbumViewModel(album)
            binding.apply {
                lifecycleOwner=lifeCycleOwner
                viewModel=albumViewModel
                executePendingBindings()
            }
        }
    }

    fun updateData(albumList: ArrayList<Album>){
        this.albumList.apply {
            clear()
            addAll(albumList)
            notifyDataSetChanged()
        }
    }
}