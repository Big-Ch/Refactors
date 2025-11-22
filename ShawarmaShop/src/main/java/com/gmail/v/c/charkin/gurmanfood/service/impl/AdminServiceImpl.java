package com.gmail.v.c.charkin.gurmanfood.service.impl;

import com.gmail.v.c.charkin.gurmanfood.constants.ErrorMessage;
import com.gmail.v.c.charkin.gurmanfood.constants.SuccessMessage;
import com.gmail.v.c.charkin.gurmanfood.domain.Order;
import com.gmail.v.c.charkin.gurmanfood.domain.Shawarma;
import com.gmail.v.c.charkin.gurmanfood.domain.User;
import com.gmail.v.c.charkin.gurmanfood.dto.request.ShawarmaRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.request.SearchRequest;
import com.gmail.v.c.charkin.gurmanfood.dto.response.MessageResponse;
import com.gmail.v.c.charkin.gurmanfood.dto.response.UserInfoResponse;
import com.gmail.v.c.charkin.gurmanfood.exception.EntityNotFoundException;
import com.gmail.v.c.charkin.gurmanfood.repository.OrderRepository;
import com.gmail.v.c.charkin.gurmanfood.repository.ShawarmaRepository;
import com.gmail.v.c.charkin.gurmanfood.repository.UserRepository;
import com.gmail.v.c.charkin.gurmanfood.service.AdminService;
import com.gmail.v.c.charkin.gurmanfood.service.FileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final ShawarmaRepository shawarmaRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final FileService fileService;

    @Override
    public Page<Shawarma> getShawarmas(Pageable pageable) {
        return shawarmaRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Shawarma> searchShawarmas(SearchRequest request, Pageable pageable) {
        return shawarmaRepository.searchShawarmas(request.getSearchType(), request.getText(), pageable);
    }

    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> searchUsers(SearchRequest request, Pageable pageable) {
        return userRepository.searchUsers(request.getSearchType(), request.getText(), pageable);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.getById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.ORDER_NOT_FOUND));
    }

    @Override
    public Page<Order> getOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);

    }

    @Override
    public Page<Order> searchOrders(SearchRequest request, Pageable pageable) {
        return orderRepository.searchOrders(request.getSearchType(), request.getText(), pageable);
    }

    @Override
    public Shawarma getShawarmaById(Long shawarmaId) {
        return shawarmaRepository.findById(shawarmaId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.SHAWARMA_NOT_FOUND));
    }

    @Override
    @Transactional
    public MessageResponse editShawarma(ShawarmaRequest shawarmaRequest, MultipartFile file) throws IOException {
        return saveShawarma(shawarmaRequest, file, SuccessMessage.SHAWARMA_EDITED);
    }

    @Override
    @Transactional
    public MessageResponse addShawarma(ShawarmaRequest shawarmaRequest, MultipartFile file) throws IOException {
        return saveShawarma(shawarmaRequest, file, SuccessMessage.SHAWARMA_ADDED);
    }

    @Override
    public UserInfoResponse getUserById(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        Page<Order> orders = orderRepository.findOrderByUserId(userId, pageable);
        return new UserInfoResponse(user, orders);
    }

    private MessageResponse saveShawarma(ShawarmaRequest shawarmaRequest, MultipartFile file, String message) throws IOException {
        Shawarma shawarma = modelMapper.map(shawarmaRequest, Shawarma.class);
        if (file != null && !file.isEmpty()) {
            String resultFilename = fileService.saveFile(file);
            shawarma.setFilename(resultFilename);
        }
        shawarmaRepository.save(shawarma);
        return new MessageResponse("alert-success", message);
    }
}
