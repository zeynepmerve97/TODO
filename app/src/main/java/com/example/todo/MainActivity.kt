package com.example.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.db.Todo
import com.example.todo.db.TodoDataBase
import com.example.todo.db.TodoRepository
import com.example.todo.viewmodel.TodoViewModel
import com.example.todo.viewmodel.TodoViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var adapter: MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dao = TodoDataBase.getInstance(application).todoDAO
        val repository = TodoRepository(dao)
        val factory = TodoViewModelFactory(repository)
        todoViewModel = ViewModelProvider(this,factory).get(TodoViewModel::class.java)
        binding.myViewModel = todoViewModel
        binding.lifecycleOwner = this

        todoViewModel.message.observe(this, Observer{
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })
        initRecylerView()
    }
    private fun initRecylerView(){
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter { selectedItem: Todo -> listItemClicked(selectedItem) }
        binding.todoRecyclerView.adapter = adapter
        displayTodosList()

    }
    private fun displayTodosList(){
        todoViewModel.getSavedTodos().observe(this, Observer { adapter.setList(it)
        adapter.notifyDataSetChanged()})
    }

    private fun listItemClicked(todo: Todo) {
        Toast.makeText(this,"Selected name is ${todo.todo}",Toast.LENGTH_LONG).show()
        todoViewModel.initUpdateAndDelete(todo)

    }

}