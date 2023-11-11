package org.delivery.api.domain.userorder.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequset;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.user.UserEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserOrderConverter {

    public UserOrderEntity toEntity(
        User user,
        Long storeId,
        List<StoreMenuEntity> storeMenuEntityList
    ) {
        var totalAmount = storeMenuEntityList.stream()
            .map(it -> it.getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return UserOrderEntity.builder()
            .userId(user.getId())
            .storeId(storeId)
            .amount(totalAmount)
            .build()
            ;
    }

    public UserOrderResponse toResponse(
        UserOrderEntity userOrderEntity
    ) {
        return UserOrderResponse.builder()
            .id(userOrderEntity.getId())
            .status(userOrderEntity.getStatus())
            .amount(userOrderEntity.getAmount())
            .orderedAt(userOrderEntity.getOrderedAt())
            .acceptedAt(userOrderEntity.getAcceptedAt())
            .cookingStartedAt(userOrderEntity.getCookingStartedAt())
            .deliveryStartedAt(userOrderEntity.getDeliveryStartedAt())
            .receivedAt(userOrderEntity.getReceivedAt())
            .build()
            ;
    }

}
