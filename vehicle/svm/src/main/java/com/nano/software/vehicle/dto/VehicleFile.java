package com.nano.software.vehicle.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;

/**
 * This class must be used to hold the metadata of a vehicle file
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class VehicleFile {

    private String filename;
    private String mimetype;
    private Double size;
    private String ext;
    private File file;

    public VehicleFile(final Builder builder) {
        this.filename = builder.vehicleFile.filename;
        this.mimetype = builder.vehicleFile.mimetype;
        this.size = builder.vehicleFile.size;
        this.ext = builder.vehicleFile.ext;
        this.file = builder.vehicleFile.file;
    }

    public VehicleFile() {
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getExtension() {
        return ext;
    }

    public void setExtension(String extension) {
        this.ext = extension;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Implement the hashCode method using HashCodeBuilder.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.filename)
                .append(this.mimetype)
                .append(this.size)
                .append(this.ext)
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

        if (!(obj instanceof VehicleFile)) {
            return false;
        }

        final VehicleFile that = (VehicleFile) obj;

        return new EqualsBuilder()
                .append(this.filename, that.filename)
                .append(this.mimetype, that.filename)
                .append(this.size, that.filename)
                .append(this.ext, that.filename)
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
                .append("filename", this.filename)
                .append("mimetype", this.mimetype)
                .append("ext", this.ext)
                .append("size in mb", this.size).toString();
    }

    public static class Builder {

        private VehicleFile vehicleFile;

        public Builder() {
            this.vehicleFile = new VehicleFile();
        }

        public Builder withName(final String name) {
            this.vehicleFile.filename = name;
            return this;
        }

        public Builder withMimetype(final String mimetype) {
            this.vehicleFile.mimetype = mimetype;
            return this;
        }

        public Builder withSize(final Double size) {
            this.vehicleFile.size = size;
            return this;
        }

        public Builder withExt(final String ext) {
            this.vehicleFile.ext = ext;
            return this;
        }

        public Builder withFile(final File file) {
            this.vehicleFile.file = file;
            return this;
        }

        public VehicleFile build() {

            return new VehicleFile(this);
        }

    }

}
