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

import java.io.IOException;
import java.beans.Beans;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import com.sun.faces.annotation.Renderer;
import com.sun.webui.jsf.component.util.Util;
import com.sun.webui.jsf.util.ConversionUtilities;
import com.sun.webui.jsf.util.ThemeUtilities;
import com.sun.webui.jsf.util.JavaScriptUtilities;
import com.sun.webui.jsf.util.RenderingUtilities;
import com.sun.webui.theme.Theme;
import com.sun.webui.jsf.theme.ThemeImages;
import com.sun.webui.jsf.theme.ThemeStyles;
import com.sun.webui.html.HTMLAttributes;
import com.sun.webui.html.HTMLElements;
import com.sun.webui.jsf.component.CommonTask;
import com.sun.webui.jsf.component.CommonTasksGroup;
import com.sun.webui.jsf.component.CommonTasksSection;
import com.sun.webui.jsf.component.ImageHyperlink;
import com.sun.webui.jsf.component.ImageComponent;
import com.sun.webui.jsf.component.Icon;
import java.io.StringWriter;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

import static com.sun.webui.jsf.util.JavaScriptUtilities.renderCall;
import static com.sun.webui.jsf.util.JavaScriptUtilities.renderCalls;
import static com.sun.webui.jsf.util.JsonUtilities.JSON_BUILDER_FACTORY;
import static com.sun.webui.jsf.util.JsonUtilities.writeJsonObject;
import static com.sun.webui.jsf.util.ThemeUtilities.getTheme;

/**
 * Renderer for a {@link com.sun.webui.jsf.component.CommonTask} component.
 */
@Renderer(@Renderer.Renders(componentFamily = "com.sun.webui.jsf.CommonTask"))
public class CommonTaskRenderer extends AbstractRenderer {

    /**
     * The set of integer pass-through attributes to be rendered.
     */
    private static final String INT_ATTRIBUTES[] = {
        "tabIndex"
    };

    /**
     * Tool tip to be rendered for the "i" image.
     */
    private static final String INFO_IMAGE_TOOLTIP =
            "commonTasks.infoImageTooltip";

    /**
     *Tool tip to be rendered for the close image.
     */
    private static final String CLOSE_IMAGE_TOOLTIP =
            "commonTasks.closeImageTooltip";

    /**
     *Tool tip to be rendered for the common task overview image.
     */
    private static final String OVERVIEW_IMAGE_TOOLTIP =
            "commonTasks.overviewImageTooltip";

    /**
     * The set of String pass-through attributes to be rendered.
     */
    private static final String STRING_ATTRIBUTES[] = {
        "onBlur",
        "onFocus",
        "onDblClick",
        "onKeyDown",
        "onKeyPress",
        "onMouseUp",
        "onKeyUp",
        "onMouseDown",
        "onMouseMove",
        "onMouseOut",
        "onMouseOver"
    };

    /**
     * Append this string for the  id for "i" image.
     */
    private static final String TOGGLE_IMAGE = "_toggleImg";
    

    /**
     * Append this string for the id for info panel close link.
     */
    private static final String CLOSE_IMAGE = "_closeImg";

    /**
     * Append this string for the id for spacer image.
     */
    private static final String SPACER_IMAGE = "_spacerImg";

    /**
     * Append this string for the id of the toggle panel.
     */
    private static final String INFO_DIV = "_info";

    /**
     * Append this string for the id of title in info panel.
     */
    private static final String INFO_TITLE = "_infoTitle";

    /**
     * Append this string for the id of text in info panel.
     */
    private static final String INFO_TEXT = "_infoText";

    /**
     * Append this string for the id of the default hyperlink of commonTask.
     */
    private static final String COMMONTASK_LINK = "_link";

    /**
     * Append this string for the id of div for the hyperlink in the info panel.
     */
    private static final String INFO_DIV_LINK = "_infoLinkDiv";

    /**
     * Ids that are appended for the spans that are present inside the
     * hyperlink.
     */
    private static final String LEFT_BOTTOM = "_leftBottom_";
    private static final String LEFT_TOP = "_leftTop_";
    private static final String RIGHT_BOTTOM = "_rightBottom_";
    private static final String RIGHT_TOP = "_rightTop_";
    private static final String RIGHT_BORDER = "_rightBorder_";
    private static final String LINK_TEXT = "_linkText_";

    /**
     * Info icon image link for the task.
     */
    private ImageHyperlink ihk;

    /**
     * Spacer  image used while rendering a toggle panel.
     */
    private Icon spacer;

    /**
     * Creates a new instance of TaskRenderer.
     */
    public CommonTaskRenderer() {
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    protected void renderEnd(FacesContext context, UIComponent component,
            ResponseWriter writer) throws IOException {

        if (context == null || component == null) {
            throw new NullPointerException();
        }
        CommonTask task = (CommonTask) component;

        boolean info = false;
        if (!task.isRendered()) {
            return;
        }

        // The common task component should come inside either a common task group
        // component or inside a common tasks section component.
        if (!(task.getParent() instanceof CommonTasksGroup ||
                task.getParent() instanceof CommonTasksSection)) {
            return;
        }

        Theme theme = getTheme(context);
        UIComponent comp = task.getTaskAction();

        String compId = task.getClientId(context);
        writer.startElement(HTMLElements.DIV, task);
        writer.writeAttribute(HTMLAttributes.ID, task.getClientId(context),
                HTMLAttributes.ID);

        String styles = RenderingUtilities.getStyleClasses(context, task,
                theme.getStyleClass(ThemeStyles.CTS_TASK));
        writer.writeAttribute(HTMLAttributes.CLASS, styles,
                HTMLAttributes.CLASS);
        styles = task.getStyle();
        if (styles != null) {
            writer.writeAttribute(HTMLAttributes.STYLE, styles,
                    HTMLAttributes.STYLE);
        }

        writer.startElement(HTMLElements.TABLE, task);
        writer.writeAttribute(HTMLAttributes.BORDER, "0",
                HTMLAttributes.BORDER);
        writer.writeAttribute(HTMLAttributes.CELLSPACING, "0",
                HTMLAttributes.CELLSPACING);
        writer.writeAttribute(HTMLAttributes.CELLPADDING, "0",
                HTMLAttributes.CELLPADDING);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TASK_BACKGROUND),
                HTMLAttributes.CLASS);

        writer.startElement(HTMLElements.TR, component);

        // If no facet has been defined, render the default taskAction.
        if (comp == null) {
            comp = task;
        }
        renderActionItem(comp, task, context, theme, writer);
        String infoText = task.getInfoText();
        String infoTitle = task.getInfoTitle();
        UIComponent facet = task.getInfoPanel();

        if (facet != null || infoText != null ||
                infoTitle != null) {
            renderInfoIcon(task, theme, context, writer);
            renderInfoText(task, theme, context, writer, infoText, infoTitle,
                    facet);
        } else {
            renderPlaceHolderImage(task, theme, context, writer);
        }
        writer.endElement(HTMLElements.TR);
        writer.endElement(HTMLElements.TABLE);
        writer.endElement(HTMLElements.DIV);
    }

    /**
     * Renders the action item for a task. 
     * 
     * @param context The current FacesContext
     * @param task component
     * @param component The action object to render
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object 
     * @throws java.io.IOException if an IO error occurs
     */
    protected void renderActionItem(UIComponent component, UIComponent task,
            FacesContext context, Theme theme, ResponseWriter writer)
            throws IOException {

        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.WIDTH, "2%", HTMLAttributes.WIDTH);
        writer.writeAttribute(HTMLAttributes.VALIGN, "bottom",
                HTMLAttributes.VALIGN);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TASK_LEFT), null);


        spacer = ThemeUtilities.getIcon(theme, ThemeImages.CTS_SPACER_IMAGE);
        spacer.setParent(task);
        spacer.setId(SPACER_IMAGE);
        RenderingUtilities.renderComponent(spacer, context);

        writer.endElement(HTMLElements.TD);

        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.WIDTH, "100%",
                HTMLAttributes.WIDTH);

        writer.writeAttribute(HTMLAttributes.CLASS, theme.getStyleClass(
                ThemeStyles.CTS_TASK_CENTER), HTMLAttributes.CLASS);

        // If no facet is defined, render the default taskAction.
        // Else, render the component that has been specified in the fact.
        if (component instanceof CommonTask) {
            renderDefaultTaskAction((CommonTask) component, writer, context,
                    theme);
        } else {
            RenderingUtilities.renderComponent(component, context);
        }
        writer.endElement(HTMLElements.TD);
    }

    /**
     *Renders an  placeholder image if no toggled information panel
     *comes up.
     * @param context The current FacesContext
     * @param component The commonTask object
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object
     * @throws java.io.IOException if an IO error occurs
     */
    protected void renderPlaceHolderImage(UIComponent component, Theme theme,
            FacesContext context, ResponseWriter writer)
            throws IOException {

        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.WIDTH, "3%",
                HTMLAttributes.WIDTH);
        writer.writeAttribute(HTMLAttributes.ALIGN, "right",
                HTMLAttributes.ALIGN);   
        writer.writeAttribute(HTMLAttributes.VALIGN, "top",
                HTMLAttributes.VALIGN);    
        writer.writeAttribute(HTMLAttributes.CLASS, 
                theme.getStyleClass(ThemeStyles.CTS_TASK_RIGHT),
                HTMLAttributes.CLASS);

        String themeIcon = ThemeImages.CTS_RIGHT_TOGGLE_EMPTY;
        Icon icon = ThemeUtilities.getIcon(theme, themeIcon);
        icon.setParent(component);
        icon.setId(TOGGLE_IMAGE);
        RenderingUtilities.renderComponent(icon, context);
        writer.endElement(HTMLElements.TD);

    }

    /**
     * Render an info icon alongside the taClicking on this would open a pane
     * which contains information about the task.
     *
     * @param context The current FacesContext
     * @param component The commonTask object
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object
     * @throws java.io.IOException if an error occurs
     */
    protected void renderInfoIcon(UIComponent component, Theme theme,
            FacesContext context, ResponseWriter writer) throws IOException {

        writer.startElement(HTMLElements.TD, component);
        writer.writeAttribute(HTMLAttributes.WIDTH, "3%",
                HTMLAttributes.WIDTH);
        writer.writeAttribute(HTMLAttributes.ALIGN, "right",
                HTMLAttributes.ALIGN);
        writer.writeAttribute(HTMLAttributes.VALIGN, "top",
                HTMLAttributes.VALIGN);

        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TASK_RIGHT),
                HTMLAttributes.CLASS);

        ihk = new ImageHyperlink();

        String themeIcon;
        themeIcon = ThemeImages.CTS_RIGHT_TOGGLE;
        ihk.setId(TOGGLE_IMAGE);
        ihk.setIcon(themeIcon);
        ihk.setParent(component);
        ihk.setToolTip(theme.getMessage(INFO_IMAGE_TOOLTIP));
        ihk.setUrl("#");
        RenderingUtilities.renderComponent(ihk, context);
        writer.endElement(HTMLElements.TD);
    }

    /**
     * Renders the info panel for the task
     * @param context The current FacesContext
     * @param task The commonTask object
     * @param writer The current ResponseWriter
     * @param theme The theme used for the object 
     * @param infoText the info text
     * @param infoTitle the info title
     * @param facet component
     * @throws java.io.IOException if an IO error occurs
     */
    protected void renderInfoText(CommonTask task, Theme theme,
            FacesContext context, ResponseWriter writer,
            String infoText, String infoTitle, UIComponent facet)
            throws IOException {

        ImageHyperlink close = new ImageHyperlink();

        // Start rendering the info menu..
        writer.startElement(HTMLElements.DIV, task);
        writer.writeAttribute(HTMLAttributes.ID,
                task.getClientId(context) + INFO_DIV, HTMLAttributes.ID);

        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TASK_INFOPANEL) + " " +
                theme.getStyleClass(ThemeStyles.HIDDEN),
                HTMLAttributes.CLASS);
        writer.startElement(HTMLElements.DIV, task);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_INFOPANEL_CLOSE),
                HTMLAttributes.CLASS);

        close.setParent(task);
        close.setId(CLOSE_IMAGE);
        close.setToolTip(theme.getMessage(CLOSE_IMAGE_TOOLTIP));
        close.setUrl("#");
        if (close.getParent() == null) {
            close.setParent(task);
        }

        close.setIcon(ThemeImages.CTS_PANEL_CLOSE);
        RenderingUtilities.renderComponent(close, context);

        writer.endElement(HTMLElements.DIV);
        writer.startElement(HTMLElements.P, task);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TASK_INFO),
                HTMLAttributes.CLASS);

        // If the infoPanel facet exists then use it.
        // instead of the stuff that is given in the tag attribute.
        if (facet == null) {

            writer.startElement(HTMLElements.SPAN, task);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.CTS_HEADER),
                    HTMLAttributes.CLASS);

            if (infoTitle != null) {
                writer.write(infoTitle);
            }

            writer.endElement(HTMLElements.SPAN);

            writer.startElement(HTMLElements.SPAN, task);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.CTS_TASK_CONTENT),
                    HTMLAttributes.CLASS);
            if (infoText != null) {
                writer.write(infoText);
            }
            writer.endElement(HTMLElements.SPAN);

        } else {
            RenderingUtilities.renderComponent(facet, context);
        }
        writer.endElement(HTMLElements.P);
        facet = task.getInfoLink();
        // Check for the existence of an infoLink facet.
        if (facet != null) {
            renderBottomInfoPanel(task, facet, writer, theme, context);
        }
        writer.endElement(HTMLElements.DIV);

        UIComponent section = task.getParent();
        if (section instanceof CommonTasksGroup
                && section.getParent() instanceof CommonTasksSection) {
            section = section.getParent();
        }

        JsonObject json = getJSONProperties(
                context, theme, task, close, section);

        StringWriter buff = new StringWriter();
        buff.append(JavaScriptUtilities.getDomNode(context, section))
                .append(".addCommonTask(");
        writeJsonObject(json, buff);
        buff.append(");\n");
        JavaScriptUtilities.renderScripTag(writer, buff.toString());
        writer.write("\n");
    }

    /**
     * Renders the bottom section of the infoPanel
     * @param task The common task component
     * @param facet The component to be rendered at the bottom of the info panel.
     * @param writer The response writer
     * @param context Faces context
     * @param theme Theme used for the object
     * @throws java.io.IOException if an error occurs
     */
    protected void renderBottomInfoPanel(UIComponent task, UIComponent facet,
            ResponseWriter writer, Theme theme, FacesContext context)
            throws IOException {

        writer.startElement(HTMLElements.P, task);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_TASK_MORE),
                HTMLAttributes.CLASS);
        writer.startElement(HTMLElements.IMG, task);
        writer.writeAttribute(HTMLAttributes.BORDER, "0",
                HTMLAttributes.BORDER);
        writer.writeAttribute(HTMLAttributes.SRC,
                theme.getImagePath(ThemeImages.HREF_LINK),
                HTMLAttributes.SRC);
        writer.endElement(HTMLElements.IMG);
        RenderingUtilities.renderComponent(facet, context);
        writer.endElement(HTMLElements.P);
    }

    protected JsonObject getJSONProperties(FacesContext context, Theme theme,
            UIComponent component, ImageHyperlink close, UIComponent section)
            throws IOException{

        JsonObject json = JSON_BUILDER_FACTORY.createObjectBuilder()
                // common task id
                .add("commonTaskId", component.getClientId(context))
                // id of the close link that closes the info panel.
                .add("closeId", close.getClientId(context))
                // id of the spacer image used for determining the info panel
                // position
                .add("spacerId", SPACER_IMAGE)
                // id of the displayed "i" image
                .add("infoIconId", new StringBuilder()
                        .append(ihk.getClientId(context))
                        .append(":")
                        .append(ihk.getId())
                        .append("_image")
                        .toString())
                // id of the info panel div's prefix.
                .add("infoPanelVar", INFO_DIV)
                //id of the "i" link image
                .add("imageLinkId", ihk.getClientId(context))
                .build();
        return json;
    }

    @Override
    protected void renderStart(FacesContext context, UIComponent component,
            ResponseWriter writer)
            throws IOException {
    }

    protected void renderDefaultTaskAction(CommonTask task,
            ResponseWriter writer, FacesContext context, Theme theme)
            throws IOException {

        String target = task.getTarget();
        String tooltip = task.getToolTip();

        writer.startElement(HTMLElements.A, task);
        writer.writeAttribute(HTMLAttributes.ID,
                task.getClientId(context)
                + COMMONTASK_LINK, HTMLAttributes.ID);

        UIComponent form = Util.getForm(context, task);
        if (form != null) {
            String formClientId = form.getClientId(context);

            List<String> params = new ArrayList<String>();
            for (UIComponent kid : task.getChildren()) {
                if (!(kid instanceof UIParameter)) {
                    continue;
                }
                params.add((String) kid.getAttributes().get("name"));
                params.add((String) kid.getAttributes().get("value"));
            }

            StringBuilder onClickBuff = new StringBuilder(200);
            onClickBuff.append(renderCalls(task.getOnClick(),
                    // ws_hyperlink_submit
                    renderCall("hyperlink_submit", "this", formClientId,
                            params)));

            writer.writeAttribute(HTMLAttributes.ONCLICK,
                    onClickBuff.toString(),
                    null);
            writer.writeAttribute(HTMLAttributes.HREF, "#", null);
            writer.writeAttribute(HTMLAttributes.CLASS,
                    theme.getStyleClass(ThemeStyles.CTS_TEXT_BGROUND), null);
        }
        if (null != target) {
            writer.writeAttribute(HTMLAttributes.TARGET, target, null);
        }

        if (null != tooltip) {
            writer.writeAttribute(HTMLAttributes.TITLE, tooltip, null);
        }

        addIntegerAttributes(context, task, writer, INT_ATTRIBUTES);
        addStringAttributes(context, task, writer, STRING_ATTRIBUTES);

        // We need to display these styles only at runtime.
        // They do not lend themselves very well at design time.
        if (!Beans.isDesignTime()) {
            renderStyles(task, writer, theme, context);
        }

        // Used for rendering the image along with the text.
        ImageComponent img;
        // The icon attribute gets preference over the imageURL attribute.
        // So, first check if the icon attribute is set. If not, check
        // whether the imageUrl attribute is set and set the image component's
        String icon = task.getIcon();
        // properties accordingly.
        if (icon != null) {
            img = ThemeUtilities.getIcon(theme, icon);
        } else {
            img = new ImageComponent();
            icon = task.getImageUrl();
            if (icon != null) {
                img.setUrl(icon);
                int dim = task.getImageHeight();
                if (dim > 0) {
                    img.setHeight(dim);
                }
                dim = task.getImageWidth();
                if (dim > 0) {
                    img.setWidth(dim);
                }
            }
        }

        // Used for rendering the image along with the text.
        img.setId(task.getId() + "_img");
        img.setToolTip(theme.getMessage(OVERVIEW_IMAGE_TOOLTIP));
        img.setStyleClass(theme.getStyleClass(ThemeStyles.CTS_TASK_BULLET));
        writer.startElement(HTMLElements.SPAN, task);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_PADDING), null);
        writer.writeAttribute(HTMLAttributes.ID, task.getClientId(context)
                + LINK_TEXT, null);
        if (img.getUrl() != null) {
            RenderingUtilities.renderComponent(img, context);
        }
        String text = ConversionUtilities.convertValueToString(task,
                task.getText());
        if (text != null) {
            writer.write(text);
        }
        writer.endElement(HTMLElements.SPAN);
        writer.endElement(HTMLElements.A);
    }

    protected void renderStyles(CommonTask component, ResponseWriter writer,
            Theme theme, FacesContext context) throws IOException {

        String clientId = component.getClientId(context);
        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_LEFT_BOTTOM), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId + LEFT_BOTTOM, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_LEFT_TOP), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId + LEFT_TOP, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_RIGHT_BOTTOM), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId + RIGHT_BOTTOM, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_RIGHT_TOP), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId + RIGHT_TOP, null);
        writer.endElement(HTMLElements.SPAN);

        writer.startElement(HTMLElements.SPAN, component);
        writer.writeAttribute(HTMLAttributes.CLASS,
                theme.getStyleClass(ThemeStyles.CTS_RIGHT_BORDER), null);
        writer.writeAttribute(HTMLAttributes.ID, clientId + RIGHT_BORDER, null);
        writer.endElement(HTMLElements.SPAN);
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {

        // Enforce NPE requirements in the Javadocs
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        CommonTask link = (CommonTask) component;
        String clientId = component.getClientId(context);
        String paramId = new StringBuilder()
                .append(clientId)
                .append(COMMONTASK_LINK)
                .append("_submittedField")
                .toString();

        String value = (String) context.getExternalContext()
                .getRequestParameterMap()
                .get(paramId);

        if ((value == null) || !value.equals(clientId +
                COMMONTASK_LINK)) {
            return;
        }

        //add the event to the queue so we know that a command happened.
        //this should automatically take care of actionlisteners and actions
        link.queueEvent(new ActionEvent(link));
    }
}
