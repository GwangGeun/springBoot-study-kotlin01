package com.jgg0328.kotlinfirst.domain.entity

import javax.persistence.*

@Entity(name = "comments_image")
class CommentsImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_image_id")
    private val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comments_id")
    private val comments: CommentsEntity,
    @Column(name = "image_path")
    private var imagePath: String
) {
}