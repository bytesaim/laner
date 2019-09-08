package dev.sourcerersproject.codewalker.attrib.java;

import dev.sourcerersproject.codewalker.enums.java.StatementType;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Azeez Adewale <azeezadewale98@gmail.com>
 * :date: 04 September 2019
 * :time: 10:59 AM
 * :filename: Statement.java
 */
public class Statement {

    /**
     *
     */
    private StatementType type = StatementType.UNKNOWN;

    /**
     *
     */
    Object object = new Object();

    /**
     *
     */
    public Object getObject() {
        return object;
    }

    /**
     *
     * @return
     */
    public StatementType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(StatementType type) {
        this.type = type;
    }
}
