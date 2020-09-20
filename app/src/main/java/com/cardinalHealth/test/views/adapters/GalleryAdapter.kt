package com.cardinalHealth.test.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.databinding.LayoutGalleryRowBinding
import com.cardinalHealth.test.viewModel.GalleryViewModel

class GalleryAdapter(private val lifeCycleOwner: LifecycleOwner):RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private val photoList = ArrayList<Photo>()
    override fun getItemCount(): Int = photoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutGalleryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         holder.bind(photoList[position])
    }


    inner class ViewHolder(private val binding:LayoutGalleryRowBinding):RecyclerView.ViewHolder(binding.root){
        private var galleryViewModel:GalleryViewModel?=null

        fun bind(photo: Photo){
            galleryViewModel=GalleryViewModel(photo)
            binding.apply {
                lifecycleOwner=lifeCycleOwner
                viewModel=galleryViewModel
                executePendingBindings()
            }
        }
    }

    fun updateData(photoList:ArrayList<Photo>){
        this.photoList.apply {
            clear()
            addAll(photoList)
            notifyDataSetChanged()
        }
    }
}