package com.example.imagesearch.ui.mapper

interface EntityMapper<T,R> {
    fun fromEntity(entity: R) : T
    fun toEntity(model: T) : R
}