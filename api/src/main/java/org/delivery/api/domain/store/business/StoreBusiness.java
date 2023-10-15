package org.delivery.api.domain.store.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreRegisterResponse;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.db.store.enums.StoreCategory;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoreService storeService;
    private final StoreConverter storeConverter;

    public StoreRegisterResponse register(
        StoreRegisterRequest storeRegisterRequest
    ) {
        // req -> entity -> response
        var entity = storeConverter.toEntity(storeRegisterRequest);
        System.out.println(entity.toString());
        var newEntity = storeService.register(entity);
        System.out.println(newEntity.toString());
        var response = storeConverter.toResponse(newEntity);
        return response;
    }

    public List<StoreRegisterResponse> searchCategory(
        StoreCategory storeCategory
    ) {
        // entity list -> response list

        var storeList = storeService.searchByCategory(storeCategory);

        return storeList.stream()
            .map(storeConverter::toResponse)
            .collect(Collectors.toList());
    }
}
