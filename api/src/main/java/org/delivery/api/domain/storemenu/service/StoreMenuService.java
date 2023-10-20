package org.delivery.api.domain.storemenu.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.storemenu.StoreMenuRepository;
import org.delivery.db.storemenu.enums.StoreMenuStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StoreMenuService {

    private final StoreMenuRepository storeMenuRepository;

    public StoreMenuEntity getStoreMenuWithThrow(Long id) {
        var entity = storeMenuRepository.findFirstByIdAndStatusOrderByIdDesc(id, StoreMenuStatus.REGISTERED);
        return entity.orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<StoreMenuEntity> getStoreMenuByStoreId(Long storeId) {
        return storeMenuRepository.findAllByStoreIdAndStatusOrderBySequenceDesc(storeId, StoreMenuStatus.REGISTERED);
    }

    public StoreMenuEntity register(
        StoreMenuEntity StoreMenuEntity
    ) {
        return Optional.ofNullable(StoreMenuEntity)
            .map(it -> {
                it.setStatus(StoreMenuStatus.REGISTERED);
                return storeMenuRepository.save(it);
            })
            .orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT));
    }

}
