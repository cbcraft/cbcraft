package io.github.cbcraft.common.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialRobot extends Material {
	public static final Material robot = new MaterialRobot(MapColor.airColor);
	
	public MaterialRobot(MapColor color) {
		super(color);
		this.setImmovableMobility();
	}
}
