package com.saber.gradle.test;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.writeStringToFile;

public class TestProject extends TemporaryFolder {
    private final String projectName;

    public TestProject(String projectName) {
        this.projectName = projectName;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        copyProjectSource();
        addClassPathToBuildDotGradle();
    }

    private void copyProjectSource() throws IOException {
        File sourceFolder = new File("src").getAbsoluteFile();
        File projectPath = new File(sourceFolder, "testProjects/" + projectName);
        FileUtils.copyDirectory(projectPath, this.getRoot());
    }

    private void addClassPathToBuildDotGradle() throws IOException {
        File projectDirectory = this.getRoot();

        String classpathString = System.getProperty("java.class.path");
        classpathString = Arrays.stream(classpathString.split(":"))
                .map(it -> "'" + it + "'")
                .collect(Collectors.joining(", "));
        File buildFile = new File(projectDirectory, "build.gradle");
        String buildScript = "" +
                "buildscript {\n" +
                "    dependencies {\n" +
                "        classpath files(" + classpathString + ")\n" +
                "    }\n" +
                "}";
        String fileText = readFileToString(buildFile, Charset.defaultCharset());
        writeStringToFile(buildFile, buildScript + fileText, Charset.defaultCharset());
    }

}
