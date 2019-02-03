package com.example.vkapifriends.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vkapifriends.R
import com.example.vkapifriends.models.FriendModel
import com.example.vkapifriends.models.UserModel
import com.example.vkapifriends.providers.FriendsProvider
import com.example.vkapifriends.views.FriendsView

@InjectViewState
class FriendsPresenter: MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        //FriendsProvider(presenter = this).testLoadFriends(hasFriends = true)
        FriendsProvider(presenter = this).loadFriends()
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>, user: UserModel){
        viewState.endLoading()
        if(friendsList.size == 0){
            viewState.setupEmptyList()
            viewState.showError(textResource = R.string.friends_no_items)
        } else{
            viewState.setupFriendsList(friendsList = friendsList, user = user)
        }
    }

    fun showError(textResource: Int) {
        viewState.showError(textResource = textResource)
    }
}