package com.moon.mapper;

import com.moon.model.phone.PhoneModel;

/**
 * Author : CaoXm
 * Date  : 2018/11/22 14:06
 * Description : Interface for
 */
public interface PhoneMapper {

    PhoneModel findPhoneByNum(String phone);

    int addNewPhone(PhoneModel phoneModel);

}
