package com.example.todo.db

class TodoRepository(private val dao: TodoDAO) {

    val todos = dao.getAllTodos()

    suspend fun insert(todo: Todo):Long{
        return dao.insertTodo(todo)
    }

    suspend fun update(todo: Todo):Int{
        return dao.updateTodo(todo)
    }

    suspend fun delete(todo: Todo):Int{
        return dao.deleteTodo(todo)
    }

    suspend fun deleteAll():Int{
        return dao.deleteAll()
    }
}