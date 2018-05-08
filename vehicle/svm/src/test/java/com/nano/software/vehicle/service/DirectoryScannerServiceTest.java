
package com.nano.software.vehicle.service;

import com.nano.software.vehicle.configuration.ConfigurationException;
import com.nano.software.vehicle.configuration.ConfigurationService;
import com.nano.software.vehicle.dto.Vehicle;
import com.nano.software.vehicle.dto.VehicleFile;
import com.nano.software.vehicle.mapper.VehicleFileMapper;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Santosh pandey
 * @since 08/05/2018
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({VehicleFileMapper.class, ConfigurationService.class})
public class DirectoryScannerServiceTest {


    final static Logger logger = Logger.getLogger(DirectoryScannerServiceTest.class);
    final String mimeType = "application/vnd.ms-excel";
    private DirectoryScanner directoryScanner;
    private List<Optional<VehicleFile>> files;

    @Before
    public void setUp() throws ConfigurationException, IOException {

        final File resourcesDirectory = new File("src/test/resources");
        final ConfigurationService config = mock(ConfigurationService.class);

        mockStatic(ConfigurationService.class);
        PowerMockito.when(ConfigurationService.getInstance()).thenReturn(config);
        when(config.getRootDirectory()).thenReturn(resourcesDirectory.getAbsolutePath());
        when(config.getSupportedFileTypes()).thenReturn(Arrays.asList(mimeType));

        directoryScanner = new DirectoryScannerService();
        files = directoryScanner.getVehicleFiles();
    }

    /**
     * Test method for {@link com.nano.software.vehicle.service.DirectoryScannerService#getVehicleFiles()}.
     *
     * @throws IOException
     * @throws ConfigurationException
     */
    @Test
    public void readVehicleFilesAndMetaDataSuccessTest() {

        files.forEach(vehicleFile -> logger.debug(vehicleFile.toString()));

        assertAll("Verify no of vehicle files and metadata",
                () -> {
                    assertThat("Incorrect number of vehicle files found",
                            files.size(), is(equalTo(1)));

                    final VehicleFile vehicleFile = files.get(0).get();

                    assertThat("Incorrect vehicle file name found",
                            vehicleFile.getFilename(), is(equalTo("vehicles-csv-file-1.csv")));
                    assertThat("Incorrect vehicle file mimeType found",
                            vehicleFile.getMimetype(), is(equalTo(mimeType)));
                    assertThat("Incorrect vehicle file extension found",
                            vehicleFile.getExtension(), is(equalTo("csv")));
                    assertThat("Incorrect vehicle file size found",
                            vehicleFile.getSize(), is(equalTo(0.08203125)));
                });
    }

    /**
     * Test method for {@link com.nano.software.vehicle.service.DirectoryScannerService#getAllVehiclesFromFile(VehicleFile)}.
     *
     * @throws IOException
     * @throws ConfigurationException
     */
    @Test
    public void readVehicleDetailsFromVehicleFileSuccessTest() {

        assertAll("Verify vehicle details",
                () -> {
                    assertThat("Incorrect number of vehicle files found",
                            files.size(), is(equalTo(1)));

                    final VehicleFile vehicleFile = files.get(0).get();
                    final Optional<List<Vehicle>> vehicles = directoryScanner.getAllVehiclesFromFile(vehicleFile);

                    assertThat("Incorrect number of vehicles found",
                            vehicles.get().size(), is(equalTo(3)));

                    assertThat("Incorrect vehicle registration header found",
                            vehicles.get().get(0).getRegistrationNo(), is(equalTo("Registration No")));
                    assertThat("Incorrect vehicle make header found",
                            vehicles.get().get(0).getMake(), is(equalTo("Make")));
                    assertThat("Incorrect vehicle colour header found",
                            vehicles.get().get(0).getColour(), is(equalTo("Colour")));

                    assertThat("Incorrect vehicle registration found",
                            vehicles.get().get(1).getRegistrationNo(), is(equalTo("LG11 HLR")));
                    assertThat("Incorrect vehicle make found",
                            vehicles.get().get(1).getMake(), is(equalTo("Honda")));
                    assertThat("Incorrect vehicle colour found",
                            vehicles.get().get(1).getColour(), is(equalTo("Grey")));

                    assertThat("Incorrect vehicle registration found",
                            vehicles.get().get(2).getRegistrationNo(), is(equalTo("X298 HBA")));
                    assertThat("Incorrect vehicle make found",
                            vehicles.get().get(2).getMake(), is(equalTo("Honda")));
                    assertThat("Incorrect vehicle colour found",
                            vehicles.get().get(2).getColour(), is(equalTo("Silver")));
                });
    }

}
