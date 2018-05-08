package com.nano.software.vehicle.service;

import com.nano.software.vehicle.dto.VehicleFile;
import com.nano.software.vehicle.dto.Vehicle;
import com.nano.software.vehicle.mapper.VehicleMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static com.nano.software.vehicle.service.DirectoryScannerService.logger;


/**
 * Helper class for reading the supported vehicle file types
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class VehicleReaderHelper {

    /**
     * Reading the vehicle information from a csv file
     * @param vehicleFile vehicleFile
     * @return list of Vehicle objects
     * @throws IOException ioException
     */
    public static Optional<List<Vehicle>> getAllVehiclesFromExcelFile(final VehicleFile vehicleFile)
            throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Getting vehicle details from a excel file, %s", vehicleFile.getFile().getPath()));
        }

        final FileInputStream excelFile = new FileInputStream(vehicleFile.getFile());
        final Workbook workbook = new XSSFWorkbook(excelFile);
        final Sheet datatypeSheet = workbook.getSheetAt(0);
        final Iterator<Row> iterator = datatypeSheet.iterator();
        final List<Vehicle> vehicles = new ArrayList<>();

        while (iterator.hasNext()) {
            VehicleMapper.apply(iterator.next()).ifPresent(vehicles::add);

        }

        return Optional.of(vehicles);

    }

    /**
     * Reading the vehicle information from a excel file
     * @param vehicleFile vehicleFile
     * @return list of Vehicle objects
     * @throws IOException ioException
     */
    public static Optional<List<Vehicle>> getAllVehiclesFromCsvFile(final VehicleFile vehicleFile)
            throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Getting vehicle details from a csv file, %s", vehicleFile.getFile().getPath()));
        }

        final Scanner sc = new Scanner(vehicleFile.getFile());
        final List<Vehicle> vehicles = new ArrayList<>();

        while (sc.hasNextLine()) {
            VehicleMapper.apply(sc.nextLine()).ifPresent(vehicles::add);

        }
        return Optional.of(vehicles);

    }
}
