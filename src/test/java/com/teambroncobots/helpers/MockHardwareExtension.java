// From:
// https://github.com/robototes/2020_Template/blob/master/src/test/java/com/robototes/helpers/MockHardwareExtension.java
package com.teambroncobots.helpers;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * JUnit 5 testing extension which ensures all WPILib foundational bits are
 * initialized to be able to run the scheduler.
 */
public final class MockHardwareExtension {

	public static void beforeAll() {
		initializeHardware();
	}

	public static void afterAll() {
		DriverStation.getInstance().release();
		HAL.releaseDSMutex();
	}

	private static void initializeHardware() {
		HAL.initialize(500, 0);
		DriverStationSim dsSim = new DriverStationSim();
		dsSim.setDsAttached(true);
		dsSim.setAutonomous(false);
		dsSim.setEnabled(true);
		dsSim.setTest(true);
	}
}