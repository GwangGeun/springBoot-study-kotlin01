package com.jgg0328.kotlinfirst.domain.entity

import javax.persistence.*

@Entity(name = "comments")
class CommentsEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private val post: PostEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private val comments: CommentsEntity?,

    private var content: String,

    ) : BaseEntity() {
}