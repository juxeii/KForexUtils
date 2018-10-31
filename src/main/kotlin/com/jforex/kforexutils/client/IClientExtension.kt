package com.jforex.kforexutils.client

import com.dukascopy.api.system.IClient
import com.jforex.kforexutils.system.KSystemListener
import java.io.File

internal fun IClient.init(cacheDirectoryPath: String) {
    setCacheDirectory(File(cacheDirectoryPath))
    setSystemListener(KSystemListener())
}