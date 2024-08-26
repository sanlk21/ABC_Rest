package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.PaymentDto;
import com.icbt.ABC_Rest.entity.Payment;
import com.icbt.ABC_Rest.entity.User;
import com.icbt.ABC_Rest.entity.Order;
import com.icbt.ABC_Rest.exception.ResourceNotFoundException;
import com.icbt.ABC_Rest.repo.PaymentRepo;
import com.icbt.ABC_Rest.repo.UserRepo;
import com.icbt.ABC_Rest.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    public PaymentDto createPayment(PaymentDto paymentDto) {
        User user = userRepo.findByEmail(paymentDto.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email " + paymentDto.getUserId());
        }

        Order order = orderRepo.findById(paymentDto.getOrderId()).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id " + paymentDto.getOrderId());
        }

        Payment payment = Payment.builder()
                .paymentMethod(Payment.PaymentMethod.valueOf(paymentDto.getPaymentMethod().name()))
                .status(Payment.PaymentStatus.valueOf(paymentDto.getStatus().name()))
                .amount(paymentDto.getAmount())
                .user(user)
                .order(order)
                .paymentDate(paymentDto.getPaymentDate())
                .build();

        Payment savedPayment = paymentRepo.save(payment);
        return convertToDto(savedPayment);
    }

    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment existingPayment = paymentRepo.findById(id).orElse(null);
        if (existingPayment == null) {
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }

        User user = userRepo.findByEmail(paymentDto.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User not found with email " + paymentDto.getUserId());
        }

        Order order = orderRepo.findById(paymentDto.getOrderId()).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id " + paymentDto.getOrderId());
        }

        existingPayment.setPaymentMethod(Payment.PaymentMethod.valueOf(paymentDto.getPaymentMethod().name()));
        existingPayment.setStatus(Payment.PaymentStatus.valueOf(paymentDto.getStatus().name()));
        existingPayment.setAmount(paymentDto.getAmount());
        existingPayment.setUser(user);
        existingPayment.setOrder(order);
        existingPayment.setPaymentDate(paymentDto.getPaymentDate());

        Payment updatedPayment = paymentRepo.save(existingPayment);
        return convertToDto(updatedPayment);
    }

    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepo.findById(id).orElse(null);
        if (payment == null) {
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }
        return convertToDto(payment);
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepo.findAll();
        return payments.stream().map(this::convertToDto).toList();
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepo.findById(id).orElse(null);
        if (payment == null) {
            throw new ResourceNotFoundException("Payment not found with id " + id);
        }
        paymentRepo.delete(payment);
    }

    private PaymentDto convertToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .paymentMethod(PaymentDto.PaymentMethod.valueOf(payment.getPaymentMethod().name()))
                .status(PaymentDto.PaymentStatus.valueOf(payment.getStatus().name()))
                .amount(payment.getAmount())
                .userId(payment.getUser().getEmail())  // Adjusted to use email instead of id
                .orderId(payment.getOrder().getId())
                .paymentDate(payment.getPaymentDate())
                .build();
    }
}
