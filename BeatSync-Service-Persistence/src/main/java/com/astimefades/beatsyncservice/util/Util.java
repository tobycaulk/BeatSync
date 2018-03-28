package com.astimefades.beatsyncservice.util;

import com.astimefades.beatsyncservice.model.Model;
import com.astimefades.beatsyncservice.model.Track;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Random;

public class Util {

    public static <T extends Model> T removeModelFromListById(List<T> t, String id) {
        T toRemove = t
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElseGet(() -> null);
        t.remove(toRemove);

        return toRemove;
    }

    public static <T extends Model> T getModelFromListById(List<T> t, String id) {
        return t
                .stream()
                .filter(o -> o.getId().equals(id))
                .findFirst().orElseGet(() -> null);
    }
}