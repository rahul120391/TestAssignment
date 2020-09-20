package com.cardinalHealth.test.viewModel

import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.cardinalHealth.test.base.BaseViewModel
import com.cardinalHealth.test.constants.Constants.ERROR_WHILE_FETCHING
import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.network.baseusecase.UseCase
import com.cardinalHealth.test.network.baseusecase.UseCaseHandler
import com.cardinalHealth.test.usecase.GetAllAlbums
import com.cardinalHealth.test.usecase.GetAllPhotos
import com.cardinalHealth.test.utils.Utility
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

@ActivityScoped
class MainActivityViewModel(private val useCaseHandler: UseCaseHandler,
                            private val getAllAlbums: GetAllAlbums,
                            private val getAllPhotos: GetAllPhotos,
                            private val utility: Utility):BaseViewModel(){


    val titleVisibility = MutableLiveData(View.VISIBLE)
    val titleText = MutableLiveData("")
    val searchIconVisibility = MutableLiveData(View.VISIBLE)
    val searchFieldVisibility = MutableLiveData(View.GONE)
    val progressVisibility = MutableLiveData(View.VISIBLE)
    val noResultFoundVisibility = MutableLiveData(View.GONE)
    var searchFieldText = MutableLiveData<String>()
    val albumListLiveData = MutableLiveData<ArrayList<Album>>()
    val photoListLiveData = MutableLiveData<ArrayList<Photo>>()
    val onBackButtonPress: PublishSubject<Unit> = PublishSubject.create<Unit>()
    private var originalAlbumList = ArrayList<Album>()
    
    fun getAlbums(userId:Int){
            if(progressVisibility.value==View.GONE){
                progressVisibility.value=View.VISIBLE
            }
            val requestValues = GetAllAlbums.RequestValues(userId)
            useCaseHandler.execute(getAllAlbums,requestValues, object : UseCase.UseCaseCallback<GetAllAlbums.ResponseValues>
            {

                override fun onSuccess(response: GetAllAlbums.ResponseValues) {
                    originalAlbumList=response.list
                    moveAlbumListResponseToMainThread(response.list)
                }

                override fun onError(code:Int) {
                    moveErrorToMainThread(code)
                }
            })

            android.os.Handler(Looper.getMainLooper()).postDelayed({
                compositeDisposable.add(getAllAlbums.getDisposable())
            },1000)
    }

    fun getAllPhotos(albumId:Int){
            if(progressVisibility.value==View.GONE){
                progressVisibility.value=View.VISIBLE
            }
            val requestValues = GetAllPhotos.RequestValues(albumId)
            useCaseHandler.execute(getAllPhotos,requestValues, object : UseCase.UseCaseCallback<GetAllPhotos.ResponseValues>
            {

                override fun onSuccess(response: GetAllPhotos.ResponseValues) {
                    movePhotosListResponseToMainThread(response.list)
                }

                override fun onError(code:Int) {
                    moveErrorToMainThread(code)
                }
            })
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                compositeDisposable.add(getAllPhotos.provideDisposable())
            },1000)

    }
    fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        searchFieldText.value = s.toString()
        if (s.toString().isNotBlank()) {
            performSearch(s.toString())
        } else {
            moveAlbumListResponseToMainThread(originalAlbumList)
        }
    }

    companion object{
        @JvmStatic
        @BindingAdapter("text")
        fun setTitleText(textView:AppCompatTextView?,title:String?){
            textView?.text= title
        }
    }

    private fun performSearch(query:String){
        val disposable = Flowable.just(query).debounce(300,TimeUnit.MILLISECONDS)
            .distinctUntilChanged().switchMap {
                val list=originalAlbumList.filter { it.title.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault())) }
                Flowable.just(list)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if(it.isEmpty()){
                    noResultFoundVisibility.value=View.VISIBLE
                    albumListLiveData.value= ArrayList()
                }
                else{
                    noResultFoundVisibility.value=View.GONE
                    albumListLiveData.value=it.toCollection(ArrayList())
                }
            }
        compositeDisposable.add(disposable)
    }

    fun onSearchClick(view:View){
        showHideSearch()
    }

    private fun showHideSearch(){
        if(originalAlbumList.isNotEmpty()){
            searchFieldVisibility.value=if(searchFieldVisibility.value==View.GONE){
                titleVisibility.value=View.GONE
                View.VISIBLE
            }
            else{
                titleVisibility.value=View.VISIBLE
                searchFieldText.value = ""
                View.GONE
            }
        }
    }

    fun hideSearch(){
        if(searchFieldVisibility.value==View.VISIBLE){
            searchFieldVisibility.value=View.GONE
            searchFieldText.value = ""
            titleVisibility.value=View.VISIBLE
        }
    }

    private fun moveAlbumListResponseToMainThread(list:ArrayList<Album>){
        CoroutineScope(Dispatchers.Main).launch{
            progressVisibility.value=View.GONE
            if(list.isNotEmpty()){
                albumListLiveData.value=list
                noResultFoundVisibility.value=View.GONE
            }
            else{
                noResultFoundVisibility.value=View.VISIBLE
            }
        }
    }

    private fun movePhotosListResponseToMainThread(list:ArrayList<Photo>){
        CoroutineScope(Dispatchers.Main).launch{
            progressVisibility.value=View.GONE
            if(list.isNotEmpty()){
                photoListLiveData.value=list
            }
            else{
                noResultFoundVisibility.value=View.VISIBLE
            }
        }
    }

    private fun moveErrorToMainThread(code:Int){
        CoroutineScope(Dispatchers.Main).launch{
            progressVisibility.value=View.GONE
            if(code==ERROR_WHILE_FETCHING){
                utility.showErrorMessage()
            }
            else{
                utility.showNoInternetError()
            }

        }
    }

    fun onBackPress(view:View){
        onBackButtonPress.onNext(Unit)
    }

    fun setTitle(title: String){
        titleText.value=title
    }

    fun setSearchIconVisibility(showHide:Int){
        searchIconVisibility.value=showHide
    }
}