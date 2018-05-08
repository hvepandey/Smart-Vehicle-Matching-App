package com.nano.software.vehicle.service;

import com.nano.software.vehicle.configuration.Configuration;
import com.nano.software.vehicle.configuration.ConfigurationException;
import com.nano.software.vehicle.configuration.ConfigurationService;
import com.nano.software.vehicle.dto.VehicleFile;
import com.nano.software.vehicle.dto.Vehicle;
import com.nano.software.vehicle.mapper.VehicleFileMapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements interface {@link DirectoryScanner}
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class DirectoryScannerService
        implements DirectoryScanner {

    final static Logger logger = Logger.getLogger(DirectoryScannerService.class);

    private final Configuration config;

    private final File file;

    public DirectoryScannerService()
            throws ConfigurationException {

        config = ConfigurationService.getInstance();
        file = new File(config.getRootDirectory());

    }

    /**
     * This method returns all the supported vehicle files present in a pre-configured directory
     * @return list of supported vehicle files
     * @throws IOException ioexception
     */
    public List<Optional<VehicleFile>> getVehicleFiles()
            throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Reading file's metadata from a directory %s", file.getPath()));
        }

        return findFiles(this.file)
                .stream()
                .filter(file -> isSupportedFileType(file))
                .map(s -> VehicleFileMapper.apply(s))
                .collect(Collectors.toList());

    }
    /**
     * This method returns all the vehicle information present in a supported vehicle file
     * @param vehicleFile the supported vehicle file
     * @return vehicle information
     * @throws IOException ioexception
     */
    public Optional<List<Vehicle>> getAllVehiclesFromFile(final VehicleFile vehicleFile)
            throws IOException {

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Get file content from %s", vehicleFile.getFile().getPath()));
        }

        if (isSupportedFileType(vehicleFile)) {

            if ("csv".equalsIgnoreCase(vehicleFile.getExtension())) {
                return VehicleReaderHelper.getAllVehiclesFromCsvFile(vehicleFile);
            }

            if ("xsl".equalsIgnoreCase(vehicleFile.getExtension())) {
                return VehicleReaderHelper.getAllVehiclesFromExcelFile(vehicleFile);
            }

        }
            return Optional.empty();
    }

    private List<File> findFiles(final File root)
            throws FileNotFoundException {

        if (root.exists()) {
            return Arrays.asList(root.listFiles(f -> f.isFile()));

        } else {
            throw new FileNotFoundException(String.format("Root directory %s does not exist", root.getPath()));

        }
    }

    private boolean isSupportedFileType(final File file) {

        try {
            return config.getSupportedFileTypes().contains(Files.probeContentType(file.toPath()));

        } catch (IOException ioe) {
            logger.error(String.format("Unable to read vehicle file %s", file.getPath()));
            throw new RuntimeException(ioe);

        } catch (ConfigurationException configex) {
            logger.error("Missing vehicle supported file types");
            throw new RuntimeException(configex);

        }
    }

    private boolean isSupportedFileType(final VehicleFile file) {

        try {
            return config.getSupportedFileTypes().contains(file.getMimetype());

        } catch (ConfigurationException configex) {
            logger.error("Missing vehicle supported file types");
            throw new RuntimeException(configex);

        }
    }

}
