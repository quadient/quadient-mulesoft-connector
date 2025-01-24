package com.quadient.connectors.evolve.internal.operation;

public class ServiceEndpoint {
    public static final String GENERATE_APP_HEALTH = "/production/v6/appHealth";

    public static final String ON_DEMAND_ON_DEMAND = "/production/v6/onDemand";
    public static final String ON_DEMAND_ON_DEMAND_CUSTOM_DATA = "/production/v6/onDemandCustomData";
    public static final String BATCH_START_BATCH_JOB = "/production/v6/startBatchJob";
    public static final String BATCH_BATCH_JOB_STATUS = "/production/v6/batchJobStatus";
    public static final String BATCH_CREATE_WORKING_FOLDER = "/production/v6/createWorkingFolder";


    public static final String CA_TEMPLATES = "/authoring/api/system/v1/templates";
    public static final String CA_HEALTH = "/authoring/api/system/v1/server/health";

    public static final String FO_TICKETS = "/frontoffice/api/system/v2/tickets";

    private ServiceEndpoint() {
    }
}
