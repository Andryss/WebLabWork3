package ru.andryss.weblab3.view.checkers.converters;

import javax.faces.convert.FacesConverter;

@FacesConverter("rFieldConverter")
public class RFieldConverter extends XFieldConverter {

    protected final String notIntegerErrorString = "select only integer";

}
