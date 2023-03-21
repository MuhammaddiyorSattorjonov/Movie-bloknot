package com.example.moviebloknot

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebloknot.databinding.ActivityMainBinding
import com.example.moviebloknot.models.MyMovie

class MainActivity : AppCompatActivity(),RvClick {
    lateinit var binding: ActivityMainBinding
    lateinit var myMovieAdapter: MyMovieAdapter
    lateinit var list:ArrayList<MyMovie>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        MySharedPreferences.init(this)
        list = MySharedPreferences.catchList
        myMovieAdapter = MyMovieAdapter(this, list, this)
        binding.rvMovies.adapter = myMovieAdapter
        binding.rvMovies
        actions()
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        list.addAll(MySharedPreferences.catchList)
        myMovieAdapter.notifyDataSetChanged()
    }

    override fun editMovie(movie: MyMovie, position: Int) {
//        list.removeAt(position)
        MySharedPreferences.catchList = list
        myMovieAdapter.notifyItemRemoved(position)
        val intent = Intent(this,AddActivity::class.java)
        intent.putExtra("position",position.toString())
        intent.putExtra("name",MySharedPreferences.catchList[position].name)
        intent.putExtra("author",MySharedPreferences.catchList[position].author)
        intent.putExtra("about",MySharedPreferences.catchList[position].about)
        intent.putExtra("data",MySharedPreferences.catchList[position].date)
        startActivity(intent)
    }

    override fun clickItem(movie: MyMovie, position: Int) {
        val intent = Intent(this,Info::class.java)
        val user = MyMovie(MySharedPreferences.catchList[position].name,MySharedPreferences.catchList[position].author,MySharedPreferences.catchList[position].about,MySharedPreferences.catchList[position].date)
        intent.putExtra("shu",user)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_add_menyu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this,AddActivity::class.java))
        return super.onOptionsItemSelected(item)
    }
    private fun actions(){
        val itemTouchHelper =object:ItemTouchHelper.Callback(){
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                val swipeFlags =ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags,swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                myMovieAdapter.onItemMove(viewHolder.adapterPosition,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myMovieAdapter.onItemDismiss(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvMovies)
    }
}