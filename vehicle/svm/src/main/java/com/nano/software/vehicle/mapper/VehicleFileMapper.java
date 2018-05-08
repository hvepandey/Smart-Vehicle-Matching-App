package com.nano.software.vehicle.mapper;

import com.nano.software.vehicle.dto.VehicleFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * This class maps vehicle file information to Vehicle file metadata  object
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public class VehicleFileMapper {

    public static Optional<VehicleFile> apply(final File file) {

        if (file != null) {

            final double bytes = file.length();
            final double kilobytes = (bytes / 1024);

            try {
                final VehicleFile vehicleFile = new VehicleFile.Builder()
                        .withName(file.getName())
                        .withMimetype(Files.probeContentType(file.toPath()))
                        .withSize(kilobytes)
                        .withExt(FilenameUtils.getExtension(file.getName()))
                        .withFile(file).build();

                return Optional.of(vehicleFile);

            } catch (IOException e) {
                throw new RuntimeException(e);

            }

        } else {
            return Optional.empty();
        }
    }

}
