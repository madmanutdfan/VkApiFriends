package com.example.vkapifriends.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.vkapifriends.models.FriendModel
import com.example.vkapifriends.models.UserModel

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FriendsView: MvpView{
    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupFriendsList(friendsList: ArrayList<FriendModel>, user: UserModel)
    fun startLoading()
    fun endLoading()
}