package com.jgg0328.kotlinfirst.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

// k8s 에 배포 즉, std 한 log 설정
class LogExtension {

    companion object {
        fun log(className: Class<Any>): Logger {
            return LoggerFactory.getLogger(className)
        }
    }

}