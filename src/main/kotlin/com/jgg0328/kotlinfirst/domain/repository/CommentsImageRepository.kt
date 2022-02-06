package com.jgg0328.kotlinfirst.domain.repository

import com.jgg0328.kotlinfirst.domain.entity.CommentsImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentsImageRepository : JpaRepository<CommentsImageEntity, Long> {
}