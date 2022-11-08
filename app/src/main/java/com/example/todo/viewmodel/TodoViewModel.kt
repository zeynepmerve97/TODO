package com.example.todo.viewmodel

import androidx.lifecycle.*
import com.example.todo.Event
import com.example.todo.db.Todo
import com.example.todo.db.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository): ViewModel() {
    private lateinit var todoToUpdateOrDelete: Todo
    val inputTodo = MutableLiveData<String>()
    private var isUpdateOrDelete = false
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
    get() = statusMessage

    init{
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }
    fun saveOrUpdate(){
        if(inputTodo.value==null){
            statusMessage.value = Event("Please enter todo's description")
        }else{
            if(isUpdateOrDelete){
                todoToUpdateOrDelete.todo = inputTodo.value!!
                updateTodo(todoToUpdateOrDelete)
            }else{
                val todoName = inputTodo.value!!
                insertTodo(Todo(0,todoName))
                inputTodo.value = ""
            }

    }}


    private fun insertTodo(todo: Todo)= viewModelScope.launch {
      val newRowId = repository.insert(todo)
        if (newRowId>-1){
            statusMessage.value = Event("Todo Inserted Successfully $newRowId")
        }else{
            statusMessage.value = Event("Error Occured")
        }
    }
    fun getSavedTodos() = liveData { repository.todos.collect{
        emit(it)
    } }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            deleteTodo(todoToUpdateOrDelete)
        }else{
            clearAll()
        }
    }



    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if(noOfRowsDeleted>0){
            statusMessage.value = Event("$noOfRowsDeleted Todos Deleted Successfully")

        }
        else{
            statusMessage.value = Event("Error Occured")
        }

        }

    fun initUpdateAndDelete(todo: Todo){
        inputTodo.value = todo.todo
        isUpdateOrDelete = true
        todoToUpdateOrDelete = todo
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"

    }

    private fun updateTodo(todo: Todo) = viewModelScope.launch {
        val noOfRows = repository.update(todo)
        if (noOfRows > 0) {
            inputTodo.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }
    private fun deleteTodo(todo: Todo)= viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(todo)
        if (noOfRowsDeleted > 0) {
            inputTodo.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
    }
