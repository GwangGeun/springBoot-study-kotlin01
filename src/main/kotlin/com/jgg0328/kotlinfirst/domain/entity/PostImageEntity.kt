package com.jgg0328.kotlinfirst.domain.entity

import javax.persistence.*

@Entity(name = "post_image")
class PostImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private val post: PostEntity,
    @Column(name = "image_path")
    private var imagePath: String
) {
}