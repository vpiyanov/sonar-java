/*
 * Sonar Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.cobertura;

import org.slf4j.LoggerFactory;
import org.sonar.api.batch.CoverageExtension;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.resources.JavaFile;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.plugins.cobertura.api.AbstractCoberturaParser;
import org.sonar.plugins.cobertura.base.CoberturaConstants;

import java.io.File;

public class CoberturaSensor implements Sensor, CoverageExtension {

  private CoberturaSettings settings;

  public CoberturaSensor(CoberturaSettings settings) {
    this.settings = settings;
  }

  public boolean shouldExecuteOnProject(Project project) {
    return settings.isEnabled(project);
  }

  public void analyse(Project project, SensorContext context) {
    String path = (String) project.getProperty(CoberturaConstants.COBERTURA_REPORT_PATH_PROPERTY);
    if (path == null) {
      // wasn't configured - skip
      return;
    }
    File report = project.getFileSystem().resolvePath(path);
    if (!report.exists() || !report.isFile()) {
      LoggerFactory.getLogger(getClass()).warn("Cobertura report not found at {}", report);
      return;
    }
    parseReport(report, context);
  }

  protected void parseReport(File xmlFile, final SensorContext context) {
    LoggerFactory.getLogger(CoberturaSensor.class).info("parsing {}", xmlFile);
    new JavaCoberturaParser().parseReport(xmlFile, context);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  private static final class JavaCoberturaParser extends AbstractCoberturaParser {
    @Override
    protected Resource<?> getResource(String fileName) {
      return new JavaFile(fileName);
    }
  }
}
