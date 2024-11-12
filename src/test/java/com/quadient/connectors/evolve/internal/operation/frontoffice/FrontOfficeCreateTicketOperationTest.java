package com.quadient.connectors.evolve.internal.operation.frontoffice;

import com.quadient.connectors.evolve.api.frontoffice.ApprovalProcessPathFE;
import com.quadient.connectors.evolve.api.frontoffice.CommandFE;
import com.quadient.connectors.evolve.api.frontoffice.ContextFE;
import com.quadient.connectors.evolve.api.frontoffice.ContractFE;
import com.quadient.connectors.evolve.api.frontoffice.DataDefinitionFE;
import com.quadient.connectors.evolve.api.frontoffice.DataDefinitionTypeFE;
import com.quadient.connectors.evolve.api.frontoffice.FrontOfficeCreateTicketInputFE;
import com.quadient.connectors.evolve.api.frontoffice.HolderTypeFE;
import com.quadient.connectors.evolve.api.frontoffice.PropertiesFE;
import com.quadient.connectors.evolve.internal.connection.Connection;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpConstants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FrontOfficeCreateTicketOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<String> bodyCaptor;

    @Test
    public void testFrontOfficeCreateTicket() throws IOException {
        FrontOfficeCreateTicketOperation operation = new FrontOfficeCreateTicketOperation();
        when(connection.sendRequest(any(HttpConstants.Method.class), anyString(), anyString(), isNull())).thenReturn(null);

        FrontOfficeCreateTicketInputFE input = new FrontOfficeCreateTicketInputFE();
        input.stateId = "stateId";
        input.holderValue = "holderValue";
        input.frontOfficeCreateTicketHolderType = HolderTypeFE.USER_GROUP;
        input.addAttachmentFromGlobalStorageEnabled = true;
        input.asynchronousProcessing = true;
        input.multipleRecord = true;
        input.uploadAttachmentFromLocalDriveEnabled = true;
        input.frontOfficeCreateTicketProductionActions = new ArrayList<>();
        input.frontOfficeCreateTicketProductionActions.add("SMS");
        input.frontOfficeCreateTicketProductionActions.add("EMAIL");
        ApprovalProcessPathFE approvalProcessPathFE = new ApprovalProcessPathFE();
        approvalProcessPathFE.resolveDepartment = true;
        approvalProcessPathFE.value = "value";
        input.frontOfficeCreateTicketApprovalProcessPath = approvalProcessPathFE;
        input.frontOfficeCreateTicketAttachments = new ArrayList<>();
        input.frontOfficeCreateTicketAttachments.add("attachment1");
        input.frontOfficeCreateTicketAttachments.add("attachment2");
        ContractFE contractFE = new ContractFE();
        contractFE.contractId = "contractId";
        contractFE.contractName = "contractName";
        input.frontOfficeCreateTicketContract = contractFE;

        PropertiesFE propertiesFE = new PropertiesFE();
        propertiesFE.overrideAlsoNotEmptyProperties = true;
        propertiesFE.createTicketPropertiesMap = new MultiMap<>();
        propertiesFE.createTicketPropertiesMap.put("key1", "value1");
        propertiesFE.createTicketPropertiesMap.put("key2", "value2");
        input.frontOfficeCreateTicketProperties = propertiesFE;
        input.templateName = "templateName";
        input.brand = "brand";
        input.frontOfficeCreateTicketCommands = new ArrayList<>();
        CommandFE commandFE = new CommandFE();
        commandFE.name = "name";
        commandFE.value = "value";
        input.frontOfficeCreateTicketCommands.add(commandFE);
        CommandFE commandFE2 = new CommandFE();
        commandFE2.name = "name2";
        commandFE2.value = "value2";
        input.frontOfficeCreateTicketCommands.add(commandFE2);
        
        input.frontOfficeCreateTicketContexts = new ArrayList<>();
        ContextFE contextFE = new ContextFE();
        contextFE.frontOfficeContextPaths = new ArrayList<>();
        contextFE.frontOfficeContextPaths.add("path1");
        contextFE.frontOfficeContextPaths.add("path2");
        contextFE.value = "value";
        input.frontOfficeCreateTicketContexts.add(contextFE);
        ContextFE contextFE2 = new ContextFE();
        contextFE2.frontOfficeContextPaths = new ArrayList<>();
        contextFE2.frontOfficeContextPaths.add("2path1");
        contextFE2.frontOfficeContextPaths.add("2path2");
        contextFE2.value = "2value";
        input.frontOfficeCreateTicketContexts.add(contextFE2);
        input.frontOfficeCreateTicketCopyOf = 20;
        input.frontOfficeCreateTicketDataDefinitions = new ArrayList<>();
        DataDefinitionFE dataDefinitionFE = new DataDefinitionFE();
        dataDefinitionFE.moduleName = "moduleName";
        dataDefinitionFE.frontOfficeDataDefinitionType = DataDefinitionTypeFE.JSON;
        dataDefinitionFE.value = "value";
        input.frontOfficeCreateTicketDataDefinitions.add(dataDefinitionFE);

        DataDefinitionFE dataDefinitionFE2 = new DataDefinitionFE();
        dataDefinitionFE2.moduleName = "moduleName2";
        dataDefinitionFE2.frontOfficeDataDefinitionType = DataDefinitionTypeFE.ICM_LOCATION;
        dataDefinitionFE2.value = "value2";
        input.frontOfficeCreateTicketDataDefinitions.add(dataDefinitionFE2);
        
        input.description = "description";
        input.pageDocument = true;
        
        operation.frontOfficeCreateTicket(connection, input);
        
        verify(connection).sendRequest(eq(HttpConstants.Method.POST), anyString(), bodyCaptor.capture(), isNull());
        File file = new File("src/test/resources/operation/frontoffice/contentAuthorTemplatesOperationTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());


    }
}