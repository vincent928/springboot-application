package com.moon.enums.rabbitmq;

/**
 * Author : moon
 * Date  : 2018/11/26 15:33
 * Description : Enum for QUEUE
 */
public enum QueueEnum {

    QUEUE_A("QUEUE_A"),
    QUEUE_B("QUEUE_B"),
    QUEUE_C("QUEUE_C");

    private String value;

    QueueEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
