package com.saber.gradle.plugin

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class TestHelloPlugin {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()
    private File buildFile

    List<File> pluginClasspath

    @Before
    void setup() {
        buildFile = testProjectDir.newFile('build.gradle')

        def pluginClasspathResource = getClass().classLoader.findResource("plugin-classpath.txt")
        if (pluginClasspathResource == null) {
            throw new IllegalStateException("Did not find plugin classpath resource, run `testClasses` build task.")
        }

        pluginClasspath = pluginClasspathResource.readLines().collect { new File(it) }
    }

    @Test
    void testHelloWorldTask() throws IOException {
        buildFile << """
        plugins {
            id 'saber-hello'
        }
        """

        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("hello")
                .withPluginClasspath(pluginClasspath)
                .build()

        assertTrue(result.getOutput().contains("Hello world !!!"))
        assertEquals(SUCCESS, result.task(":hello").getOutcome())
    }

}
