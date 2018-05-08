package com.nano.software.vehicle.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class must be used to hold the vehicle information of a vehicle
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class Vehicle {

    private String registrationNo;
    private String make;
    private String colour;

    public Vehicle(final Vehicle.Builder builder) {
        this.registrationNo = builder.vehicle.registrationNo;
        this.make = builder.vehicle.make;
        this.colour = builder.vehicle.colour;
    }

    public Vehicle() {
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public void setRegistrationNo(final String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(final String make) {
        this.make = make;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(final String colour) {
        this.colour = colour;
    }

    /**
     * Implement the hashCode method using HashCodeBuilder.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.registrationNo)
                .append(this.make)
                .append(this.colour)
                .toHashCode();
    }

    /**
     * Implement the equals method using the EqualsBuilder.
     *
     * @param obj object to compare with
     * @return true if the objects are same otherwise false
     */
    @Override
    public boolean equals(final Object obj) {

        if (!(obj instanceof Vehicle)) {
            return false;
        }

        final Vehicle that = (Vehicle) obj;

        return new EqualsBuilder()
                .append(this.registrationNo, that.registrationNo)
                .append(this.make, that.make)
                .append(this.colour, that.colour)
                .isEquals();
    }

    /**
     * Implement the toString method using the ToStringBuilder
     *
     * @return toString
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("registrationNo", this.registrationNo)
                .append("make", this.make)
                .append("colour", this.colour)
                .toString();
    }

    public static class Builder {

        private Vehicle vehicle;

        public Builder() {
            this.vehicle = new Vehicle();
        }

        public Builder withRegistrationNo(final String registrationNo) {
            this.vehicle.registrationNo = registrationNo;
            return this;
        }

        public Builder withMake(final String make) {
            this.vehicle.make = make;
            return this;
        }

        public Builder withColour(final String colour) {
            this.vehicle.colour = colour;
            return this;
        }

        public Vehicle build() {

            return new Vehicle(this);
        }

    }

}
