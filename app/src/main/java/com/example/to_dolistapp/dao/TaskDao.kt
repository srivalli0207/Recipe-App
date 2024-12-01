package com.example.to_dolistapp.dao

import androidx.room.*
import com.example.to_dolistapp.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Query("""SELECT * FROM Task ORDER BY
        CASE WHEN :isAsc = 1 THEN taskTitle END ASC, 
        CASE WHEN :isAsc = 0 THEN taskTitle END DESC""")
    fun getTaskListSortByTaskTitle(isAsc: Boolean) : Flow<List<Task>>

    @Query("""SELECT * FROM Task ORDER BY
        CASE WHEN :isAsc = 1 THEN year END ASC, 
        CASE WHEN :isAsc = 1 THEN month END ASC,
        CASE WHEN :isAsc = 1 THEN day END ASC,
        CASE WHEN :isAsc = 0 THEN year END DESC,
        CASE WHEN :isAsc = 0 THEN month END DESC,
        CASE WHEN :isAsc = 0 THEN day END DESC""")
    fun getTaskListSortByTaskDate(isAsc: Boolean) : Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long


    // First way
    @Delete
    suspend fun deleteTask(task: Task) : Int


    // Second Way
    @Query("DELETE FROM Task WHERE taskId == :taskId")
    suspend fun deleteTaskUsingId(taskId: String) : Int


    @Update
    suspend fun updateTask(task: Task): Int


    @Query("UPDATE Task SET taskTitle=:title, description = :description WHERE taskId = :taskId")
    suspend fun updateTaskPaticularField(taskId:String,title:String,description:String): Int


    @Query("SELECT * FROM Task WHERE taskTitle LIKE :query")
    fun searchTaskList(query: String) : Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE date LIKE :query")
    fun showTaskList(query: String) : Flow<List<Task>>
}