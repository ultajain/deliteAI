/*
 * SPDX-FileCopyrightText: (C) 2025 DeliteAI Authors
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package dev.deliteai.agents.gmail.impl

import android.app.Application

internal class DependencyContainer private constructor(
    private val application: Application
) {
    private val llmManagerSingleton = LlmManager()
    private val gmailSdkHelperSingleton = GmailSdkHelper.getInstance()
    fun getLlmManager(): LlmManager = llmManagerSingleton
    fun getController(): Controller = Controller(this)
    fun getGmailSdkHelper(): GmailSdkHelper = gmailSdkHelperSingleton

    companion object {
        private var instance: DependencyContainer? = null

        @Synchronized
        fun getInstance(
            application: Application
        ): DependencyContainer {
            if (instance == null) {
                instance = DependencyContainer(application)
            }
            return instance!!
        }
    }
}
