package com.github.igrqb.julialangintellij.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspServerSupportProvider
import com.intellij.platform.lsp.api.ProjectWideLspServerDescriptor

internal class JuliaLspServer : LspServerSupportProvider {
  override fun fileOpened(project: Project, file: VirtualFile, serverStarter: LspServerSupportProvider.LspServerStarter) {
    if (file.extension == "jl") {
      serverStarter.ensureServerStarted(JuliaLspServerDescriptor(project))
    }
  }
}

private class JuliaLspServerDescriptor(project: Project) : ProjectWideLspServerDescriptor(project, "Julia") {
  override fun isSupportedFile(file: VirtualFile) = file.extension == "jl"
  override fun createCommandLine() = GeneralCommandLine("julia")
    .withParameters("-e", "\"using LanguageServer; runserver()\"")
}