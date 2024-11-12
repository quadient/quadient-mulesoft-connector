package com.quadient.connectors.evolve.internal.operation.contentauthor;

import com.quadient.connectors.evolve.api.contentauthor.CategorizationConditionFE;
import com.quadient.connectors.evolve.api.contentauthor.ConditionFE;
import com.quadient.connectors.evolve.api.contentauthor.ContentAuthorTemplatesInputFE;
import com.quadient.connectors.evolve.api.contentauthor.LogicalOperatorFE;
import com.quadient.connectors.evolve.api.contentauthor.MetadataConditionFE;
import com.quadient.connectors.evolve.api.contentauthor.OperatorEnumFE;
import com.quadient.connectors.evolve.internal.ObjectConverter;
import com.quadient.connectors.evolve.internal.connection.Connection;
import com.quadient.connectors.generated.model.ca.CategorizationCondition;
import com.quadient.connectors.generated.model.ca.Condition;
import com.quadient.connectors.generated.model.ca.MetadataCondition;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mule.runtime.http.api.HttpConstants;

import java.util.ArrayList;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContentAuthorTemplatesOperationTest extends TestCase {

    @Mock
    private Connection connection;

    @Captor
    private ArgumentCaptor<Map<String, String>> uriParamsCapture;

    @Test
    public void testContentAuthorGetTemplates() {
        ObjectConverter objectConverter = new ObjectConverter();
        ContentAuthorTemplatesOperation operation = new ContentAuthorTemplatesOperation();

        when(connection.sendRequest(any(HttpConstants.Method.class), anyString(), isNull(), anyMap())).thenReturn(null);

        ContentAuthorTemplatesInputFE input = new ContentAuthorTemplatesInputFE();
        input.folder = "folder";
        input.offset = 0;
        input.limit = 100;
        input.includeMetadata = true;
        input.holder = "holder";
        input.approvalStates = "approvalStates";

        input.contentAuthorTemplatesCondition = createConditionFE("prefixA");
        input.contentAuthorTemplatesCondition.conditions = new ArrayList<>();
        input.contentAuthorTemplatesCondition.conditions.add(objectConverter.convertToJson(createCondition("prefixB")));
        input.contentAuthorTemplatesCondition.conditions.add(objectConverter.convertToJson(createCondition("prefixC")));
        operation.contentAuthorGetTemplates(connection, input);

        verify(connection).sendRequest(any(HttpConstants.Method.class), anyString(), isNull(), uriParamsCapture.capture());

        Map<String, String> capturedParams = uriParamsCapture.getValue();
        assertEquals("approvalStates", capturedParams.get("approvalStates"));
        assertEquals("true", capturedParams.get("includeMetadata"));
        assertEquals("{\"categorizations\":[{\"fieldName\":\"prefixAfieldName\",\"name\":\"name\",\"negation\":true,\"operator\":\"beginWith\",\"value\":\"value\"},{\"fieldName\":\"prefixAfieldName2\",\"name\":\"name2\",\"negation\":false,\"operator\":\"empty\",\"value\":\"value2\"}],\"conditions\":[{\"categorizations\":[{\"fieldName\":\"prefixBfieldName\",\"name\":\"name\",\"negation\":true,\"operator\":\"beginWith\",\"value\":\"value\"},{\"fieldName\":\"prefixBfieldName2\",\"name\":\"name2\",\"negation\":false,\"operator\":\"empty\",\"value\":\"value2\"}],\"conditions\":[],\"metadata\":[{\"name\":\"prefixBname\",\"negation\":true,\"operator\":\"equal\",\"value\":\"value\"},{\"name\":\"prefixBname2\",\"negation\":false,\"operator\":\"empty\",\"value\":\"value2\"}],\"negation\":true,\"operator\":\"and\"},{\"categorizations\":[{\"fieldName\":\"prefixCfieldName\",\"name\":\"name\",\"negation\":true,\"operator\":\"beginWith\",\"value\":\"value\"},{\"fieldName\":\"prefixCfieldName2\",\"name\":\"name2\",\"negation\":false,\"operator\":\"empty\",\"value\":\"value2\"}],\"conditions\":[],\"metadata\":[{\"name\":\"prefixCname\",\"negation\":true,\"operator\":\"equal\",\"value\":\"value\"},{\"name\":\"prefixCname2\",\"negation\":false,\"operator\":\"empty\",\"value\":\"value2\"}],\"negation\":true,\"operator\":\"and\"}],\"metadata\":[{\"name\":\"prefixAname\",\"negation\":true,\"operator\":\"equal\",\"value\":\"value\"},{\"name\":\"prefixAname2\",\"negation\":false,\"operator\":\"empty\",\"value\":\"value2\"}],\"negation\":true,\"operator\":\"and\"}", capturedParams.get("condition"));
        assertEquals("folder", capturedParams.get("folder"));
        assertEquals("0", capturedParams.get("offset"));
        assertEquals("100", capturedParams.get("limit"));
        assertEquals("holder", capturedParams.get("holder"));
    }

    private ConditionFE createConditionFE(String prefix) {
        ConditionFE condition = new ConditionFE();
        condition.contentAuthorConditionCategorizations = new ArrayList<>();
        condition.contentAuthorConditionCategorizations.add(createCategorizationConditionFE(prefix + "fieldName", "name", "value", true, OperatorEnumFE.BEGINWITH));
        condition.contentAuthorConditionCategorizations.add(createCategorizationConditionFE(prefix + "fieldName2", "name2", "value2", false, OperatorEnumFE.EMPTY));
        condition.contentAuthorConditionMetadata = new ArrayList<>();
        condition.contentAuthorConditionMetadata.add(createMetadataConditionFE(prefix + "name", "value", true, OperatorEnumFE.EQUAL));
        condition.contentAuthorConditionMetadata.add(createMetadataConditionFE(prefix + "name2", "value2", false, OperatorEnumFE.EMPTY));
        condition.negation = true;
        condition.contentAuthorConditionOperator = LogicalOperatorFE.AND;
        condition.conditions = new ArrayList<>();
        return condition;
    }

    private Condition createCondition(String prefix) {
        Condition condition = new Condition();
        condition.setCategorizations(new ArrayList<>());
        condition.getCategorizations().add(createCategorizationCondition(prefix + "fieldName", "name", "value", true, OperatorEnumFE.BEGINWITH));
        condition.getCategorizations().add(createCategorizationCondition(prefix + "fieldName2", "name2", "value2", false, OperatorEnumFE.EMPTY));
        condition.setMetadata(new ArrayList<>());
        condition.getMetadata().add(createMetadataCondition(prefix + "name", "value", true, OperatorEnumFE.EQUAL));
        condition.getMetadata().add(createMetadataCondition(prefix + "name2", "value2", false, OperatorEnumFE.EMPTY));
        condition.setNegation(true);
        condition.setOperator(Condition.OperatorEnum.AND);
        condition.setConditions(new ArrayList<>());
        return condition;
    }

    private CategorizationConditionFE createCategorizationConditionFE(String fieldName, String name, String value, boolean negation, OperatorEnumFE operator) {
        CategorizationConditionFE condition = new CategorizationConditionFE();
        condition.fieldName = fieldName;
        condition.name = name;
        condition.value = value;
        condition.negation = negation;
        condition.operator = operator;
        return condition;
    }

    private CategorizationCondition createCategorizationCondition(String fieldName, String name, String value, boolean negation, OperatorEnumFE operator) {
        CategorizationCondition condition = new CategorizationCondition();
        condition.setFieldName(fieldName);
        condition.setName(name);
        condition.setValue(value);
        condition.setNegation(negation);
        condition.setOperator(CategorizationCondition.OperatorEnum.fromValue(operator.getValue()));
        return condition;
    }

    private MetadataConditionFE createMetadataConditionFE(String name, String value, boolean negation, OperatorEnumFE operator) {
        MetadataConditionFE metadataCondition = new MetadataConditionFE();
        metadataCondition.name = name;
        metadataCondition.value = value;
        metadataCondition.negation = negation;
        metadataCondition.operator = operator;
        return metadataCondition;
    }

    private MetadataCondition createMetadataCondition(String name, String value, boolean negation, OperatorEnumFE operator) {
        MetadataCondition metadataCondition = new MetadataCondition();
        metadataCondition.setName(name);
        metadataCondition.setValue(value);
        metadataCondition.setNegation(negation);
        metadataCondition.setOperator(MetadataCondition.OperatorEnum.fromValue(operator.getValue()));
        return metadataCondition;
    }
}