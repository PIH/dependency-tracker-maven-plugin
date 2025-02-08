package net.mekomsolutions.maven.plugin.dependency;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

import static net.mekomsolutions.maven.plugin.dependency.Constants.ARTIFACT_SUFFIX;

/**
 * Tracks project dependency details excluding transitive dependencies and writes them to a file as
 * an artifact in the build directory.
 */
@Mojo(name = "track", defaultPhase = LifecyclePhase.COMPILE, requiresDependencyResolution = ResolutionScope.TEST)
public class DependencyTrackerMojo extends AbstractMojo {
	
	@Parameter(defaultValue = "${project}", readonly = true)
	private MavenProject project;
	
	@Parameter(property = "buildDirectory", defaultValue = "${project.build.directory}")
	private File buildDirectory;
	
	@Parameter(property = "buildFileName", defaultValue = "${project.build.finalName}" + ARTIFACT_SUFFIX)
	private String buildFileName;
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			DependencyTracker.createInstance(project, buildFileName, buildDirectory, getLog()).track();
		}
		catch (IOException e) {
			throw new MojoFailureException("An error occurred while tracking dependencies", e);
		}
	}
	
}
