package com.example.myapplication.domain

import java.time.LocalDate

val mockTasks = listOf(
    Task(0, "Osta maitoa", "Käy kaupassa", 2, LocalDate.of(2026,1,15), false),
    Task(1, "Kirjoita raportti", "Kuukausiraportti", 1, LocalDate.of(2026,1,12), true),
    Task(2, "Soita Pentille", "Miettikää projektia", 3, LocalDate.of(2025,1,14), false),
    Task(3, "Powerpoint esitys", "Kokousta varten", 2, LocalDate.of(2026,2,16), false),
    Task(4, "Lue sähköpostit", "Tyhjennä roskapostit", 1, LocalDate.of(2026,1,11), true),
    Task(5, "Mee salille", "Jalkareenit", 3, LocalDate.of(2025,1,13), false)
   )