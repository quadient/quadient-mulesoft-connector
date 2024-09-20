package com.quadient.connectors.evolve.api.frontoffice;

import org.mule.sdk.api.annotation.param.Parameter;
import org.mule.sdk.api.annotation.param.display.Example;
import org.mule.sdk.api.annotation.param.display.Summary;

public class ContractFE {
    @Parameter
    @Summary("Dedicated field that is displayed in default ticket dashboards.\n" +
            "It usually represents customer identifier sent from external system.")
    @Example("L81054")
    public String contractId;

    @Parameter
    @Example("Robert Miles")
    @Summary("Dedicated field that is displayed in default ticket dashboards.\n" +
            "Typically used for showing customer name.")
    public String contractName;

    public String getContractId() {
        return contractId;
    }

    public String getContractName() {
        return contractName;
    }
}
