package com.jgg0328.kotlinfirst.domain.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

// 참고 : https://gist.github.com/dlxotn216/94c34a2debf848396cf82a7f21a32abe
@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity {
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    var createdDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(name = "modified_date")
    var modifiedDate: LocalDateTime = LocalDateTime.now()

    /**
     * whenever created or update, auditorProvider will be called and it will set createdBy & lastModifiedBy
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    var createdBy: String = ""

    @LastModifiedBy
    @Column(name = "modified_by")
    var modifiedBy: String = ""
}