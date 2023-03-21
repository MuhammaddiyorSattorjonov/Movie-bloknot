package com.example.moviebloknot

import android.content.Context
import android.content.SharedPreferences
import com.example.moviebloknot.models.MyMovie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MySharedPreferences {
    private const val NAME = "Android10"
    private const val MODE = Context.MODE_PRIVATE

    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var myName:String?
        get() = preferences.getString("count", "")
        set(value) = preferences.edit{
            if (value!=null){
                it.putString("count", value)
            }
        }

    var catchList:ArrayList<MyMovie>
        get() = gsonStringToArray(preferences.getString("keyList","[]")!!)
        set(value) = preferences.edit{
            if (value!=null){
                it.putString("keyList", arrayListToGsonString(value))
            }
        }

    fun arrayListToGsonString(list:ArrayList<MyMovie>):String{
        val gson = Gson()
        return gson.toJson(list)
    }

    fun gsonStringToArray(str:String):ArrayList<MyMovie>{
        val gson = Gson()
        val type = object : TypeToken<ArrayList<MyMovie>>(){}.type
        return gson.fromJson(str,type)
    }
}