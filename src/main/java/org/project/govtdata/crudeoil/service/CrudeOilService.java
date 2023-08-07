package org.project.govtdata.crudeoil.service;

import org.project.govtdata.crudeoil.domain.ApiRequest;
import org.project.govtdata.crudeoil.domain.ApiResponse;

public interface CrudeOilService {
    ApiResponse getCrudeOilData(ApiRequest request);
}
