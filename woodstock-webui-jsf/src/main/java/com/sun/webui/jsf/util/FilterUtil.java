/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.webui.jsf.util;

import java.util.StringTokenizer;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileFilter;

/**
 * A utility class that checks if a file or folder should be
 * displayed based on the filter entered by the user.
 *
 */

public class FilterUtil implements FileFilter {

        private String filterString = null;
        private String extPattern = null;
        private String namePattern = null;

        public FilterUtil() { }

        public FilterUtil(String filterString) {

            if (filterString != null) {
                this.filterString = filterString;
                this.filterString.trim();
		int index = filterString.indexOf('.');
		if (index == -1) {
		    namePattern = filterString;
		} else if (index == 0) {
		    if (filterString.length() > 1) {
		        extPattern = filterString.substring(1);
		    } 
		} else if (index == (filterString.length() - 1)) {
		    namePattern = filterString.substring(0, index -1);
		} else {
		    namePattern = filterString.substring(0, index);
		    extPattern = filterString.substring(index+1);
		}
            }
        }

        public boolean accept(File f) {

            if (f.isDirectory()) {
                return true;
            }

            if (filterString == null) {
                return true;
            }

            if (filterString.length() == 0) {
                return true;
            }

            if (filterString.equals("*")) {
                return true;
            }

	    String fileName = f.getName();

	    if (filterString.equals(fileName)) {
		return true;
	    }

	    // Now we should get both name and extension
	    // and then match name against name pattern and 
	    // extension against ext pattern. Only if both 
	    // match return true.

            String extension  = getExtension(fileName);
	    String name = getName(fileName);
	    
	    // if pattern does not have a dot there is no need to
	    // check for filename and the extension separately. 
	    if (filterString.indexOf('.') == -1) {
		return check(fileName, filterString);
	    }

	    // if filename does not have an extension and there 
	    // exists an extension pattern return false

            if (filterString.indexOf('.') != -1) {
		if (fileName.indexOf('.') == -1) {
		    return false;
		}
            }

	    // check for name and extension match separately and 
	    // return true if both are true

	    boolean nameFlag =  check(name, namePattern);
	    boolean extFlag =  check(extension, extPattern);

	    return (nameFlag && extFlag);
        }

        /*
         * This function checks to see if the value matches a 
	 * pattern. If the value is null and the pattern is * 
         * this is also considered a match. The result of the
	 * comparison is returned as a boolean value.
         */
        private boolean check(String value, String pattern) {

	    boolean flag = false;

	    if ((value == null) && (pattern == null)) {
	        flag = true;

	    } else if ((value == null) && (pattern != null)) {
		if (pattern.equals("*")) {
		    flag = true;
		}
            } else if ((value != null) && (pattern != null)) {
                if (pattern.equals("*")) {
                    flag  = true;

                } else if (pattern.equals(value)) {
                    flag = true;

                } else {  // match patterns of type *foo*bar*

		    StringTokenizer st = 
			new StringTokenizer(pattern, "*");
		    if (st.countTokens() == 1 ) {

                	if (pattern.endsWith("*") && pattern.startsWith("*")) {
			    flag = checkPattern(st, value);

                	} else if (pattern.endsWith("*")) {
                    	    String prefix =
                                pattern.substring(0, pattern.length()-1);
                    	    flag = value.startsWith(prefix);

                	} else if (pattern.startsWith("*")) {
                    	    String suffix =
                        	pattern.substring(1, pattern.length());
                    	    flag = value.endsWith(suffix);
			} 
		    } else {
			flag = checkPattern(st, value);
		    }
		}
            }
	    return flag;
	}

        /*
         * Get the extension of a file.
         */
        private boolean checkPattern(StringTokenizer token, String name) {

	    int position = 0;
            boolean flag = true;
            while (token.hasMoreTokens()) {
                String subset = token.nextToken();
                int index = name.indexOf(subset, position);
                if (index == -1) {
        	    flag = false;
		    break;
                } else {
                    position = index + subset.length();
                }
            }
	    return flag;
	}

        /*
         * Get the extension of a file.
         */
        private String getExtension(String name) {
            String ext = null;
            if (name != null) {
                int i = name.indexOf('.');
		
		if (i == -1) { 
		    return null; 
		} else if (i == 0) {  // its a .file
		    ext = name.substring(1);

		} else if (i < name.length() - 1) {
                    
                    // this change makes filter case sensitive
                    // ext = name.substring(i+1).toLowerCase();
                    ext = name.substring(i+1);
                }
            }
            return ext;
        }

        /*
         * Get the name part of a file.
         */
        private String getName(String name) {
            String fileName = null;
	    int length = name.length();
            if (name != null) {
                int i = name.indexOf('.');

                if (i == -1) {
                    fileName = name;  // entire thing is the file name
                } else if (i == 0) {  // its a .file
                    fileName = null;
                } else if (i <= length - 1) {
                    fileName = name.substring(0, i);
                } 
            }
            return fileName;
        }
}
