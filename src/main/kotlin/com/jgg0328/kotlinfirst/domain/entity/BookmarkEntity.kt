package com.jgg0328.kotlinfirst.domain.entity

import javax.persistence.*

@Entity(name = "bookmark")
class BookmarkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private val post: PostEntity
) {
}