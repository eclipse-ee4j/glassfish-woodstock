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

package com.sun.webui.jsf.renderkit.html;

import com.sun.faces.annotation.Renderer;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import com.sun.webui.jsf.component.AlarmStatus;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;

/**
 * <p>Render an instance of the AlarmStatus component.</p>
 *
 * @author Sean Comerford
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.AlarmStatus"))
public class AlarmStatusRenderer extends HyperlinkRenderer {

    /** Creates a new instance of AlarmStatusRenderer */
    public AlarmStatusRenderer() {
    }

    protected void renderAlarmLabel(FacesContext context,
            AlarmStatus alarmStatus, ResponseWriter writer, Theme theme)
            throws IOException {
        writer.startElement("span", alarmStatus);
        addCoreAttributes(context, alarmStatus, writer,
                theme.getStyleClass(ThemeStyles.MASTHEAD_TEXT));

        Object textObj = alarmStatus.getText();
        String text = textObj == null ? theme.getMessage("masthead.currentAlarms") : ConversionUtilities.convertValueToString(alarmStatus, textObj);

        writer.write(text);
        writer.write("&nbsp;");
        writer.endElement("span");
    }

    /**
     * <p>Render the start of the JobInfo component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        AlarmStatus alarmStatus = (AlarmStatus) component;
        Theme theme = ThemeUtilities.getTheme(context);

        UIComponent facet = alarmStatus.getFacet("alarmLabel");

        if (facet != null) {
            RenderingUtilities.renderComponent(facet, context);
        } else {
            renderAlarmLabel(context, alarmStatus, writer, theme);
        }

        int numAlarms = 0;
        String icon = null;
        String themeIcon = null;
        String alt = null;
        facet = alarmStatus.getFacet("downAlarms");

        if (facet != null) {
            // render the developer specified facet for down alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isDownAlarms()) {
            // render the down alarm image + count
            numAlarms = alarmStatus.getNumDownAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_DOWN_MEDIUM : ThemeImages.ALARM_MASTHEAD_DOWN_DIMMED;
            icon = alarmStatus.getDownIcon() != null ? alarmStatus.getDownIcon() : themeIcon;
            alt = theme.getMessage("Alarm.downImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }

        facet = alarmStatus.getFacet("criticalAlarms");

        if (facet != null) {
            // render the developer specified facet for critical alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isCriticalAlarms()) {
            // render the critical alarm + count
            numAlarms = alarmStatus.getNumCriticalAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_CRITICAL_MEDIUM : ThemeImages.ALARM_MASTHEAD_CRITICAL_DIMMED;
            icon = alarmStatus.getCriticalIcon() != null ? alarmStatus.getCriticalIcon() : themeIcon;
            alt = theme.getMessage("Alarm.criticalImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }

        facet = alarmStatus.getFacet("majorAlarms");

        if (facet != null) {
            // render the developer specified facet for major alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isMajorAlarms()) {
            // render the major alarm + count
            numAlarms = alarmStatus.getNumMajorAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_MAJOR_MEDIUM : ThemeImages.ALARM_MASTHEAD_MAJOR_DIMMED;
            icon = alarmStatus.getMajorIcon() != null ? alarmStatus.getMajorIcon() : themeIcon;
            alt = theme.getMessage("Alarm.majorImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }

        facet = alarmStatus.getFacet("minorAlarms");

        if (facet != null) {
            // render the developer specified facet for minor alarms
            RenderingUtilities.renderComponent(facet, context);
        } else if (alarmStatus.isMinorAlarms()) {
            // render the minor alarm + count
            numAlarms = alarmStatus.getNumMinorAlarms();
            themeIcon = numAlarms != 0 ? ThemeImages.ALARM_MASTHEAD_MINOR_MEDIUM : ThemeImages.ALARM_MASTHEAD_MINOR_DIMMED;
            icon = alarmStatus.getMinorIcon() != null ? alarmStatus.getMinorIcon() : themeIcon;
            alt = theme.getMessage("Alarm.minorImageAltText");

            renderAlarmCount(context, writer, alarmStatus, icon,
                    theme.getStyleClass(ThemeStyles.MASTHEAD_ALARM_LINK),
                    numAlarms, alt);
        }
    }

    protected void renderAlarmCount(FacesContext context,
            ResponseWriter writer, AlarmStatus alarmStatus, String icon,
            String styleClass, int numAlarms, String alt) throws IOException {

        // We don't want conversion here. This is just to cache
        // the original value so we can restore it after calling
        // renderLink.
        //
        Object realText = alarmStatus.getText();
        String realIcon = alarmStatus.getIcon();
        String realStyleClass = alarmStatus.getStyleClass();
        boolean reallyDisabled = alarmStatus.isDisabled();
        String realAlt = alarmStatus.getAlt();

        // This is really bad practice.
        // A renderer MUST not modify a component.
        // It looks like we need to either make
        // renderLink more granular or reimplement it here.
        //
        alarmStatus.setIcon(icon);
        alarmStatus.setText("&nbsp;" + numAlarms);
        alarmStatus.setStyleClass(styleClass);
        alarmStatus.setAlt(alt);

        if (numAlarms == 0) {
            // alarm count of zero is NOT linked
            alarmStatus.setDisabled(true);
        }

        renderLink(context, alarmStatus, writer);
        writer.write("&nbsp;&nbsp;&nbsp;");

        alarmStatus.setText(realText);
        alarmStatus.setIcon(realIcon);
        alarmStatus.setStyleClass(realStyleClass);
        alarmStatus.setDisabled(reallyDisabled);
        alarmStatus.setAlt(alt);
    }

    /**
     * <p>Render the end of the JobInfo component.</p>
     *
     * @param context <code>FacesContext</code> for the current request
     * @param component <code>UIComponent</code> to be rendered
     * @param writer <code>ResponseWriter</code> to which the element
     * start should be rendered
     * @exception IOException if an input/output error occurs
     */
    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {
        // override super so tha we do nothing here
    }

    @Override
    protected void finishRenderAttributes(FacesContext context,
            UIComponent component,
            ResponseWriter writer)
            throws IOException {
        // this method will (usually) be called 4 times to render each alarm
        // image and count as a separate instance of the same anchor tag
        AlarmStatus alarmStatus = (AlarmStatus) component;
        UIComponent image = alarmStatus.getImageFacet();

        String label = ConversionUtilities.convertValueToString(alarmStatus,
                alarmStatus.getText());

        if (image != null) {
            RenderingUtilities.renderComponent(image, context);
        }

        if (label != null && label.length() != 0) {
            writer.write(label);
        }
    }

    /**
     * @deprecated
     */
    protected void renderAlarmImage(FacesContext context, AlarmStatus alarmStatus,
            ResponseWriter writer, Theme theme, String alarmSrc)
            throws IOException {
        ImageComponent image = new ImageComponent();

        image.setIcon(alarmSrc);

        RenderingUtilities.renderComponent(image, context);
    }
}
