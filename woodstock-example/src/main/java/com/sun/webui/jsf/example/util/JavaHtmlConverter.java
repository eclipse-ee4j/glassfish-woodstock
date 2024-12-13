/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation.
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.webui.jsf.example.util;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * JavaHtmlConverter converts the contents of a Java source code file to HTML.
 * The HTML generated display line numbers and color codes Java keywords (or at
 * least those in the private keyword array field).
 */
public final class JavaHtmlConverter {

    /**
     * Cannot be instanciated.
     */
    private JavaHtmlConverter() {
    }

    /**
     * The words that will color coded in the HTML as Java keywords.
     */
    private static final List<String> KEYWORD_LIST
            = new ArrayList<String>(Arrays.asList(new String[]{
        "abstract", "default", "if", "private", "throw",
        "boolean", "do", "implements", "protected", "throws",
        "break", "double", "import", "public", "transient",
        "byte", "else", "instanceof", "return", "try",
        "case", "extends", "int", "short", "void",
        "catch", "final", "interface", "static", "volatile",
        "char", "finally", "long", "super", "while",
        "class", "float", "native", "switch",
        "const", "for", "new", "synchronized",
        "continue", "goto", "package", "this"
    }));

    /**
     * Size to use for syntax indenting in the HTML produced.
     */
    private static final int TAB_SIZE = 8;

    /**
     * Background color used for the HTML produced.
     */
    private static final String BACKGROUND_COLOR = "#FFFFFF";

    /**
     * Color used for normal text in the HTML produced.
     */
    private static final String TEXT_COLOR = "#000000";

    /**
     * Color used for keyword text in the HTML produced.
     */
    private static final String KEYWORD_COLOR = "#0000F0";

    /**
     * Color used for Java comment text in the HTML produced.
     */
    private static final String COMMENT_COLOR = "#1E801E";

    /**
     * Color used for Java String literals in the HTML produced.
     */
    private static final String STRING_COLOR = "#007F7F";

    /**
     * Color used for line number text in the HTML produced.
     */
    private static final String LINE_NUMBER_COLOR = "#C0C0C0";

    /**
     * Color used to highlight line number text in the HTML produced.
     */
    private static final String HIGHLIGHT_LINE_COLOR = "#FF0000";

    /**
     * Accepts a Reader object containing the Java source code to markup and a
     * Writer object that will be used to output the HTML to display the source.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @throws IOException if an IO error occurs
     */
    public static void convert(final Reader in, final Writer out)
            throws IOException {

        convert(in, out, false, true, -1, -1);
    }

    /**
     * Accepts a Reader object containing the Java source code to markup and a
     * Writer object that will be used to output the HTML to display the source.
     * Also accepts a boolean indicating if the HTML generated will be embedded
     * withing an enclosing HTML page, or will be standalone. If embeddable is
     * false, the HTML header and footer will be written to out. Otherwise, no
     * header or footer are written.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @param embeddable Indicates if the HTML output will be embedded inside an
     * HTML page. If false, the HTML header & footer will be written to the
     * Writer object.
     * @throws IOException if an IO error occurs
     */
    public static void convert(final Reader in, final Writer out,
            final boolean embeddable) throws IOException {

        convert(in, out, embeddable, true, -1, -1);
    }

    /**
     * Accepts a Reader object containing the Java source code to markup and a
     * Writer object that will be used to output the HTML to display the source.
     * Also accepts a boolean indicating if the HTML generated will be embedded
     * withing an enclosing HTML page, or will be standalone. If embeddable is
     * false, the HTML header and footer will be written to out. Otherwise, no
     * header or footer are written. The highlightKeywords param should be set
     * to true if color coding of Java keywords is desired.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @param embeddable Indicates if the HTML output will be embedded inside an
     * HTML page. If false, the HTML header & footer will be written to the
     * Writer object.
     * @param highlightKeywords Indicates if Java keyword color coding /
     * highlighting is desired
     * @throws IOException if an IO error occurs
     */
    public static void convert(final Reader in, final Writer out,
            final boolean embeddable, final boolean highlightKeywords)
            throws IOException {

        convert(in, out, embeddable, highlightKeywords, -1, -1);
    }

    /**
     * Accepts a Reader object containing the Java source code to markup and a
     * Writer object that will be used to output the HTML to display the source.
     * Also accepts a boolean indicating if the HTML generated will be embedded
     * withing an enclosing HTML page, or will be standalone. If embeddable is
     * false, the HTML header and footer will be written to out. Otherwise, no
     * header or footer are written. The highlightKeywords param should be set
     * to true if color coding of Java keywords is desired. This constructor can
     * also be used to highlight a certain range of lines in the markup
     * generated for the Java source. The desired lines to highlight are
     * specified via the startLineHighlight and endLineHighlight int params.
     *
     * @param in Contains the Java source code
     * @param out Used to output the HTML displaying the source
     * @param embeddable Indicates if the HTML output will be embedded inside an
     * HTML page. If false, the HTML header & footer will be written to the
     * Writer object.
     * @param highlightKeywords Indicates if Java keyword color coding /
     * highlighting is desired
     * @param startLineHighlight The first line of the Java source that should
     * be highlighted from the rest
     * @param endLineHighlight The last line of the Java source that should be
     * highlighted from the rest
     * @throws IOException if an IO error occurs
     */
    @SuppressWarnings({"checkstyle:magicnumber", "checkstyle:methodlength"})
    public static void convert(final Reader in, final Writer out,
            final boolean embeddable, final boolean highlightKeywords,
            final int startLineHighlight, final int endLineHighlight)
            throws IOException {

        int lineNumber = 1;

        StringBuilder buf = new StringBuilder(2048);
        int c = 0;
        int kwl = 0;
        int bufl = 0;
        char ch = 0;
        char lastChar;

        final int stateNormal = 0;
        final int stateString = 1;
        final int stateChar = 2;
        final int stateCommentLine = 3;
        final int stateComment = 4;

        // Keep a state flag
        int state = stateNormal;

        // Write the header
        if (!embeddable) {
            out.write("<html>\r\n<head>\r\n<title>");
            out.write("</title>\r\n</head>\r\n<body ");
            out.write("bgcolor=\"" + BACKGROUND_COLOR + "\" ");
            out.write("text=\"" + TEXT_COLOR + "\">\r\n");
        }

        out.write("<font size=3><pre>\r\n");
        out.write(getLineNumberReference(
                lineNumber++, startLineHighlight, endLineHighlight));

        while (c != -1) {
            c = in.read();
            lastChar = ch;
            if (c >= 0) {
                ch = (char) c;
            } else {
                ch = 0;
            }

            if (state == stateNormal) {
                if (kwl == 0 && Character.isJavaIdentifierStart(ch)
                        && !Character.isJavaIdentifierPart(lastChar)
                        || kwl > 0 && Character.isJavaIdentifierPart(ch)) {

                    buf.append(ch);
                    bufl++;
                    kwl++;
                    continue;
                } else if (kwl > 0) {
                    String kw = buf.toString().
                            substring(buf.length() - kwl);
                    if (KEYWORD_LIST.contains(kw)
                            && highlightKeywords) {
                        buf.insert(buf.length() - kwl,
                                "<font color=\"" + KEYWORD_COLOR + "\">");
                        buf.append("</font>");
                    }
                    kwl = 0;
                }
            }

            switch (ch) {
                case '&':
                    buf.append("&amp;");
                    bufl++;
                    break;

                case '\"':
                    buf.append("&quot;");
                    bufl++;
                    if (state == stateNormal) {
                        buf.insert(buf.length() - 6,
                                "<font color=\"" + STRING_COLOR + "\">");
                        state = stateString;
                    } else if (state == stateString && lastChar != '\\') {
                        buf.append("</font>");
                        state = stateNormal;
                    }
                    break;

                case '\'':
                    buf.append("\'");
                    bufl++;
                    if (state == stateNormal) {
                        state = stateChar;
                    } else if (state == stateChar && lastChar != '\\') {
                        state = stateNormal;
                    }

                    break;

                case '\\':
                    buf.append("\\");
                    bufl++;
                    if (lastChar == '\\'
                            && (state == stateString || state == stateChar)) {
                        lastChar = 0;
                    }
                    break;

                case '/':
                    buf.append("/");
                    bufl++;
                    if (state == stateComment && lastChar == '*') {
                        buf.append("</font>");
                        state = stateNormal;
                    }
                    if (state == stateNormal && lastChar == '/') {
                        buf.insert(buf.length() - 2,
                                "<font color=\"" + COMMENT_COLOR + "\">");
                        state = stateCommentLine;
                    }

                    break;

                case '*':
                    buf.append("*");
                    bufl++;
                    if (state == stateNormal && lastChar == '/') {
                        buf.insert(buf.length() - 2,
                                "<font color=\"" + COMMENT_COLOR + "\">");
                        state = stateComment;
                    }
                    break;

                case '<':
                    buf.append("&lt;");
                    bufl++;
                    break;

                case '>':
                    buf.append("&gt;");
                    bufl++;
                    break;

                case '\t':
                    int n = bufl / TAB_SIZE * TAB_SIZE + TAB_SIZE;
                    while (bufl < n) {
                        buf.append(' ');
                        bufl++;
                    }
                    break;

                case '\r':
                    // Ignore; we will deal with these
                    // as part of the '\n' processing
                    break;

                case '\n':
                    if (state == stateCommentLine) {
                        buf.append("</font>");
                        state = stateNormal;
                    }

                    buf.append('\r');
                    buf.append(ch);
                    buf.append(getLineNumberReference(lineNumber++,
                            startLineHighlight, endLineHighlight));

                    if (buf.length() >= 1024) {
                        out.write(buf.toString());
                        buf.setLength(0);
                    }

                    bufl = 0;

                    if (kwl != 0) {
                        // This should never execute
                        kwl = 0;
                    }
                    if (state != stateNormal && state != stateComment) {
                        // Syntax Error
                        state = stateNormal;
                    }
                    break;

                case 0:
                    if (c < 0) {
                        if (state == stateCommentLine) {
                            buf.append("</font>");
                            state = stateNormal;
                        }
                        out.write(buf.toString());
                        buf.setLength(0);
                        bufl = 0;
                        if (state == stateComment) {
                            // Syntax Error
                            buf.append("</font>");
                            state = stateNormal;
                        }
                        break;
                    }

                default:
                    bufl++;
                    buf.append(ch);
            }
        }

        out.write("</pre></font>");
        if (!embeddable) {
            out.write("\r\n</body>\r\n</html>");
        }

        out.flush();
    }

    /**
     * Returns an anchor tag containing the necessary markup to highlight the
     * given lineNumber. The given lineNumber must be between startLineHighlight
     * and endLineHighlight or else its color will not be changed (it will not
     * be highlighted). Note that this ONLY highlights the line number, not the
     * entire line.
     *
     * @param lineNumber The line number to be highlighted
     * @param startLineHighlight Minimum value for lineNumber
     * @param endLineHighlight Maximum value for lineNumber
     * @return String Contains the markup to display the highlighted line number
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private static String getLineNumberReference(final int lineNumber,
            final int startLineHighlight, final int endLineHighlight) {

        String result = "<a name=line" + lineNumber + "><font color=\"";
        if (startLineHighlight != -1 && endLineHighlight != -1
                && lineNumber >= startLineHighlight
                && lineNumber < endLineHighlight) {
            result += HIGHLIGHT_LINE_COLOR;
        } else {
            result += LINE_NUMBER_COLOR;
        }
        result += "\">";
        if (lineNumber < 10) {
            result += "&nbsp;&nbsp;&nbsp;" + lineNumber;
        } else if (lineNumber < 100) {
            result += "&nbsp;&nbsp;" + lineNumber;
        } else if (lineNumber < 1000) {
            result += "&nbsp;" + lineNumber;
        } else if (lineNumber < 10000) {
            result += lineNumber;
        }
        result += "</font></a>&nbsp;";
        return result;
    }
}
