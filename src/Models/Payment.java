package Models;

import Enums.PaymentStatus;
import Enums.PaymentType;

public class Payment {
    private int userId;
    private int serviceId;
    private String serviceName;
    private int paymentId;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;


    public Payment(int userId, int serviceId, String serviceName, int paymentId, PaymentStatus paymentStatus, PaymentType paymentType) {
        this.userId = userId;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
