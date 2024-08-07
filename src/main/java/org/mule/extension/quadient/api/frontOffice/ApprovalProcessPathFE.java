package org.mule.extension.quadient.api.frontOffice;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

public class ApprovalProcessPathFE {
    @Parameter
    @Summary("'false'' strictly uses approval process at the specified\n" +
            "path. 'true' uses specific approval process according to the department\n" +
            "specified in the path to the template.")
    boolean resolveDepartment;
    
    @Parameter
    @Example("NewBusinessTicket")
    @Summary("Specified approval process file name relative to the /ApprovalProcesses/Ticket/ folder.")
    String value;

    public boolean isResolveDepartment() {
        return resolveDepartment;
    }

    public void setResolveDepartment(boolean resolveDepartment) {
        this.resolveDepartment = resolveDepartment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
