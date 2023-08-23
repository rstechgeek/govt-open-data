package org.project.crudeoilfunction.dao;

import com.amazonaws.services.lambda.runtime.Context;
import org.apache.logging.log4j.util.Strings;
import org.project.crudeoilfunction.domain.ApiResponse;
import org.project.crudeoilfunction.domain.OilRecord;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class CrudeOilDao {
    private DynamoDbEnhancedClient enhancedClient;

    public CrudeOilDao() {
        enhancedClient = DynamoDbEnhancedClient.create();
    }

    public void saveCrudeOil(ApiResponse crudeOilData, Context context) {
        try {
            context.getLogger().log("Saving crude oil data.");
            DynamoDbTable<OilRecord> crudeOil = enhancedClient.table("CRUDE_OIL", TableSchema.fromBean(OilRecord.class));

            if (Objects.nonNull(crudeOilData)) {
                crudeOilData.getRecords().parallelStream().forEach(oilRecord -> {
                    oilRecord.setId(String.valueOf(oilRecord.hashCode()));
                    crudeOil.putItem(oilRecord);
                });
                context.getLogger().log("Crude Oil data saved in DB.");
            } else {
                context.getLogger().log("No data.");
            }

        } catch (DynamoDbException e) {
            context.getLogger().log(e.getMessage());
        }
    }


}
