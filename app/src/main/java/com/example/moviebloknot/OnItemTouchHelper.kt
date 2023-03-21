package com.example.moviebloknot

interface OnItemTouchHelper {
    fun onItemMove(fromPosition:Int,toPosition:Int)

    fun onItemDismiss(position: Int)
}