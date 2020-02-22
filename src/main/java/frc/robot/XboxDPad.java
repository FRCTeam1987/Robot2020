/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;

public class XboxDPad extends Button {

    public static enum Direction {
		Down(180),
		Left(270),
		Right(90),
		Up(0),
		Null(-1);

		private final int value;

		Direction(final int value) {
			this.value = value;
		}

		public int get() {
			return this.value;
		}
	}

	private final XboxController xbox;
	private final Direction direction;

	public XboxDPad(final XboxController xbox, final Direction direction) {
		this.xbox = xbox;
		this.direction = direction;
    }

	@Override
	public boolean get() {
		return this.xbox.getPOV() == this.direction.get();
	}
}