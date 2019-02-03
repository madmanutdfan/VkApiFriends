package com.example.vkapifriends.providers

import com.example.vkapifriends.presenters.FriendsPresenter
import android.os.Handler
import android.util.Log
import com.example.vkapifriends.R
import com.example.vkapifriends.models.FriendModel
import com.example.vkapifriends.models.UserModel
import com.google.gson.JsonParser
import com.vk.sdk.api.*

class FriendsProvider(var presenter: FriendsPresenter) {
//    private val TAG: String = FriendsProvider::class.java.simpleName
    private var user: UserModel = UserModel("", "")

    fun testLoadFriends(hasFriends: Boolean) {
        Handler().postDelayed({
            val friendsList: ArrayList<FriendModel> = ArrayList()
            if(hasFriends){
                val friend1 = FriendModel(name = "Ivan", surname = "Ivanov123",
                    avatar = "https://vk.com/madmufan?z=photo188887249_456239759%2Falbum188887249_0%2Frev")
                val friend2 = FriendModel(name = "Ivan2", surname = "Ivano1v",
                    avatar = "https://avatars.mds.yandex.net/get-kino-vod-films-gallery/34144/2a00000151e8471d29dedc24655d6c4f8166/280x178_2")
                val friend3 = FriendModel(name = "Ivan23213", surname = "Ivanov1231",
                    avatar = "https://avatars.mds.yandex.net/get-kino-vod-films-gallery/34144/2a00000151e8471d29dedc24655d6c4f8166/280x178_2")

                friendsList.add(friend1)
                friendsList.add(friend2)
                friendsList.add(friend3)
            }

            presenter.friendsLoaded(friendsList = friendsList, user = user)
        },1000)
    }
    fun loadFriends() {
        var jsonParser: JsonParser
        var friendsList: ArrayList<FriendModel> = ArrayList()
        var user: UserModel = UserModel("", "")

        val requestUser = VKApi.users().get(VKParameters.from("users_ids", "fields", "photo_100"))
        requestUser.executeWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                jsonParser = JsonParser()
                var parsedJson = jsonParser.parse(response!!.json.toString()).asJsonObject

                parsedJson.getAsJsonArray("response").forEach {
                    user = UserModel(
                        name = it.asJsonObject.get("first_name").asString,
                        surname = it.asJsonObject.get("last_name").asString
                    )
                }
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.friends_error_loading)
            }
        })

        val request = VKApi.friends().get(VKParameters.from("order", "random", VKApiConst.COUNT, 5, VKApiConst.FIELDS, "photo_100"))
        request.executeWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)

                jsonParser = JsonParser()
                var parsedJson = jsonParser.parse(response!!.json.toString()).asJsonObject
//                Log.e(TAG, "parsed - ${parsedJson}")
                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach{
//                    Log.e(TAG,"element - $it")
                    var friend = FriendModel(
                        name = it.asJsonObject.get("first_name").asString,
                        surname = it.asJsonObject.get("last_name").asString,
                        avatar = it.asJsonObject.get("photo_100").asString)
                    friendsList.add(friend)
                }
                presenter.friendsLoaded(friendsList, user)
//                Log.e(TAG, "response ${response!!.json}")
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.friends_error_loading)
            }
        })
    }
}