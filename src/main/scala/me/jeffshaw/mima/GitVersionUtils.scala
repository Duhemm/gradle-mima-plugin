package me.jeffshaw.mima

import java.io.ByteArrayOutputStream
import java.util.Optional
import java.util.function.Function
import org.gradle.api.{Action, Project}
import org.gradle.process.{ExecResult, ExecSpec}

object GitVersionUtils {

  def previousGitTags(project: Project, tagFilter: Function[String, Optional[String]]): GitResult = {
    val errorStream = new ByteArrayOutputStream()
    val standardStream = new ByteArrayOutputStream()
    val command = Seq("git", "tag", "-l")

    val gitResult: ExecResult = project.exec(
      new Action[ExecSpec]() {
        override def execute(execSpec: ExecSpec): Unit = {
          execSpec.commandLine(command: _*)
          execSpec.setErrorOutput(errorStream)
          execSpec.setStandardOutput(standardStream)
          execSpec.setIgnoreExitValue(true)
        }
      }
    )
    GitResult(
      exitCode = gitResult.getExitValue,
      stdout = standardStream.toString.trim,
      stderr = errorStream.toString.trim,
      command = command,
      tagFilter = tagFilter
    )
  }

  case class GitResult(
    exitCode: Int,
    stdout: String,
    stderr: String,
    command: Seq[String],
    tagFilter: Function[String, Optional[String]]
  ) {
    object TagMatcher {
      def unapply(s: String): Option[String] = {
        val tag = tagFilter.apply(s)
        Option(tag.orElse(null))
      }
    }

    def versions: Seq[String] = {
      stdout.split("\n").toSeq
        .collect {
          case TagMatcher(filteredTag) => filteredTag
        }.sorted
        .reverse
    }

    def stdoutOrThrowIfNonZero: String = {
      if (exitCode == 0) {
        return stdout
      }
      throw new RuntimeException("Failed running command:\n" + "\tCommand:" + command + "\n" + "\tExit code: " + exitCode + "\n" + "\tStdout:" + stdout + "\n" + "\tStderr:" + stderr + "\n")
    }
  }

}
