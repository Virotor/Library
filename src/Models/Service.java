package Models;

public class Service {
    private String description;
    private int serviceId;
    private String serviceName;
    private int moneyBig;
    private int moneyLittle;


    public Service(String description, int serviceId, String serviceName, int moneyBig, int moneyLittle) {
        this.description = description;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.moneyBig = moneyBig;
        this.moneyLittle = moneyLittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getMoneyBig() {
        return moneyBig;
    }

    public void setMoneyBig(int moneyBig) {
        this.moneyBig = moneyBig;
    }

    public int getMoneyLittle() {
        return moneyLittle;
    }
}
