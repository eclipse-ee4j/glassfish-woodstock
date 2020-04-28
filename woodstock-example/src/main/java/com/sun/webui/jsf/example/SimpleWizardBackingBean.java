/*
 * Copyright (c) 2007, 2019 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Payara Services Ltd.
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
package com.sun.webui.jsf.example;

import java.io.Serializable;
import java.io.File;
import java.net.InetAddress;

import jakarta.faces.context.FacesContext;
import jakarta.faces.component.UIComponent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.validator.ValidatorException;
import com.sun.webui.jsf.component.Wizard;
import com.sun.webui.jsf.event.WizardEvent;
import com.sun.webui.jsf.event.WizardEventListener;
import com.sun.webui.jsf.model.Option;
import com.sun.webui.jsf.example.util.MessageUtil;
import java.net.UnknownHostException;

/**
 * Backing bean for Simple Wizard example.
 */
public class SimpleWizardBackingBean implements Serializable {

    /**
     * Special password characters.
     */
    private static final String SPECIAL_PSWD_CHARS = "?()[]!";

    /**
     * Default home path.
     */
    private static final String DEFAULT_HOME_PATH = "/export/home/";

    /**
     * Password setting locked.
     */
    private static final String PASSWORD_SETTING_LOCKED = "Locked";

    /**
     * Password setting first login.
     */
    private static final String PASSWORD_SETTING_FIRSTLOGIN = "First Login";

    /**
     * Password setting create.
     */
    private static final String PASSWORD_SETTING_CREATE = "Create now";

    /**
     * User name.
     */
    private String userName = "";

    /**
     * User description.
     */
    private String userDesc = "";

    /**
     * User ID.
     */
    private String userUid = "";

    /**
     * UID auto generate flag.
     */
    private boolean uidAutoGenerate = false;

    /**
     * UID set flag.
     */
    private boolean uidSet = true;

    /**
     * User password.
     */
    private String userPswd = "";

    /**
     * User password confirm.
     */
    private String userPswdConfirm = "";

    /**
     * Password now flag.
     */
    private boolean pswdNow = true;

    /**
     * Password locked flag.
     */
    private boolean pswdLocked = false;

    /**
     * Password first login flag.
     */
    private boolean pswdFirstLogin = false;

    /**
     * Primary group name.
     */
    private String primaryGroupName = "other";

    /**
     * Secondary group name.
     */
    private String[] secondaryGroupNames = new String[0];

    /**
     * Home server.
     */
    private String homeServer = "";

    /**
     * Home path.
     */
    private String homePath = null;

    /**
     * Result message.
     */
    private String resultMessage = "Invalid results!";

    /**
     * Private members.
     */
    private String checkPswd = null;

    /**
     * Data list used by some wizard steps.
     */
    private Option[] primaryGroupList;

    /**
     * Data list used by some wizard steps.
     */
    private Option[] secondaryGroupList;

    /**
     * Constructor initializes some list elements.
     */
    public SimpleWizardBackingBean() {
        primaryGroupList = initPrimaryGroupList();
        secondaryGroupList = initSecondaryGroupList();
    }

    /**
     * Getter for user name.
     * @return String
     */
    public String getUserName() {
        return (userName);
    }

    /**
     * Setter for user name.
     * @param newName name
     */
    public void setUserName(final String newName) {
        userName = newName;
    }

    /**
     * Getter for user description.
     * @return String
     */
    public String getUserDescription() {
        return (userDesc);
    }

    /**
     * Setter for user description.
     * @param desc desc
     */
    public void setUserDescription(final String desc) {
        userDesc = desc;
    }

    /**
     * Getter for user ID.
     * @return String
     */
    public String getUserUid() {
        return (userUid);
    }

    /**
     * Setter for user ID.
     * @param uid user ID
     */
    public void setUserUid(final String uid) {
        userUid = uid;
    }

    /**
     * Getter for UID auto generate radio button.
     * @return {@code boolean}
     */
    public boolean isUidAutoGenerate() {
        return (uidAutoGenerate);
    }

    /**
     * Setter for UID auto generate radio button.
     * @param newValue value
     */
    public void setUidAutoGenerate(final boolean newValue) {
        uidAutoGenerate = newValue;
        if (uidAutoGenerate) {
            userUid = generateUserUid();
        }
    }

    /**
     * Getter for UID explicit set radio button.
     * @return {@code boolean}
     */
    public boolean isUidSet() {
        return (uidSet);
    }

    /**
     * Setter for UID explicit set radio button.
     * @param value value
     */
    public void setUidSet(final boolean value) {
        uidSet = value;
        if (uidSet) {
            userUid = "";
        }
    }

    /**
     * Getter for UID field disabled state.
     * @return {@code boolean}
     */
    public boolean isUidDisabled() {
        // UID field is disabled when auto generate is selected.
        return (uidAutoGenerate);
    }

    /**
     * Setter for UID field disabled state.
     * @param value value
     */
    public void setUidDisabled(final boolean value) {
        // Not explicitly set; derived from radio button settings.
    }

    /**
     * Getter for user password.
     * @return String
     */
    public String getUserPassword() {
        return (userPswd);
    }

    /**
     * Setter for user password.
     * @param pswd password
     */
    public void setUserPassword(final String pswd) {
        userPswd = pswd;
    }

    /**
     * Getter for user password confirmation.
     * @return String
     */
    public String getUserPasswordConfirm() {
        return (userPswdConfirm);
    }

    /**
     * Setter for user password confirm.
     * @param pswd password
     */
    public void setUserPasswordConfirm(final String pswd) {
        userPswdConfirm = pswd;
    }

    /**
     * Getter for password locked radio button.
     * @return {@code boolean}
     */
    public boolean isPswdLocked() {
        return (pswdLocked);
    }

    /**
     * Setter for password locked radio button.
     * @param value value
     */
    public void setPswdLocked(final boolean value) {
        pswdLocked = value;
    }

    /**
     * Getter for password on first login radio button.
     * @return {@code boolean}
     */
    public boolean isPswdFirstLogin() {
        return (pswdFirstLogin);
    }

    /**
     * Setter for password on first login radio button.
     * @param value value
     */
    public void setPswdFirstLogin(final boolean value) {
        pswdFirstLogin = value;
    }

    /**
     * Getter for password now radio button.
     * @return {@code boolean}
     */
    public boolean isPswdNow() {
        return (pswdNow);
    }

    /**
     * Setter for password now radio button.
     * @param value value
     */
    public void setPswdNow(final boolean value) {
        pswdNow = value;
        // If set now is not selected, clear password fields.
        if (!pswdNow) {
            userPswd = "";
            userPswdConfirm = "";
        }
    }

    /**
     * Getter for password field disabled state.
     * @return {@code boolean}
     */
    public boolean isPasswordDisabled() {
        // Password is disabled if Set Now is not enabled.
        return (!pswdNow);
    }

    /**
     * Setter for password field disabled state.
     * @param value value
     */
    public void setPasswordDisabled(final boolean value) {
        // Not explicitly set; derived from radio button settings.
    }

    /**
     * Getter for password confirmation field disabled state.
     * @return {@code boolean}
     */
    public boolean isPasswordConfirmDisabled() {
        // Confirmation is disabled if Set Now is not enabled.
        return (!pswdNow);
    }

    /**
     * Setter for password confirmation field disabled state.
     * @param value value
     */
    public void setPasswordConfirmDisabled(final boolean value) {
        // Not explicitly set; derived from radio button settings.
    }

    /**
     * Getter for primary group name.
     * @return String
     */
    public String getPrimaryGroupName() {
        return (primaryGroupName);
    }

    /**
     * Setter for primary group name.
     * @param groupName groupName
     */
    public void setPrimaryGroupName(final String groupName) {
        primaryGroupName = groupName;
    }

    /**
     * Getter for home directory server.
     * @return String
     */
    public String getHomeServer() {
        return (homeServer);
    }

    /**
     * Setter for home directory server.
     * @param serverName serverName
     */
    public void setHomeServer(final String serverName) {
        homeServer = serverName;
    }

    /**
     * Getter for home directory path.
     * @return String
     */
    public String getHomePath() {
        String val = homePath;
        if ((val == null) || (val.length() == 0)) {
            val = DEFAULT_HOME_PATH;
            if (userName != null) {
                val = val + userName;
            }
        }
        return (val);
    }

    /**
     * Setter for home directory path.
     * @param pathName pathName
     */
    public void setHomePath(final String pathName) {
        homePath = pathName;
    }

    /**
     * Getter for home directory server and path.
     * @return String
     */
    public String getHomeDirectory() {
        return (homeServer + ":" + homePath);
    }

    /**
     * Getter for primary group drop down list.
     * @return Option[]
     */
    public Option[] getPrimaryGroupList() {
        return (primaryGroupList);
    }

    /**
     * Getter for selected secondary group names.
     * @return String[]
     */
    public String[] getSecondaryGroupNames() {
        if (secondaryGroupNames == null) {
            return (new String[0]);
        } else {
            return (secondaryGroupNames);
        }
    }

    /**
     * Setter for selected secondary group names.
     * @param groupNames groupNames
     */
    public void setSecondaryGroupNames(final String[] groupNames) {
        secondaryGroupNames = groupNames;
    }

    /**
     * Getter for secondary group available list.
     * @return Option[]
     */
    public Option[] getSecondaryGroupList() {
        return (secondaryGroupList);
    }

    /**
     * Getter for password setting option.
     * @return String
     */
    public String getPasswordSetting() {
        String val = "";
        if (pswdLocked) {
            val = PASSWORD_SETTING_LOCKED;
        } else if (pswdFirstLogin) {
            val = PASSWORD_SETTING_FIRSTLOGIN;
        } else if (pswdNow) {
            val = PASSWORD_SETTING_CREATE;
        }
        return (val);
    }

    /**
     * Setter for password setting option.
     */
    public void setPasswordSetting() {
        // Not set, but derived from radio settings.
    }

    /**
     * Getter for results of operation.
     * @return String
     */
    public String getResultMessage() {
        return (resultMessage);
    }

    /**
     * Setter for results of operation.
     * @param msg message
     */
    public void setResultMessage(final String msg) {
        resultMessage = msg;
    }

    /**
     * Getter for wizard event handler.
     * @return WizardEventListener
     */
    public WizardEventListener getWizardEventListener() {
        return (new SimpleWizardEventListener(this));
    }

    /**
     * Getter for wizard step event handler.
     * @return WizardEventListener
     */
    public WizardEventListener getWizardStepEventListener() {
        return (new SimpleWizardEventListener(this));
    }

    /**
     * Validator for user name. Name must be a string of ASCII characters from 6
     * to 32 bytes long comprised of lower case letters and numbers, and
     * beginning with a letter.
     *
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void validateUserName(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        if (value != null) {
            boolean bValid = false;
            String user = value.toString();
            char[] cs = user.toCharArray();
            String msgKey = "wiz_invalid_username_1";
            if (Character.isLowerCase(cs[0])) {
                // Check for a valid user name length.
                msgKey = "wiz_invalid_username_2";
                if ((cs.length > 5) && (cs.length < 33)) {
                    // Check for valid characters in the name.
                    bValid = true;
                    for (int i = 0; i < cs.length; i++) {
                        if ((Character.isLowerCase(cs[i]))
                                || (Character.isDigit(cs[i]))) {
                            continue;
                        }
                        msgKey = "wiz_invalid_username_3";
                        bValid = false;
                        break;
                    }
                    if (bValid) {
                        // Check first character is valid.
                        if (!Character.isLowerCase(cs[0])) {
                            msgKey = "wiz_invalid_username_4";
                            bValid = false;
                        }
                    }
                }
            }
            if (!bValid) {
                String msgString
                        = MessageUtil.getMessage(msgKey);
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Validator for user uid. Name must be an integer number from 100 to 65535.
     * We should check if the UID is already in use, but this is not part of
     * this example.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void validateUserUid(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        int uid;
        boolean bValid = false;
        if (value != null) {
            String msgKey;
            try {
                uid = (new Integer((String) value));
                msgKey = "wiz_invalid_userid_2";
                if ((uid >= 100) & (uid < 65536)) {
                    bValid = true;
                }
            } catch (NumberFormatException ex) {
                // Number format error
                msgKey = "wiz_invalid_userid_1";
            }
            if (!bValid) {
                String msgString
                        = MessageUtil.getMessage(msgKey);
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Validator for user password. Must be an ASCII string at least 8 bytes
     * long consisting of lower case and upper case alphabetic characters,
     * numbers, and some specific punctuation marks.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void validateUserPassword(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        // We only validate the password if the option to
        // set it now was selected.  Note that a value must
        // be specified for the password field in this case.
        if (!pswdNow) {
            return;
        }
        checkPswd = null;
        String pswd = null;
        boolean bValid = false;
        String msgKey = "wiz_missing_password";
        if (value != null) {
            pswd = value.toString();
            char[] cs = pswd.toCharArray();
            // Check for proper password length.
            msgKey = "wiz_invalid_password_1";
            if ((cs.length > 7) && (cs.length < 33)) {
                boolean bUpper = false;
                boolean bLower = false;
                boolean bDigit = false;
                boolean bSpecial = false;
                // Check for an invalid character.
                bValid = true;
                for (int i = 0; i < cs.length; i++) {
                    int iUpper = 0;
                    int iLower = 0;
                    int iDigit = 0;
                    int iSpecial = 0;
                    if (Character.isLowerCase(cs[i])) {
                        bLower = true;
                        iLower = 1;
                    }
                    if (Character.isUpperCase(cs[i])) {
                        bUpper = true;
                        iUpper = 1;
                    }
                    if (Character.isDigit(cs[i])) {
                        bDigit = true;
                        iDigit = 1;
                    }
                    if (SPECIAL_PSWD_CHARS.indexOf(cs[i]) >= 0) {
                        bSpecial = true;
                        iSpecial = 1;
                    }
                    if ((iLower + iUpper + iDigit + iSpecial) == 0) {
                        msgKey = "wiz_invalid_password_2";
                        bValid = false;
                        break;
                    }
                }
                // Check that we have at least one character from
                // each category.
                if (bValid) {
                    if (!bLower) {
                        msgKey = "wiz_invalid_password_3";
                        bValid = false;
                    } else if (!bUpper) {
                        msgKey = "wiz_invalid_password_4";
                        bValid = false;
                    } else if (!bDigit) {
                        msgKey = "wiz_invalid_password_5";
                        bValid = false;
                    } else if (!bSpecial) {
                        msgKey = "wiz_invalid_password_6";
                        bValid = false;
                    }
                }
            }
        }
        if (!bValid) {
            String msgString = MessageUtil.getMessage(msgKey,
                    SPECIAL_PSWD_CHARS);
            FacesMessage msg = new FacesMessage(msgString);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        checkPswd = pswd;
    }

    /**
     * Validator for confirming user password. Assumes it is called after the
     * first password validator above. Checks that the confirmation value equals
     * the original password value.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    public void confirmUserPassword(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        // We only confirm the password if the option to set a
        // password now was selected.  If checking, the password
        // field validator left us the password value.
        if (!pswdNow) {
            return;
        }
        boolean bValid = true;
        String msgKey = "";
        if (value != null) {
            String pswd = value.toString();
            if (checkPswd != null) {
                if (!checkPswd.equals(pswd)) {
                    msgKey = "wiz_invalid_confirm";
                    bValid = false;
                }
            }
        } else {
            msgKey = "wiz_missing_confirm";
            bValid = false;
        }
        checkPswd = null;
        if (!bValid) {
            String msgString = MessageUtil.getMessage(msgKey);
            FacesMessage msg = new FacesMessage(msgString);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    /**
     * Validator for home directory server. Must be a host accessible from this
     * server.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    public void validateHomeServer(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        String host;
        boolean bValid = false;
        if (value != null) {
            try {
                host = (String) value;
                InetAddress.getByName(host);
                bValid = true;
            } catch (UnknownHostException ex) {
                // Unknown host error
            }
            if (!bValid) {
                String msgString
                        = MessageUtil.getMessage("wiz_invalid_servername");
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Validator for home directory path. Must be a fully qualified path name.
     * We should check that the directory exists as a shared file system path on
     * the home directory server, but that is not part of this example.
     * @param context faces context
     * @param component UI component
     * @param value value to validate
     * @throws ValidatorException if a validation error occurs
     */
    public void validateHomePath(final FacesContext context,
            final UIComponent component, final Object value)
            throws ValidatorException {

        String path;
        boolean bValid = false;
        if (value != null) {
            try {
                path = (String) value;
                if ((path.length() > 0)
                        && (!path.equals(DEFAULT_HOME_PATH))) {
                    File fPath = new File(path);
                    if (fPath.isAbsolute()) {
                        bValid = true;
                    }
                }
            } catch (Exception ex) {
                // Class cast error
            }
            if (!bValid) {
                String msgString
                        = MessageUtil.getMessage("wiz_invalid_pathname");
                FacesMessage msg = new FacesMessage(msgString);
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }

    /**
     * Set auto generated user identifier. Probably OS specific, but we just
     * fake it for our example.
     *
     * @return String
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private String generateUserUid() {
        long t = System.currentTimeMillis();
        int uid = (int) (t - ((t / 1000) * 1000)) + 100;
        return (Integer.toString(uid));
    }

    /**
     * Generate the list of primary group names. Typically accessed from
     * underlying OS.
     *
     * @return Option[]
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private Option[] initPrimaryGroupList() {
        Option[] list = new Option[6];
        list[0] = new Option("staff", "staff");
        list[1] = new Option("engineering", "engineering");
        list[2] = new Option("marketing", "marketing");
        list[3] = new Option("sysadmin", "sysadmin");
        list[4] = new Option("other", "other");
        list[5] = new Option("nobody", "nobody");
        return (list);
    }

    /**
     * Generate the list of secondary group names. Typically accessed from
     * underlying OS.
     *
     * @return Option[]
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private Option[] initSecondaryGroupList() {
        Option[] list = new Option[11];
        list[0] = new Option("east", "east");
        list[1] = new Option("bur_eng", "bur_eng");
        list[2] = new Option("bur_mktg", "bur_mktg");
        list[3] = new Option("central", "central");
        list[4] = new Option("brm_eng", "brm_eng");
        list[5] = new Option("brm_mktg", "brm_mktg");
        list[6] = new Option("west", "west");
        list[7] = new Option("mpk_eng", "mpk_eng");
        list[8] = new Option("mpk_mktg", "mpk_mktg");
        list[9] = new Option("sca_eng", "sca_eng");
        list[10] = new Option("sca_mktg", "sca_mktg");
        return (list);
    }

    /**
     * Reset state. Called by wizard event handler when wizard is complete.
     * Method is package protected.
     */
    void clearState() {
        userName = "";
        userDesc = "";
        userUid = "";
        uidAutoGenerate = false;
        uidSet = true;
        userPswd = "";
        userPswdConfirm = "";
        pswdNow = true;
        pswdLocked = false;
        pswdFirstLogin = false;
        primaryGroupName = "other";
        secondaryGroupNames = new String[0];
        homeServer = "";
        homePath = "";
        resultMessage = "Invalid results!";
    }

    /**
     * Handle wizard events based on wizard NavigationEvent state. We handle the
     * FINISH event to indicate that we must perform the actual operation of
     * adding the new user account and then set the results message based on the
     * outcome of that operation. We handle the COMPLETE event to indicate we
     * should reset all wizard properties so the next execution will start with
     * initial values. All other events are ignored.
     */
    private static final class SimpleWizardEventListener
            implements WizardEventListener {

        /**
         * Reference to our backing bean instance.
         */
        private final SimpleWizardBackingBean bean;

        /**
         * Constructor accepts backing bean reference.
         *
         * @param newBean wizard bean
         */
        SimpleWizardEventListener(
                final SimpleWizardBackingBean newBean) {

            this.bean = newBean;
        }

        @Override
        public boolean handleEvent(final WizardEvent event) {

            Wizard wiz = event.getWizard();
            // WizardStep step = event.getStep();
            // String stepTitle = step.getTitle();;
            switch (event.getNavigationEvent()) {

                // FINISH event indicates we can add our new user.
                // In this example, we simply set the success message.
                case WizardEvent.FINISH:
                    String messageString
                            = MessageUtil.getMessage("wiz_simple_result",
                                    bean.getUserName());
                    bean.setResultMessage(messageString);
                    break;

                // COMPLETE event indicates we are all done.
                // Reset the wizard be removing all its children,
                // which forces next execution to reinitialize them,
                // and clearing the bean state.
                case WizardEvent.COMPLETE:
                    wiz.getChildren().clear();
                    bean.clearState();
                    break;

                // All other events are ignored.
                default:
                    break;
            }
            return (true);
        }

        @Override
        public void setTransient(final boolean transientFlag) {
        }

        @Override
        public boolean isTransient() {
            return true;
        }

        @Override
        public Object saveState(final FacesContext context) {
            return null;
        }

        @Override
        public void restoreState(final FacesContext context,
                final Object state) {

        }
    }
}
