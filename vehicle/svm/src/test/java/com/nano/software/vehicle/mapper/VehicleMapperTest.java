package com.nano.software.vehicle.mapper;

import com.nano.software.vehicle.dto.Vehicle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.nano.software.vehicle.constant.ApplicationConstant.COMMA;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @author Santosh pandey
 * @since 08/05/2018
 */

class VehicleMapperTest {

    final String registration = "LG11LHR";
    final String make = "Honda";
    final String colour = "Red";

    @Test
    void mapVehicleInformationInCSVFormatToVehicleObject_success() {

        final StringBuilder vehicleDetails = new StringBuilder();
        vehicleDetails.append(registration)
                .append(COMMA)
                .append(make)
                .append(COMMA)
                .append(colour);

        final Optional<Vehicle> vehicle = VehicleMapper.apply(vehicleDetails.toString());

        assertAll("Verify vehicle mapping when the vehicle information is in csv format",
                () -> {
                    assertThat("Incorrect registration found",
                            vehicle.get().getRegistrationNo(), is(equalTo(registration)));
                    assertThat("Incorrect make found",
                            vehicle.get().getMake(), is(equalTo(make)));
                    assertThat("Incorrect colour found",
                            vehicle.get().getColour(), is(equalTo(colour)));

                });
    }

    @Test
    void mapVehicleInformationInExcelFormatToVehicleObject_success() {

        final Workbook workbook = new XSSFWorkbook();
        final Sheet sheet = workbook.createSheet("vehicle");

        final Row excelRow = sheet.createRow(0);
        final Cell cell_0 = excelRow.createCell(0);
        final Cell cell_1 = excelRow.createCell(1);
        final Cell cell_2 = excelRow.createCell(2);
        cell_0.setCellValue(registration);
        cell_1.setCellValue(make);
        cell_2.setCellValue(colour);

        final Optional<Vehicle> vehicle = VehicleMapper.apply(excelRow);

        assertAll("Verify vehicle mapping when the vehicle information is in csv format",
                () -> {
                    assertThat("Incorrect registration found",
                            vehicle.get().getRegistrationNo(), is(equalTo(registration)));
                    assertThat("Incorrect make found",
                            vehicle.get().getMake(), is(equalTo(make)));
                    assertThat("Incorrect colour found",
                            vehicle.get().getColour(), is(equalTo(colour)));

                });
    }
}