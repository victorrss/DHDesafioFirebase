package com.digitalhouse.desafio4firebase.game.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.digitalhouse.desafio4firebase.game.entities.Game
import com.digitalhouse.desafio4firebase.game.service.cr

class HomeViewModel() : ViewModel() {
    var listGames = MutableLiveData<ArrayList<Game>>()

    fun getListGames() {
        var list = ArrayList<Game>()
        cr.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        list.add(
                            Game(
                                document.data["name"].toString(),
                                document.data["year"].toString(),
                                document.data["description"].toString(),
                                document.data["url"].toString()
                            )
                        )
                    }
                    listGames.value = list
                } else {
                    Log.w("HomeViewModel", "Error ", task.exception)
                }
            }
    }
}

