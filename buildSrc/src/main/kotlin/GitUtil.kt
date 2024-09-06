import java.io.ByteArrayOutputStream
import org.gradle.api.Project

/**
 * 익셉션 나오면 1로 세팅돼서
 * aab 업로드시에 버전이 겹치다고 나올 것임
 * 그 때 default를 바꾸던,,익셉션 안나게 하던,,, 잘 해보도록 해~
 */
fun Project.getGitCommitCount(): Int {
    return runCatching {
        val stdout = ByteArrayOutputStream()
        project.exec {
            commandLine = listOf("git", "rev-list", "--count", "HEAD")
            standardOutput = stdout
        }
        stdout.toString().trim().toInt()
    }.getOrDefault(1)
}
