package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequset;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.db.store.StoreEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;
    private final UserOrderConverter userOrderConverter;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreService storeService;
    private final StoreConverter storeConverter;
    private final UserOrderProducer userOrderProducer;

    // 1. 사용자, 메뉴 id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequset body) {
        var storeMenuEntityList = body.getStoreMenuIdList()
            .stream()
            .map(it -> storeMenuService.getStoreMenuWithThrow(it))
            .collect(Collectors.toList());

        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        // 주문
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 맵핑
        var userOrderMenuEntityList = storeMenuEntityList.stream()
            .map(it -> {
                // menu + user order
                var userOrderMenuEntity = userOrderMenuConverter.toEntity(newUserOrderEntity, it);
                return userOrderMenuEntity;
            })
            .collect(Collectors.toList());

        // 주문내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        // 비동기로 가맹점에 주문 알리기
        userOrderProducer.sendOrder(newUserOrderEntity);

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }

    public List<UserOrderDetailResponse> current(User user) {

        var userOrderEntityList = userOrderService.current(user.getId());

        // 주문 1건씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            // 사용자가 주문 메뉴
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .collect(Collectors.toList());

            // 사용자가 주문한 스토어
            // TODO: 2023/10/24 리펙토링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(it))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build()
                ;
            }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public List<UserOrderDetailResponse> history(User user) {

        var userOrderEntityList = userOrderService.history(user.getId());

        // 주문 1건씩 처리
        var userOrderDetailResponseList = userOrderEntityList.stream().map(it -> {

            // 사용자가 주문 메뉴
            var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(it.getId());
            var storeMenuEntityList = userOrderMenuEntityList.stream()
                .map(userOrderMenuEntity -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                    return storeMenuEntity;
                })
                .collect(Collectors.toList());

            // 사용자가 주문한 스토어
            // TODO: 2023/10/24 리펙토링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

            return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(it))
                .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .build()
                ;
        }).collect(Collectors.toList());

        return userOrderDetailResponseList;
    }

    public UserOrderDetailResponse read(User user, Long orderId) {

        var userOrderEntity = userOrderService.getUserOrderWithOutStatusWithThrow(orderId, user.getId());

        // 사용자가 주문 메뉴
        var userOrderMenuEntityList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        var storeMenuEntityList = userOrderMenuEntityList.stream()
            .map(userOrderMenuEntity -> {
                var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
                return storeMenuEntity;
            })
            .collect(Collectors.toList());

        // 사용자가 주문한 스토어
        // TODO: 2023/10/24 리펙토링 필요
        var storeEntity = storeService.getStoreWithThrow(storeMenuEntityList.stream().findFirst().get().getStoreId());

        return UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
            .storeMenuResponseList(storeMenuConverter.toResponse(storeMenuEntityList))
            .storeResponse(storeConverter.toResponse(storeEntity))
            .build()
            ;
    }
}
