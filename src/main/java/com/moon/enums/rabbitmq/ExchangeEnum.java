package com.moon.enums.rabbitmq;

/**
 * Author : moon
 * Date  : 2018/11/26 15:30
 * Description : Enum for Exchange
 */
public enum ExchangeEnum {

    EXCHANGE_A("my-mq-exchange_A"),
    EXCHANGE_B("my-mq-exchange_B"),
    EXCHANGE_C("my-mq-exchange_C"),
    FANOUT_EXCHANGE("my-mq-fanout-exchange"),
    ;
    private String value;

    ExchangeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
