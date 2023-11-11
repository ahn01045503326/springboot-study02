package org.delivery.storeadmin.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import org.delivery.common.message.model.UserOrderMessage;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.storeadmin.domain.storemenu.service.StoreMenuService;
import org.delivery.storeadmin.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.storeadmin.domain.userorder.converter.UserOrderConverter;
import org.delivery.storeadmin.domain.userorder.service.UserOrderService;
import org.delivery.storeadmin.domain.userordermenu.service.UserOrderMenuService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;
    private final SseConnectionPool sseConnectionPool;
    private final UserOrderMenuService userOrderMenuService;
    private final StoreMenuService storeMenuService;
    private final StoreMenuConverter storeMenuConverter;

    /**
     * 주문
     * 주문 내역 찾기
     * 스토어 찾기
     * 연결된 세션 찾아서
     * push
     */
    public void pushUserOrder(UserOrderMessage userOrderMessage){
        System.out.println("userOrderMessage : " + userOrderMessage.toString());
        var userOrderEntity = userOrderService.getUserOrder(userOrderMessage.getUserOrderId())
            .orElseThrow(()-> new RuntimeException("사용자 주문내역 없음"));

        // user order menu
        var userOrderMenuList = userOrderMenuService.getUserOrderMenuList(userOrderEntity.getId());

        // user order menu -> store menu
        var storeMenuResponseList = userOrderMenuList.stream()
            .map(userOrderMenuEntity ->{
                return storeMenuService.getStoreMenuWithThrow(userOrderMenuEntity.getStoreMenuId());
            })
            .map(storeMenuEntity ->{
                return storeMenuConverter.toResponse(storeMenuEntity);
            })
            .collect(Collectors.toList());

        var userOrderResponse = userOrderConverter.toResponse(userOrderEntity);

        // response
        var push = UserOrderDetailResponse.builder()
            .userOrderResponse(userOrderResponse)
            .storeMenuResponseList(storeMenuResponseList)
            .build()
            ;
        System.out.println("여기니 ? " + push.toString());
        var userConnection = sseConnectionPool.getSession(userOrderEntity.getStoreId().toString());

        System.out.println(userConnection.toString());
        // 사용자에게 push
        userConnection.sendMessage(push);

    }
}
