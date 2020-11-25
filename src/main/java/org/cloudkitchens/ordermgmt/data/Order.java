package org.cloudkitchens.ordermgmt.data;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(unique = true, nullable = false, columnDefinition="VARCHAR(36)")
    private String id;

    private String name;

    private String temp;

    private int shelfLife;

    private double decayRate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTime;

    private int status;

    public Order() {

    }

    public Order(String id, String name, String temp, int shelfLife, double decayRate) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.shelfLife = shelfLife;
        this.decayRate = decayRate;
        this.status = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTemp() {
        return temp;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public double getDecayRate() {
        return decayRate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {

        return String.format("Order Details: UUID : %s, Name : %s, Temp : %s, ShelfLife : %d, Decay : %f"
                + ", Status : Integer.toString(status), CreationTime : %s", getId(), getName(), getTemp(),
                getShelfLife(), getDecayRate(), getStatus(), getCreationTime().toString());
    }
}
