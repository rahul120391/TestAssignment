package com.cardinalHealth.test.viewModel

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.cardinalHealth.test.R
import com.cardinalHealth.test.constants.Constants.APP_AGENT
import com.cardinalHealth.test.constants.Constants.USER_AGENT
import com.cardinalHealth.test.dataModel.Photo


class GalleryViewModel(photo: Photo) {

    val title = MutableLiveData(photo.title)
    val imageUrl = MutableLiveData(photo.thumbnailUrl)
    companion object{
        @JvmStatic
        @BindingAdapter("image")
        fun setImage(view:AppCompatImageView,url:String?){
            if(!url.isNullOrBlank()){
                val glideUrl = GlideUrl(
                    url, LazyHeaders.Builder()
                        .addHeader("User-Agent", USER_AGENT)
                        .addHeader("App-agent", APP_AGENT)
                        .build()
                )
                Glide.with(view.context).load(glideUrl).
                placeholder(R.drawable.ic_placeholder).
                error(R.drawable.ic_placeholder).
                into(view)
            }
            else{
                view.setImageResource(R.drawable.ic_placeholder)
            }
        }
    }
}