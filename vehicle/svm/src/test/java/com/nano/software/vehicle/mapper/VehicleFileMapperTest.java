package com.nano.software.vehicle.mapper;

import com.nano.software.vehicle.dto.VehicleFile;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
@PrepareForTest({VehicleFileMapper.class, Files.class})
class VehicleFileMapperTest {

    @Test
    void mapVehicleFileToVehicleFileObject_Success() throws IOException {

        final String fileName = "testFile.csv";
        final String mimeType = "application/csv";
        final String extension = "csv";
        final Long fileLength = new Long(4444444);
        final double size = fileLength / 1024;

        final File mockFile = mock(File.class);
        final Path mockFilePath = mock(Path.class);
        when(mockFile.getName()).thenReturn(fileName);
        when(mockFile.toPath()).thenReturn(mockFilePath);
        when(mockFile.length()).thenReturn(fileLength);

        mockStatic(Files.class);
        PowerMockito.when(Files.probeContentType(mockFilePath)).thenReturn(mimeType);

        final Optional<VehicleFile> vehicleFile = VehicleFileMapper.apply(mockFile);

        assertAll("Verify vehicle file mapping",
                () -> {
                    assertThat("Incorrect file name found",
                            vehicleFile.get().getFilename(), is(equalTo(fileName)));
                    assertThat("Incorrect mimeType found",
                            vehicleFile.get().getMimetype(), is(equalTo(mimeType)));
                    assertThat("Incorrect file extension found",
                            vehicleFile.get().getExtension(), is(equalTo(extension)));
                    assertThat("Incorrect file size found",
                            vehicleFile.get().getSize(), is(equalTo(size)));
                    assertThat("Incorrect file found",
                            vehicleFile.get().getFile(), is(equalTo(mockFile)));

                });
    }
}