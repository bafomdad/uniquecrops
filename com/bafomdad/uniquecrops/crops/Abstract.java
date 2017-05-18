package com.bafomdad.uniquecrops.crops;

import com.bafomdad.uniquecrops.blocks.BlockCropsBase;
import com.bafomdad.uniquecrops.core.EnumCrops;
import com.bafomdad.uniquecrops.core.UCConfig;

public class Abstract extends BlockCropsBase {

	public Abstract() {
		
		super(EnumCrops.ABSTRACT, false, UCConfig.cropAbstract);
	}
}
