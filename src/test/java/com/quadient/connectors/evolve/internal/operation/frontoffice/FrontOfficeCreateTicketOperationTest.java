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
        input.createTicketHolderType = HolderTypeFE.USER_GROUP;
        input.addAttachmentFromGlobalStorageEnabled = true;
        input.asynchronousProcessing = true;
        input.multipleRecord = true;
        input.uploadAttachmentFromLocalDriveEnabled = true;
        input.productionActions = new ArrayList<>();
        input.productionActions.add("SMS");
        input.productionActions.add("EMAIL");
        ApprovalProcessPathFE approvalProcessPathFE = new ApprovalProcessPathFE();
        approvalProcessPathFE.resolveDepartment = true;
        approvalProcessPathFE.value = "value";
        input.createTicketApprovalProcessPath = approvalProcessPathFE;
        input.createTicketAttachments = new ArrayList<>();
        input.createTicketAttachments.add("attachment1");
        input.createTicketAttachments.add("attachment2");
        ContractFE contractFE = new ContractFE();
        contractFE.contractId = "contractId";
        contractFE.contractName = "contractName";
        input.createTicketContract = contractFE;

        PropertiesFE propertiesFE = new PropertiesFE();
        propertiesFE.overrideAlsoNotEmptyProperties = true;
        propertiesFE.createTicketPropertiesMap = new MultiMap<>();
        propertiesFE.createTicketPropertiesMap.put("key1", "value1");
        propertiesFE.createTicketPropertiesMap.put("key2", "value2");
        input.createTicketProperties = propertiesFE;
        input.templateName = "templateName";
        input.brand = "brand";
        input.commands = new ArrayList<>();
        CommandFE commandFE = new CommandFE();
        commandFE.name = "name";
        commandFE.value = "value";
        input.commands.add(commandFE);
        CommandFE commandFE2 = new CommandFE();
        commandFE2.name = "name2";
        commandFE2.value = "value2";
        input.commands.add(commandFE2);
        
        input.createTicketContexts = new ArrayList<>();
        ContextFE contextFE = new ContextFE();
        contextFE.paths = new ArrayList<>();
        contextFE.paths.add("path1");
        contextFE.paths.add("path2");
        contextFE.value = "value";
        input.createTicketContexts.add(contextFE);
        ContextFE contextFE2 = new ContextFE();
        contextFE2.paths = new ArrayList<>();
        contextFE2.paths.add("2path1");
        contextFE2.paths.add("2path2");
        contextFE2.value = "2value";
        input.createTicketContexts.add(contextFE2);
        input.createTicketCopyOf = 20;
        input.dataDefinitions = new ArrayList<>();
        DataDefinitionFE dataDefinitionFE = new DataDefinitionFE();
        dataDefinitionFE.moduleName = "moduleName";
        dataDefinitionFE.type = DataDefinitionTypeFE.JSON;
        dataDefinitionFE.value = "value";
        input.dataDefinitions.add(dataDefinitionFE);

        DataDefinitionFE dataDefinitionFE2 = new DataDefinitionFE();
        dataDefinitionFE2.moduleName = "moduleName2";
        dataDefinitionFE2.type = DataDefinitionTypeFE.ICM_LOCATION;
        dataDefinitionFE2.value = "value2";
        input.dataDefinitions.add(dataDefinitionFE2);
        
        input.description = "description";
        input.pageDocument = true;
        
        operation.frontOfficeCreateTicket(connection, input);
        
        verify(connection).sendRequest(eq(HttpConstants.Method.POST), anyString(), bodyCaptor.capture(), isNull());
        File file = new File("src/test/resources/operation/frontoffice/contentAuthorTemplatesOperationTest.txt");
        assertEquals(FileUtils.readFileToString(file, "UTF-8"), bodyCaptor.getValue());


    }
}