package net.tislib.ugm.lib.markers.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class MarkerData {
    private String name;
    private String type;

    private Map<String, Serializable> parameters;
}