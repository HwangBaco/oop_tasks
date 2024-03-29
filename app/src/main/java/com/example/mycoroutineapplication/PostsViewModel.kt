package com.example.mycoroutineapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class Post(val title: String, val body: String)

class PostsViewModel: ViewModel() {
    private val repository = PostsRepository()

    private val _posts = MutableLiveData<ArrayList<Post>>()
    val posts : LiveData<ArrayList<Post>> = _posts

    fun retrievePosts() {
        viewModelScope.launch {
            repository.readPosts()?.let { jsonPosts ->
                val pList = ArrayList<Post>()
                for( idx in 0 until jsonPosts.length() ) {
                    jsonPosts.getJSONObject(idx)?.let { obj ->
                        pList += Post(obj.getString("title"), obj.getString("body"))
                    }
                }
                _posts.value = pList
            }
        }
        // launch와 withContext의 차이를 이해할 것
    }
}