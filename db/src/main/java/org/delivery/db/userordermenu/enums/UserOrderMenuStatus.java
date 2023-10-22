package org.delivery.db.userordermenu.enums;

//@AllArgsConstructor
public enum UserOrderMenuStatus {
    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ;

    UserOrderMenuStatus(String description) {
        this.description = description;
    }

    private final String description;
}
