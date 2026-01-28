package com.example.myapplication.model

import java.time.LocalDate


val mockTasks = listOf(
    Task("32", "Osta maitoa", "Käy kaupassa", LocalDate.of(2026,1,15), false),
    Task("123", "Kirjoita raportti", "Kuukausiraportti",LocalDate.of(2026,1,12), true),
    Task("5543", "Soita Pentille", "Miettikää projektia", LocalDate.of(2025,1,14), false),
    Task("533", "Powerpoint esitys", "Kokousta varten",  LocalDate.of(2026,1,16), false),
    Task("756", "Lue sähköpostit", "Tyhjennä roskapostit",  LocalDate.of(2026,1,11), true),
    Task("6354", "Mee salille", "Jalkareenit", LocalDate.of(2025,1,13), false)
   )