package com.example.todo.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {
    @Insert
    suspend fun insertTodo(todo: Todo): Long
    @Update
    suspend fun updateTodo(todo:Todo):Int
    @Delete
    suspend fun deleteTodo(todo: Todo):Int

    @Query("DELETE FROM todo_data_table")
    suspend fun deleteAll(): Int

    @Query("SELECT * FROM todo_data_table")
    fun getAllTodos():Flow<List<Todo>>
}