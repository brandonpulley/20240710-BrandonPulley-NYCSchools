package com.jpm.nycschools

import android.app.Application
import com.jpm.nycschools.datasources.NycSchoolsLocalDataSource
import com.jpm.nycschools.datasources.NycSchoolsRemoteDataSource

class MainApplication: Application() {
    val nycSchoolsRemoteDataSource by lazy {  NycSchoolsRemoteDataSource() }
    val nycSchoolsLocalDataSource by lazy {  NycSchoolsLocalDataSource(this) }
}