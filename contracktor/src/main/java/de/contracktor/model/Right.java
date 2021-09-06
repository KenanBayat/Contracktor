package de.contracktor.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Right {
	
	@Getter @Setter @NotNull @Id private String rightName;
	
	public Right(String rightName) {
		this.rightName = rightName;
	}

}
