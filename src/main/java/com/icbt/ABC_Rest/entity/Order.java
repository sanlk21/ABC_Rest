package com.icbt.ABC_Rest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetails> orderDetails;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date orderDate;

    @Column(nullable = false)
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderType type;

    public enum OrderStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        CANCELLED
    }

    public enum OrderType {
        DINEIN,
        TAKEAWAY,
        DELIVERY
    }
}
