package com.jgg0328.kotlinfirst.domain.entity

import javax.persistence.*

@Entity(name = "post")
class PostEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private val id: Long,
    var title:String,
    var content:String,
    var viewCount:Long

) : BaseEntity() {

    /**
     * TODO: Business Logic
     */
}