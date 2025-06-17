package com.sadmag.macros_v2.user;

import lombok.Getter;

@Getter
public enum UserRole {
   ADMIN("admin"),
   USER("user");

   private String role;

   UserRole(String role) { }
}