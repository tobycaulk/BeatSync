package com.astimefades.beatsyncservice.util;

import com.astimefades.beatsyncservice.model.Model;
import com.astimefades.beatsyncservice.model.Track;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Random;

public class Util {

    public static <T extends Model> T removeModelFromListById(List<T> t, ObjectId id) {
        T toRemove = t
                .stream()
                .filter(o -> o.getId().equals(o.getId()))
                .findFirst().orElseGet(() -> null);
        t.remove(toRemove);

        return toRemove;
    }
}