/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
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

    public boolean isInt() {
        return this == INT;
    }

    public boolean isString() {
        return this == STRING;
    }

    public boolean isArray() {
        return this == ARRAY_STRING || this == ARRAY_INT;
    }
}
