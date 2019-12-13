package dev.sourcerersproject.codewalker.attrib.java;

import java.util.ArrayList;

/**
 * :copyright: MIT LICENSE (c) 2019 watchersproject
 * :author: Adewale Azeez <azeezadewale98@gmail.com>
 * :date: 23 September 2019
 * :time: 03:25 AM
 * :filename: Package.java
 */
public class Package {

    /**
     *
     */
    private ArrayList<Class> classes = new ArrayList<Class>();

    /**
     *
     */
    private String name = "" ;

    /**
     *
     */
    private boolean structureChanges = true;

    /**
     *
     * @return
     */
    public ArrayList<Class> getClasses() {
        return classes;
    }

    /**
     *
     * @param clazz
     */
    public void addMethod(Class clazz) {
        classes.add(clazz);
        structureChanges = true;
    }

    /**
     *
     * @param clazz
     */
    public void removeClass(Class clazz) {
        classes.remove(clazz);
        structureChanges = true;
    }

    /**
     *
     * @param index
     */
    public void removeClass(int index) {
        classes.remove(index);
        structureChanges = true;
    }

}
