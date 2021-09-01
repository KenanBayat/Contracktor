package de.sopro.model;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Right {
	
	@Getter @Setter @NotNull private String rightName;
	
	public Right(String rightName) {
		this.rightName = rightName;
	}

}
