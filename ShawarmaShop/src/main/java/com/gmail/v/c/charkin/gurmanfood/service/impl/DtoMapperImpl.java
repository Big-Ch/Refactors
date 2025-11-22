package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.OrderRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ShawarmaRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.UserRequest;
import com.gmail.v.c.charkin.gurmanfood.service.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса маппинга DTO.
 * Использует ModelMapper для автоматического маппинга полей.
 */
@Service
@RequiredArgsConstructor
public class DtoMapperImpl implements DtoMapper {

    private final ModelMapper modelMapper;

    @Override
    public User mapToUser(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    @Override
    public Shawarma mapToShawarma(ShawarmaRequest shawarmaRequest) {
        return modelMapper.map(shawarmaRequest, Shawarma.class);
    }

    @Override
    public Order mapToOrder(OrderRequest orderRequest) {
        return modelMapper.map(orderRequest, Order.class);
    }
}

