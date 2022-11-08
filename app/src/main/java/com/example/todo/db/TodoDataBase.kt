package com.example.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
//BOILERPLATE CODE
@Database(entities = [Todo::class], version = 1)
abstract class TodoDataBase: RoomDatabase() {
    abstract val todoDAO: TodoDAO

    companion object{
        @Volatile
        private var INSTANCE : TodoDataBase? = null
        fun getInstance(context: Context):TodoDataBase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,TodoDataBase::class.java,"todo_data_database"
                    ).build()
                }
                return instance
                }
            }
        }
    }
