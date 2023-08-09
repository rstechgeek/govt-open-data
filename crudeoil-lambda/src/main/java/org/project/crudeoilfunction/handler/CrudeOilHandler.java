package org.project.crudeoilfunction.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.project.crudeoilfunction.domain.ApiRequest;
import org.project.crudeoilfunction.domain.ApiResponse;
import org.project.crudeoilfunction.service.CrudeOilService;
import org.project.crudeoilfunction.service.impl.CrudeOilServiceImpl;
import reactor.core.publisher.Mono;


public class CrudeOilHandler implements RequestHandler<ApiRequest, ApiResponse> {

    private CrudeOilService crudeOilService;

    private void initialize(Context context) {
        context.getLogger().log("Initialization started!");
        crudeOilService = new CrudeOilServiceImpl();
        context.getLogger().log("Initialization done!");
    }

    @Override
    public ApiResponse handleRequest(ApiRequest input, Context context) {
        initialize(context);
        return crudeOilService.getCrudeOilData(input).block();
    }
}
