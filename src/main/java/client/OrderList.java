package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderList {
    private int id;
    private Integer courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private int track;
    private List<String> color;
    private String comment;
    private String createdAt;
    private String updatedAt;
    private int status;
}
