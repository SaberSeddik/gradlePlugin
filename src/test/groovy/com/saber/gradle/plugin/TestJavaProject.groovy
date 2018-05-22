package com.saber.gradle.plugin

import com.saber.gradle.test.TestProject
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.Test

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class TestJavaProject extends FunctionalPluginTest {
    @Rule
    public final TestProject projectDir = new TestProject("javaProject")

    @Test
    void testHelloWorldTask() throws IOException {
        BuildResult result = GradleRunner.create()
                .withProjectDir(projectDir.getRoot())
                .withArguments("hello")
                .withDebug(true)
                .build()

        assertTrue(result.getOutput().contains("Hello world !!!"))
        assertEquals(SUCCESS, result.task(":hello").getOutcome())
    }

}
