package com.cardinalHealth.test.viewModel

import androidx.lifecycle.MutableLiveData
import com.cardinalHealth.test.dataModel.Album

class AlbumViewModel(album: Album) {

    val title = MutableLiveData(album.title)
    val id = MutableLiveData(album.id.toString())
}