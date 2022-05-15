package com.my.model.dao.exeptions;

public class DBException extends RuntimeException {

     public DBException() {
         super();
     }

     public DBException(String str) {
         super(str);
     }

     public DBException(String str, Exception e) {
         super(str, e);
     }
}
