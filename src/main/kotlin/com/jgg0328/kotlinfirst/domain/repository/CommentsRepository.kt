package com.jgg0328.kotlinfirst.domain.repository

import com.jgg0328.kotlinfirst.domain.entity.CommentsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentsRepository : JpaRepository<CommentsEntity, Long> {
}