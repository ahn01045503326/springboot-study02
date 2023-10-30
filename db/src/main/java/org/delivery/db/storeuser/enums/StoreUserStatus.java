package org.delivery.db.storeuser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreUserStatus {

    REGISTERED("등록중"),
    UNREGISTERED("해지"),
    ;

    private final String description;

}
