package com.cardinalHealth.test.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.cardinalHealth.test.R


abstract class BaseActivity<T : ViewDataBinding,V : BaseViewModel>:AppCompatActivity() {

    var viewDataBinding: T? = null
    private var baseViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int
    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int
    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        setUpTransparentStatusBar()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }


    private fun setUpTransparentStatusBar() {
        if(Build.VERSION.SDK_INT<30){
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                statusBarColor= Color.TRANSPARENT
            }
        }
        else{
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if (controller != null) {
                controller.hide(WindowInsets.Type.statusBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.baseViewModel = if (baseViewModel == null) viewModel else baseViewModel
        viewDataBinding?.setVariable(bindingVariable, baseViewModel)
        viewDataBinding?.executePendingBindings()
    }

    fun performFragmentTransaction(fragment: Fragment, tag:String){
          supportFragmentManager.beginTransaction().
                                 replace(R.id.fragmentContainerView,fragment,tag).
                                 addToBackStack(null).
                                 commit()
    }

    fun handleBackStack(){
        if(supportFragmentManager.backStackEntryCount>1){
            supportFragmentManager.popBackStack()
        }
        else{
            finish()
        }
    }

}