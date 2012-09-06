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
package org.sonar.java;

import com.google.common.collect.Sets;

import java.nio.charset.Charset;
import java.util.Set;

public class JavaConfiguration {

  private final Charset charset;
  private final Set<String> fieldsToExcludeFromLcom4Calculation = Sets.newHashSet();
  private boolean analyzePropertyAccessors = true;

  public JavaConfiguration(Charset charset) {
    this.charset = charset;
  }

  public Charset getCharset() {
    return charset;
  }

  public boolean getIgnoreHeaderComments() {
    // TODO should be configurable
    return false;
  }

  public Set<String> getFieldsToExcludeFromLcom4Calculation() {
    return fieldsToExcludeFromLcom4Calculation;
  }

  public void addFieldToExcludeFromLcom4Calculation(String fieldName) {
    fieldsToExcludeFromLcom4Calculation.add(fieldName);
  }

  public boolean isAnalysePropertyAccessors() {
    return analyzePropertyAccessors;
  }

  public void setAnalyzePropertyAccessors(boolean analyzePropertyAccessors) {
    this.analyzePropertyAccessors = analyzePropertyAccessors;
  }

}