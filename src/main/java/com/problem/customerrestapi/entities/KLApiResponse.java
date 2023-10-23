package com.problem.customerrestapi.entities;

public class KLApiResponse {
    public Phone phone;
    public StreetAddress streetAddress;

    @Override
    public String toString() {
        return "KLApiResponse{" +
                "phone=" + phone +
                ", streetAddress=" + streetAddress +
                '}';
    }

    public String getPhoneNumber() {
        return this.getPhone() != null ? this.getPhone().getValue() : null;
    }

    public String getStreetAddressActual() {
        return this.getStreetAddress() != null ? this.getStreetAddress().getStreet() != null ? this.getStreetAddress().getStreet().getValue() : null : null;

    }

    public Phone getPhone() {
        return phone;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }
}


class Phone {
    public String value;


    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "value='" + value + '\'' +
                '}';
    }
}


class Street {
    public String value;

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Street{" +
                "value='" + value + '\'' +
                '}';
    }
}

class StreetAddress {
    public Street street;

    @Override
    public String toString() {
        return "StreetAddress{" +
                "street=" + street +
                '}';
    }

    public Street getStreet() {
        return street;
    }
}

