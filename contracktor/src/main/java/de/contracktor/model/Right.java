package de.contracktor.model;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Right {
	
	@Getter @Setter @NotNull private String rightName;
	
	public Right(String rightName) {
		this.rightName = rightName;
	}

}
