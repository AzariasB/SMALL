/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sal.small;

/**
 *
 * @author azarias
 */
public enum Type {
    INT("I"),
    STRING("java/lang/String"),
    ARRAY_INT("[I"),
    ARRAY_STRING("[Ljava.lang.String;");

    private final String code;

    private Type(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public boolean isString() {
        return this == STRING;
    }

    public boolean isArray() {
        return this == ARRAY_STRING || this == ARRAY_INT;
    }
}
