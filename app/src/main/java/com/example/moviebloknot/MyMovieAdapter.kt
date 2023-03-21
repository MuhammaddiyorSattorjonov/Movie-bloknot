package com.example.moviebloknot

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebloknot.databinding.ItemRvBinding
import com.example.moviebloknot.models.MyMovie
import java.util.*
import kotlin.collections.ArrayList

class MyMovieAdapter(val context: Context,val list:ArrayList<MyMovie>,val rvClick: RvClick): RecyclerView.Adapter<MyMovieAdapter.Vh>(),OnItemTouchHelper {

    inner class Vh(val itemRvBinding:ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(myMovie: MyMovie,position: Int){
            val animation = AnimationUtils.loadAnimation(context,R.anim.rv_anim)
            itemRvBinding.root.startAnimation(animation)
            itemRvBinding.tvName.text = myMovie.name
            itemRvBinding.tvAuthor.text = myMovie.author
            itemRvBinding.tvDate.text = myMovie.date

            itemRvBinding.btnEdit.setOnClickListener{
                rvClick.editMovie(myMovie,position)
            }
            itemRvBinding.back.setOnClickListener{
                rvClick.clickItem(myMovie,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition>toPosition){
            for (i in fromPosition until  toPosition+1){
                Collections.swap(list,i,i+1)
            }
        }else{
            for (i in fromPosition until toPosition-1){
                Collections.swap(list,i,i+1)
            }
        }
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }
}
interface RvClick{
    fun editMovie(movie: MyMovie,position: Int)
    fun clickItem(movie: MyMovie,position: Int)
}