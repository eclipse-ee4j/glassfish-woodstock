# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Improvements

- Update the annotation processor to work with JSR 269.
- Mavenize the build
- Re-package dojo as part of the build
- Update common fileupload to 1.3.3
- Enforce and update checkstyle
- Enforce and update copyright headers
- Update/Improve the JS code rendering
- Update the theme JS code for components for DojoJS async
- Replace the use of `org.json` with `javax.json`
- Update all APIs to JakartaEE artifacts / versions

### Fixes

- Fix some of the issues added with the switch to the async mode of DojoJS
- Woodstock can be built with JDK >= 1.7
- Fix the example (it deploys to GF 5.1.0)

### Notes

## 4.0.2.13

### Notes

- Fixing screen reader issue PR#1379.

## 4.0.2.12

### Notes

- Fixing duplicate error issue PR#137

## 4.0.2.11

### Notes

- Added http headers to disable proxy caching PR#1377

## 4.0.2.10

### Notes

- GLASSFISH-18252 [508] add `role="presentation"` to `CommonTasks`

## 4.0.2.9

### Notes

- GLASSFISH-18054 [508] add role="presentation" to layout tables
- GLASSFISH-14226 [508] add alt and title attributes to tree graphics
- GLASSFISH-14947 [508] do not render title if not set (fix empty header)

## 4.0.2.8

### Notes

- Rework the addRemove fix to support both IE7 and Firefox 5. Refer to GlassFish
 issue #16903

## 4.0.2.7

### Notes

- Fixed a JS bug in addRemove that broke functionality in Firefox 4.

## 4.0.2.6

### Notes

- Fixed GlassFish issue #4020/#4234.  NPE was fired when client with user-agent
 without a version was used.
- Fixed Tree/TreeNode so they render *all* their children.  This enables
 non-visual components to be used within the tree (like Ajax zones, events
 objects, iterators, etc.).
- Fixed bug where embeded TreeNode image was placed as a child instead of a
 facet.
- Added initial support for Safari &amp; Chrome.
- Fixed Masthead rendering bug (was missing a <td>).
- Fixed GlassFish IT #: 10945.  This required that all cookie names be  RFC 2109
 compliant (no ':' or '/' characters... plus a few more).

## 4.0.2.5

### Notes

- Source code cleanup.
- Changed version page style to use 100% of the width.  This was the design
 intent, but was only enforced in the past by having text that required more
 than the window width.  It now works with shorter text as well.
- Checked in a new non-S-shaped version of the version page background image.
- Fixed bug preventing HTML to be used in the version page copyrightString
 property.  It is important to be able to insert br's, b's and other important
 html tags for formatting purposes.  This property is expected to originate from 
the developer and should not require escaping by default. IF the developer
 expects user-created content to be inserted here, it is now the developer's
 responsibility to escape this text.
- Fixed a couple 508-related alt text messages that were not I18N'd.

## 4.0.2.4

### Notes

- If no Woodstock-supported locales are specified, the locales supported by JSF
 (i.e. Application.getSupportedLocales()) are now used.

## 4.0.2.3

### Notes

- TabSet component now renders it's 'id', making JS easier particularly JSF2
 Ajax.
- ant targets added to the build files to publish maven artifacts.

## 4.0.2.2

### Notes

- A security fix related to the ThemeServlet serving 404 pages was fixed.
- 508 messages were added on some images that were not labeled causing their
 client-id's to be read by screen readers, which significantly impacted
 usability.

### IE7 Button width issue

When displayed in IE7, the button component might be rendered
with an incorrect width. This problem occurs when the button is placed
using absolute positioning.For example, Netbeans with
Visual Web Pack, uses absolute positioning to place components in Grid
Layout
Mode, and in such situation this problem could occur.

IE7 expects a value to be specified for the width of buttons. If
a width attribute is not specified, the width is set to width:auto,
which results in the button's width being set to the entire available
width of the button's container element.&nbsp; For example, if the
button style has `left=130px` then the button will start at 130px left and will
expand to the width of the `<body>`.

#### Workarounds

There are several workarounds that can be used in the developer's code to solve
 the problem.

- Specify the width attribute for the button, or resize the button in the IDE
 to make the IDE assign a width to the button.
- Use relative positioning instead of absolute positioning on the button
 component. For example:
```html
<input name="Button2" style="position:relative; left:72px;top:18px" type="button" value="Button">
```
- Enclose the button inside a div or span element to restrict the available
 width to the div/span element's width. Remember to set the positioning for the
 div/span element. For example:
```html
<div style="position:absolute; left:72px; top:68px">
    <input name="Button1" type="button" value="button">
</div>
<span style="position:absolute; left:72px; top:68px">
    <input name="Button1" type="button" value="button">
</span>
```
- In VWP IDE, drop the button into a Group Panel to put the button inside a
 span tag. In this case no need to set the positioning as IDE sets the
 positioning for Group Panel. For example:
```html
<webuijsf:panelGroup binding="#{Page1.groupPanel1}" id="groupPanel1" style="position:absolute; left:240px; top:168px">
    <webuijsf:button binding="#{Page1.button1}" id="button1" text="Button">
</webuijsf:panelGroup>
