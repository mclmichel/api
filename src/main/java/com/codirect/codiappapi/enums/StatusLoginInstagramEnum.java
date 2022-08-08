package com.codirect.codiappapi.enums;

import lombok.Getter;

@Getter
public enum StatusLoginInstagramEnum {

  SUCESS_LOGIN("SUCESS_LOGIN"),
  BAD_PASSWORD("BAD_PASSWORD"),
  TWO_FACTOR_AUTH("TWO_FACTOR_AUTH");

  private String description;

  StatusLoginInstagramEnum(String description) {
    this.description = description;
  }
}
