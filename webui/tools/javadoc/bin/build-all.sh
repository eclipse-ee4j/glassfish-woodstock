#!/bin/sh
#
# Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
#
# This program and the accompanying materials are made available under the
# terms of the Eclipse Public License v. 2.0, which is available at
# http://www.eclipse.org/legal/epl-2.0.
#
# This Source Code may also be made available under the following Secondary
# Licenses when the conditions for such availability set forth in the
# Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
# version 2 with the GNU Classpath Exception, which is available at
# https://www.gnu.org/software/classpath/license.html.
#
# SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
#

PKGPREFIX="com.sun.webui.jsf"
PKGPATHPREFIX="com/sun/webui/jsf"
WEBUISRC="webui/runtime/src"

PATH="/usr/bin:${PATH}"
SCRIPT=`basename $0`
SCRIPT_DIR=`dirname $0`
SCRIPT_DIR=`cd $SCRIPT_DIR; pwd`

[ -z "$JAVA_HOME" ] && JAVA_HOME=/usr/java

BASE_DIR=$SCRIPT_DIR/..
DOCLET_PATH=$BASE_DIR/src
WS_DIR=$SCRIPT_DIR/../../../..

##
## Build Javadoc doclet
##
echo "\nBuilding tags package...\n"
cd $BASE_DIR/src/$PKGPATHPREFIX/doclets/standard/tags
javac -classpath $JAVA_HOME/lib/tools.jar:$DOCLET_PATH *.java

[ "$?" -ne 0 ] && exit
echo "\nBuilding standard package...\n"
cd $BASE_DIR/src/$PKGPATHPREFIX/doclets/standard
javac -classpath $JAVA_HOME/lib/tools.jar:$DOCLET_PATH *.java

[ "$?" -ne 0 ] && exit
echo "\nBuilding doclet package...\n"
cd $BASE_DIR/src/$PKGPATHPREFIX/doclets
javac -classpath $JAVA_HOME/lib/tools.jar:$DOCLET_PATH *.java

[ "$?" -ne 0 ] && exit
echo "\nBuilding doclets jar file\n"
cd $BASE_DIR/src
rm -rf doclets.jar
jar cvf $BASE_DIR/lib/doclets.jar `find . -name \*class -o -name \*properties`

##
## Clean up.
##
rm -rf `find . -name \*class`

exit

##
## Test Javadoc
##
CLASS_PATH="$WS_DIR/webui/classes/runtime/$PKGPATHPREFIX/component"

SOURCE_PATH="\
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component \
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component"

FILES="\
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/DropDown.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/ListSelector.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/Selector.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/component/Table.java
    $WS_DIR/$WEBUISRC/$PKGPATHPREFIX/event/TableSelectPhaseListener.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/DropDownBase.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/ListSelectorBase.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/SelectorBase.java
    $WS_DIR/webui/gen/component/$PKGPATHPREFIX/component/TableBase.java"

echo "\nTesting Javadoc: see $BASE_DIR/test\n"
rm -rf $BASE_DIR/test
javadoc -classpath "$CLASS_PATH" -sourcepath "$SOURCE_PATH" $FILES \
    -docletpath $BASE_DIR/lib/doclets.jar -doclet $PKGPATH.doclets.standard.Standard \
    -d $BASE_DIR/test
