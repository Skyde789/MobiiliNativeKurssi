package com.example.myapplication.model
import java.time.LocalDate
import java.util.UUID

data class  Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val date: LocalDate,
    val done: Boolean,

    )