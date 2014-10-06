package com.myschool.infra.application.constants;

/**
 * The Enum ExternalizeAction.
 */
public enum ExternalizeAction {

    /** The INIT. */
    INIT,

    /** The EXTRACT. */
    EXTRACT,

    /** The LOAD. */
    LOAD,

    /** The RELOAD. */
    RELOAD;

    /**
     * Gets the externalize action.
     *
     * @param actionName the action name
     * @return the externalize action
     */
    public static ExternalizeAction getExternalizeAction(String actionName) {
        ExternalizeAction externalizeAction = null;
        ExternalizeAction[] values = ExternalizeAction.values();
        for (ExternalizeAction value : values) {
            if (actionName.equalsIgnoreCase(value.toString())) {
                externalizeAction = value;
                break;
            }
        }
        return externalizeAction;
    }
}
