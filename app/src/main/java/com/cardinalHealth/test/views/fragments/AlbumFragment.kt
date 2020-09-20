package com.cardinalHealth.test.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cardinalHealth.test.R
import com.cardinalHealth.test.constants.Constants
import com.cardinalHealth.test.constants.Constants.PHOTO_FRAGMENT
import com.cardinalHealth.test.constants.Constants.userId
import com.cardinalHealth.test.customClasses.ItemDecorationAlbumColumns
import com.cardinalHealth.test.extensions.gone
import com.cardinalHealth.test.extensions.visible
import com.cardinalHealth.test.views.activity.MainActivity
import com.cardinalHealth.test.views.adapters.AlbumAdapter
import kotlinx.android.synthetic.main.layout_album_fragment.*

class AlbumFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_album_fragment,container,false)
    }

    private val adapter by lazy { AlbumAdapter(this,::onRowClick) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setLiveListeners()
        (requireActivity() as MainActivity).viewModel.getAlbums(userId)
    }

    private fun init(){
        (requireActivity() as MainActivity).viewModel.setTitle(resources.getString(R.string.albums))
        val layoutManager = GridLayoutManager(context,resources.getInteger(R.integer.numColumns))
        rvAlbums.apply {
            this.layoutManager=layoutManager
            setHasFixedSize(false)
            addItemDecoration(ItemDecorationAlbumColumns(resources.getDimensionPixelSize(R.dimen.dimen_10dp),resources.getInteger(R.integer.numColumns)))
            this.adapter=this@AlbumFragment.adapter
        }
    }
    private fun setLiveListeners(){
        (requireActivity() as MainActivity).viewModel.albumListLiveData.observe(viewLifecycleOwner,
            Observer {
                if(it.isEmpty()){
                    rvAlbums.gone()
                }
                else{
                    rvAlbums.visible()
                    adapter.updateData(it)
                }

            })
    }

    private fun onRowClick(albumId:Int){
        val galleryFragment = GalleryFragment()
        val bundle = Bundle()
        bundle.putInt(Constants.ALBUM_ID,albumId)
        galleryFragment.arguments=bundle
        (requireActivity() as MainActivity).apply {
            viewModel.hideSearch()
            performFragmentTransaction(galleryFragment,PHOTO_FRAGMENT)
        }
    }
}