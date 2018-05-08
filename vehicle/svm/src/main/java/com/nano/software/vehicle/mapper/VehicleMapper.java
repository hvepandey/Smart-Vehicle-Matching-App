package com.nano.software.vehicle.mapper;

import com.nano.software.vehicle.dto.Vehicle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;
import java.util.Optional;

import static com.nano.software.vehicle.constant.ApplicationConstant.COMMA;

/**
 * This class maps vehicle information stored in files (csv or excel) to Vehicle objects
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class VehicleMapper {

    /**
     * This method is used maps vehicle information stored in a csv file to a Vehicle object
     *
     * @param vehicleDetails vehicle information
     * @return vehicle object
     */
    public static Optional<Vehicle> apply(final String vehicleDetails) {

        if (vehicleDetails != null) {

            final String[] vehicleAttribute = vehicleDetails.split(COMMA);

            if (vehicleAttribute == null
                    || vehicleAttribute.length != 3) {
                throw new RuntimeException(String.format("unsupported format of vehicle entry, %s, found in the csv/excel file", vehicleDetails));

            }

            final Vehicle vehicle = new Vehicle.Builder()
                    .withRegistrationNo(vehicleAttribute[0].trim())
                    .withMake(vehicleAttribute[1].trim())
                    .withColour(vehicleAttribute[2].trim())
                    .build();

            return Optional.of(vehicle);


        } else {
            return Optional.empty();

        }
    }

    /**
     * This method is used maps vehicle information stored in a excel file to a Vehicle object
     *
     * @param excelRow vehicle information
     * @return vehicle object
     */
    public static Optional<Vehicle> apply(final Row excelRow) {

        if (excelRow != null) {

            final Iterator<Cell> cellIterator = excelRow.iterator();
            final StringBuilder sb = new StringBuilder();

            while (cellIterator.hasNext()) {

                final Cell currentCell = cellIterator.next();

                if (currentCell.getCellTypeEnum() == CellType.STRING) {

                    if (sb.length() == 0) {
                        sb.append(currentCell.getStringCellValue());

                    } else {
                        sb.append(COMMA).append(currentCell.getStringCellValue());

                    }
                }
            }

            return apply(sb.toString());

        } else {
            return Optional.empty();

        }
    }
}
