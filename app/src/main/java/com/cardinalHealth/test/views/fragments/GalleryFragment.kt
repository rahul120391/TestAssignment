package com.cardinalHealth.test.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cardinalHealth.test.R
import com.cardinalHealth.test.constants.Constants.ALBUM_ID
import com.cardinalHealth.test.customClasses.ItemDecorationAlbumColumns
import com.cardinalHealth.test.views.activity.MainActivity
import com.cardinalHealth.test.views.adapters.AlbumAdapter
import com.cardinalHealth.test.views.adapters.GalleryAdapter
import kotlinx.android.synthetic.main.layout_album_fragment.*
import kotlinx.android.synthetic.main.layout_gallery_fragment.*

class GalleryFragment:Fragment() {

    private var albumId = -1
    private val adapter by lazy { GalleryAdapter(this) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_gallery_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumId = arguments?.getInt(ALBUM_ID)?:-1
        init()
        setLiveListeners()
        (requireActivity() as MainActivity).viewModel.getAllPhotos(albumId)
    }

    private fun init(){
        (requireActivity() as MainActivity).viewModel.apply {
            setTitle(resources.getString(R.string.gallery))
            setSearchIconVisibility(View.GONE)
        }
        val layoutManager = GridLayoutManager(context,resources.getInteger(R.integer.numColumns))
        rvPhotos.apply {
            this.layoutManager=layoutManager
            setHasFixedSize(false)
            addItemDecoration(ItemDecorationAlbumColumns(resources.getDimensionPixelSize(R.dimen.dimen_10dp),resources.getInteger(R.integer.numColumns)))
            this.adapter=this@GalleryFragment.adapter
        }
    }

    private fun setLiveListeners(){
        (requireActivity() as MainActivity).viewModel.photoListLiveData.observe(viewLifecycleOwner,
            Observer {
                  adapter.updateData(it)
            })
    }
}