package com.nano.software.vehicle.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.nano.software.vehicle.dto.VehicleFile;
import com.nano.software.vehicle.dto.Vehicle;

/**
 * Interface definition for scanning of a pre-configured directory and reading the content of the supported file types
 *
 * @author Santosh pandey
 * @since 08/05/2018
 */

public interface DirectoryScanner {
	
	List<Optional<VehicleFile>> getVehicleFiles()
			throws IOException;

	Optional<List<Vehicle>> getAllVehiclesFromFile(final VehicleFile vehicleFile)
			throws IOException;

}
