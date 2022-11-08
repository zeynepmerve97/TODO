package com.example.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.databinding.ListItemBinding
import com.example.todo.db.Todo
import java.util.ArrayList


class MyRecyclerViewAdapter(private val clickListener: (Todo) -> Unit):RecyclerView.Adapter<MyViewHolder>() {
    private val todoList = ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding:ListItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(todoList[position],clickListener)
    }
    fun setList(todos:List<Todo>){
        todoList.clear()
        todoList.addAll(todos)
    }
}
class MyViewHolder(val binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){

    fun bind(todo: Todo,clickListener: (Todo) -> Unit){
        binding.todoTextView.text = todo.todo
        binding.listItemLayout.setOnClickListener { clickListener(todo) }
    }
}
