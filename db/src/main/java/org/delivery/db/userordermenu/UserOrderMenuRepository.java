package org.delivery.db.userordermenu;

import org.delivery.db.user.enums.UserStatus;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

    // select * from user_order_menu where user_order_id = ? and status = ?
    List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId, UserOrderMenuStatus status);
}
