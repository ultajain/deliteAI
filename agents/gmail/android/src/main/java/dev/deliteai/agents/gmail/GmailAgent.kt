/*
 * SPDX-FileCopyrightText: (C) 2025 DeliteAI Authors
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package dev.deliteai.agents.gmail

import android.app.Application
import dev.deliteai.agents.gmail.dataModels.GmailSummary
import dev.deliteai.agents.gmail.impl.Controller
import dev.deliteai.agents.gmail.impl.DependencyContainer
import java.util.concurrent.atomic.AtomicBoolean

object GmailAgent {
    private val isAgentInitialized = AtomicBoolean(false)
    private lateinit var controller: Controller

    suspend fun initialize(
        application: Application
    ) {
        if (!isAgentInitialized.get()) {
            val dependencyContainer = DependencyContainer.getInstance(application)
            controller = dependencyContainer.getController()
            isAgentInitialized.set(true)
        }
    }

    /**
     * Summarizes the unread emails in the user's inbox.
     *
     * @return The summary of the unread emails.
     * @throws IllegalStateException if the agent is not initialized.
     */
    suspend fun summarizeUnreadEmails(): GmailSummary {
        if (!isAgentInitialized.get()) throw IllegalStateException("Please initialize GmailAgent first.")
        return controller.summarizeUnreadEmails()
    }

    /**
     * Prompts the LLM with a custom prompt and returns the result as a String.
     *
     * Example prompts:
     * - "Draft a response for the last email I received."
     * - "What were the action items from Bob's meeting?"
     *
     * @param prompt The prompt to send to the LLM. This can be any instruction or question about your Gmail data.
     * @return The LLM's response as a String.
     * @throws IllegalStateException if the agent is not initialized.
     */
    suspend fun promptLlm(prompt: String): String {
        if (!isAgentInitialized.get()) throw IllegalStateException("Please initialize GmailAgent first.")
        return controller.promptLlm(prompt)
    }
}
