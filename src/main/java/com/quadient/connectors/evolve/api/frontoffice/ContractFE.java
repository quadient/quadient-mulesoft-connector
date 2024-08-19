package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

public class ContractFE {
    @Parameter
    @Summary("Dedicated field that is displayed in default ticket dashboards.\n" +
            "It usually represents customer identifier sent from external system.")
    @Example("L81054")
    String contractId;

    @Parameter
    @Example("Robert Miles")
    @Summary("Dedicated field that is displayed in default ticket dashboards.\n" +
            "Typically used for showing customer name.")
    String contractName;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
}
