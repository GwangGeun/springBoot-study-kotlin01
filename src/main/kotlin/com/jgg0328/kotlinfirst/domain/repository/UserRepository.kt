package com.jgg0328.kotlinfirst.domain.repository

import com.jgg0328.kotlinfirst.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long>{

    fun findByEmail(email:String) : UserEntity?

}