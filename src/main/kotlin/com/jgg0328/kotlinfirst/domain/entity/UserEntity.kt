package com.jgg0328.kotlinfirst.domain.entity

import com.jgg0328.kotlinfirst.domain.enums.Gender
import com.jgg0328.kotlinfirst.dto.user.SignUpRequestDto
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*

@Entity(name = "user")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long = 0L,
    var name: String,
    var password:String,
    var age: Int,
    var email: String,
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    var image: String? = null,

    ) : BaseEntity() {


}