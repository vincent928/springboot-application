package com.moon.enums.rabbitmq;

/**
 * Author : moon
 * Date  : 2018/11/26 15:34
 * Description : Enum for
 */
public enum RoutingKeyEnum {

    ROUTINGKEY_A("spring-boot-routingKey_A"),
    ROUTINGKEY_B("spring-boot-routingKey_B"),
    ROUTINGKEY_C("spring-boot-routingKey_C");

    private String value;

    RoutingKeyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
