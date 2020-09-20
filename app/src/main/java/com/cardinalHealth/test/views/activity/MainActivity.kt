package com.cardinalHealth.test.views.activity


import android.os.Bundle
import android.view.View
import com.cardinalHealth.test.BR
import com.cardinalHealth.test.R
import com.cardinalHealth.test.base.BaseActivity
import com.cardinalHealth.test.constants.Constants.ALBUM_FRAGMENT
import com.cardinalHealth.test.databinding.ActivityMainBinding
import com.cardinalHealth.test.viewModel.MainActivityViewModel
import com.cardinalHealth.test.views.fragments.AlbumFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, MainActivityViewModel>(){
    override val bindingVariable: Int
        get() = BR._all
    override val layoutId: Int
        get() = R.layout.activity_main

    private var binding:ActivityMainBinding?=null
    @Inject
    override lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=viewDataBinding
        binding?.apply {
            viewModel=this@MainActivity.viewModel
            lifecycleOwner=this@MainActivity
        }
        performFragmentTransaction(AlbumFragment(),ALBUM_FRAGMENT)
        setListeners()
    }

    override fun onBackPressed() {
        handleBackStack()
        settings()
    }

    private fun setListeners(){
        viewModel.onBackButtonPress.subscribe {
            handleBackStack()
            settings()
        }
    }

    private fun settings(){
        val fragment = supportFragmentManager.findFragmentByTag(ALBUM_FRAGMENT)
        fragment?.let {
                viewModel.apply {
                    photoListLiveData.value= ArrayList()
                    setSearchIconVisibility(View.VISIBLE)
            }
        }
    }
}



