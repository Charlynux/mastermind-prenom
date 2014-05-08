package com.mastermind.prenom.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrenomResultBean extends ResultBean {
	private String prenom;
}
